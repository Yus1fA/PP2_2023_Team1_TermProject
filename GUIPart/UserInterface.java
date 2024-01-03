package GUIPart;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import MovieData.Movie;
import MovieData.MovieAlreadyInWatchlistException;
import MovieData.MovieDatabase;
import MovieData.Watchlist;
import UserData.User;

public class UserInterface extends JFrame {
    private MovieDatabase movieDatabase;
    private User currentUser;
    private Watchlist currentWatchlist;

    private JPanel loginPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JList<Movie> movieList;
    private JList<Movie> watchlistJList;

    private JTextField titleField;
    private JTextField directorField;
    private JTextField releaseYearField;
    private JTextField runningTimeField;

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "1223334444";

    public UserInterface() {
        currentUser = null;
        currentWatchlist = null;

        movieDatabase = new MovieDatabase();
        try {
            movieDatabase.loadMoviesFromFile("MovieDatabase.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        DefaultListModel<Movie> movieListModel = new DefaultListModel<>();
        movieList = new JList<>(movieListModel);


        setTitle("Movie Database");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(600, 400);

        showLoginPanel();

        setVisible(true);
    }

    private void showLoginPanel() {
        getContentPane().removeAll();
    
        if (loginPanel == null) {
            loginPanel = new JPanel(new GridLayout(0, 2));
    
            loginPanel.add(new JLabel("Username:"));
            usernameField = new JTextField();
            loginPanel.add(usernameField);
    
            loginPanel.add(new JLabel("Password:"));
            passwordField = new JPasswordField();
            loginPanel.add(passwordField);
    
            JButton loginButton = new JButton("Login");
            loginButton.addActionListener(e -> loginUser());
            loginPanel.add(loginButton);
    
            JButton registerButton = new JButton("Register");
            registerButton.addActionListener(e -> registerUser());
            loginPanel.add(registerButton);
        }
    
        getContentPane().add(loginPanel, BorderLayout.CENTER);
    
        revalidate();
        repaint();
    }

    private void showRoleSelection() {
        JPanel rolePanel = new JPanel(new GridLayout(0, 1));

        JButton adminButton = new JButton("Admin");
        adminButton.addActionListener(e -> showAdminInterface());
        rolePanel.add(adminButton);

        JButton userButton = new JButton("User");
        userButton.addActionListener(e -> showUserInterface());
        rolePanel.add(userButton);

        setContentPane(rolePanel);
        revalidate();
        repaint();
    }

    private void showAdminInterface() {
        JPanel adminPanel = new JPanel(new BorderLayout());
        JPanel movieManagementPanel = new JPanel(new GridLayout(0, 2));
        titleField = new JTextField();
        directorField = new JTextField();
        releaseYearField = new JTextField();
        runningTimeField = new JTextField();
    
        movieManagementPanel.add(new JLabel("Title:"));
        movieManagementPanel.add(titleField);
        movieManagementPanel.add(new JLabel("Director:"));
        movieManagementPanel.add(directorField);
        movieManagementPanel.add(new JLabel("Release Year:"));
        movieManagementPanel.add(releaseYearField);
        movieManagementPanel.add(new JLabel("Running Time (minutes):"));
        movieManagementPanel.add(runningTimeField);
    
        JButton addButton = new JButton("Add Movie");
        addButton.addActionListener(e -> addMovie(titleField.getText(), directorField.getText(),
                Integer.parseInt(releaseYearField.getText()), Integer.parseInt(runningTimeField.getText())));
        movieManagementPanel.add(addButton);
    
        JButton removeButton = new JButton("Remove Movie");
        removeButton.addActionListener(e -> removeMovie(titleField.getText(), directorField.getText(),
                Integer.parseInt(releaseYearField.getText())));
        movieManagementPanel.add(removeButton);
    
        adminPanel.add(movieManagementPanel, BorderLayout.NORTH);
    
        movieList = new JList<>();
        updateMovieList();
        JScrollPane movieListScrollPane = new JScrollPane(movieList);
        movieListScrollPane.setBorder(BorderFactory.createTitledBorder("Movie List"));
        adminPanel.add(movieListScrollPane, BorderLayout.CENTER);
    
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showLoginPanel());
        adminPanel.add(backButton, BorderLayout.SOUTH);

        movieList.addListSelectionListener(e -> {
            Movie selectedMovie = movieList.getSelectedValue();
            if (selectedMovie != null) {
                showMovieDetailsDialog(selectedMovie);
            }
        });

        setContentPane(adminPanel);
        revalidate();
        repaint();
    }
    
    private void addMovie(String title, String director, int releaseYear, int runningTime) {
        try {
            movieDatabase.addMovie(title, director, releaseYear, runningTime);
            saveAndUpdateMovies();
            JOptionPane.showMessageDialog(this, "Movie added successfully!");
            
            titleField.setText("");
            directorField.setText("");
            releaseYearField.setText("");
            runningTimeField.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
    private void removeMovie(String title, String director, int releaseYear) {
        try {
            movieDatabase.removeMovie(title, director, releaseYear);
            saveAndUpdateMovies();
            JOptionPane.showMessageDialog(this, "Movie removed successfully!");
           
            titleField.setText("");
            directorField.setText("");
            releaseYearField.setText("");
            runningTimeField.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
    private void saveAndUpdateMovies() {
        try {
            movieDatabase.saveMoviesToFile("MovieDatabase.txt");
        } catch (IOException e) {
            e.printStackTrace(); 
        }
        updateMovieList();
    }

    private void showUserInterface() {
        JPanel userPanel = new JPanel(new BorderLayout());

        movieList = new JList<>(new DefaultListModel<Movie>());
        movieList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Movie) {
                    Movie movie = (Movie) value;
                    String text = movie.getTitle() + " - " + movie.getDirector() + " - " + movie.getReleaseYear() + " - " + movie.getRunningTime() + " minutes";
                    setText(text);
                }
                return this;
            }
        });
        updateMovieList();
        JScrollPane movieListScrollPane = new JScrollPane(movieList);
        movieListScrollPane.setBorder(BorderFactory.createTitledBorder("Browse Movies"));
        userPanel.add(movieListScrollPane, BorderLayout.CENTER);
    

        JButton infoButton = new JButton("Info");
        infoButton.addActionListener(e -> showSelectedMovieInfo());
        userPanel.add(infoButton, BorderLayout.SOUTH);

        watchlistJList = new JList<>();
        updateWatchlist();
        JScrollPane watchlistScrollPane = new JScrollPane(watchlistJList);
        watchlistScrollPane.setBorder(BorderFactory.createTitledBorder("Your Watchlist"));
        JPanel watchlistPanel = new JPanel(new BorderLayout());
        watchlistPanel.add(watchlistScrollPane, BorderLayout.CENTER);

        JButton addToWatchlistButton = new JButton("Add to Watchlist");
        addToWatchlistButton.addActionListener(e -> addToWatchlist(movieList.getSelectedValue()));
        userPanel.add(addToWatchlistButton, BorderLayout.SOUTH);

        JButton removeFromWatchlistButton = new JButton("Remove from Watchlist");
        removeFromWatchlistButton.addActionListener(e -> removeFromWatchlist());
        watchlistPanel.add(removeFromWatchlistButton, BorderLayout.SOUTH);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showLoginPanel());
        topPanel.add(backButton);

        JButton filterButton = new JButton("Filter Movies");
        filterButton.addActionListener(e -> showFilterPanel());
        topPanel.add(filterButton);
        userPanel.add(topPanel, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, userPanel, watchlistPanel);
        splitPane.setDividerLocation(300);

        setContentPane(splitPane);
        revalidate();
        repaint();
    }

    private void removeFromWatchlist() {
        Movie selectedMovie = watchlistJList.getSelectedValue();
        if (selectedMovie != null) {
            currentWatchlist.removeFromWatchlist(selectedMovie);
            updateWatchlist();
            JOptionPane.showMessageDialog(this, "Movie removed from watchlist.");
        } else {
            JOptionPane.showMessageDialog(this, "Select a movie to remove from the watchlist.");
        }
    }

    private void updateMovieList() {
        DefaultListModel<Movie> model = new DefaultListModel<>();
        for (Movie movie : movieDatabase.getAllMovies()) {
            model.addElement(movie);
        }
        movieList.setModel(model);
    }
    
    private void showSelectedMovieInfo() {
        Movie selectedMovie = movieList.getSelectedValue();
        if (selectedMovie != null) {
            String movieDetails = selectedMovie.getMovieDetails();
            JOptionPane.showMessageDialog(this, movieDetails, "Movie Details", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a movie.");
        }
    }
    
    private void addToWatchlist(Movie selectedMovie) {
        if (selectedMovie != null) {
            try {
                currentWatchlist.addToWatchlist(selectedMovie);
                updateWatchlist();
                JOptionPane.showMessageDialog(this, "Movie added to watchlist!");
            } catch (MovieAlreadyInWatchlistException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a movie to add to watchlist.");
        }
    }

    private void updateWatchlist() {
        DefaultListModel<Movie> model = new DefaultListModel<>();
        if (currentWatchlist != null) {
            for (Movie movie : currentWatchlist.getWatchlist()) {
                model.addElement(movie);
            }
        }
        watchlistJList.setModel(model);
    }
    
    private void loginUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        currentUser = new User(username, password);
        int result = currentUser.login();
        
        switch (result) {
            case 1:
                currentWatchlist = new Watchlist(username);
                getContentPane().removeAll();
                if (isAdmin(username, password)) {
                    showAdminInterface();
                } else {
                    showUserInterface();
                }
                break;
            case 0:
                currentWatchlist = new Watchlist(username);
                getContentPane().removeAll();
                showRoleSelection();
                break;
            case -1:
                JOptionPane.showMessageDialog(this, "Username or password format incorrect.");
                break;
            case -2:
                JOptionPane.showMessageDialog(this, "User not found.");
                break;
            case -3:
                JOptionPane.showMessageDialog(this, "Incorrect password.");
                break;
        }
    }
    
    private boolean isAdmin(String username, String password) {
        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }

    private void registerUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        currentUser = new User(username, password);
        int result = currentUser.register();
        switch (result) {
            case 1:
                JOptionPane.showMessageDialog(this, "Registration successful. Please log in.");
                usernameField.setText("");
                passwordField.setText("");
                break;
            case -1:
                JOptionPane.showMessageDialog(this, "User already exists.");
                break;
            case -2:
                JOptionPane.showMessageDialog(this, "Registration error.");
                break;
        }
    }

    private void showMovieDetailsDialog(Movie movie) {
        String details = "Title: " + movie.getTitle() + "\n" +
                "Director: " + movie.getDirector() + "\n" +
                "Release Year: " + movie.getReleaseYear() + "\n" +
                "Running Time: " + movie.getRunningTime() + " minutes";

        JOptionPane.showMessageDialog(this, details, "Movie Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showFilterPanel() {
        JDialog filterDialog = new JDialog(this, "Filter Movies", true);
        filterDialog.setLayout(new GridLayout(0, 2));
        filterDialog.setSize(500, 300);
    
        JTextField titleFilterField = new JTextField();
        JButton titleFilterButton = new JButton("Filter by Title");
        titleFilterButton.addActionListener(e -> filterMoviesByTitle(titleFilterField.getText()));
     
        JTextField directorFilterField = new JTextField();
        JButton directorFilterButton = new JButton("Filter by Director");
        directorFilterButton.addActionListener(e -> filterMoviesByDirector(directorFilterField.getText()));
    

        JButton yearFilterButton = new JButton("Sort by Year");
        yearFilterButton.addActionListener(e -> filterMoviesByYear());

        JButton timeFilterButton = new JButton("Sort by Running Time");
        timeFilterButton.addActionListener(e -> filterMoviesByRunningTime());

        filterDialog.add(titleFilterButton);

        filterDialog.add(directorFilterButton);
    
        filterDialog.add(yearFilterButton);
    
        filterDialog.add(timeFilterButton);
    
        filterDialog.setVisible(true);
    }

    private void filterMoviesByTitle(String title) {
        List<Movie> filteredMovies = movieDatabase.getMoviesByTitle(title);
        updateMovieListWithFilteredMovies(filteredMovies);
    }
    
    private void filterMoviesByDirector(String director) {
        List<Movie> filteredMovies = movieDatabase.getMoviesByDirector(director);
        updateMovieListWithFilteredMovies(filteredMovies);
    }
    
    private void filterMoviesByYear() {
        List<Movie> sortedMovies = movieDatabase.getMoviesByReleaseYear();
        updateMovieListWithFilteredMovies(sortedMovies);
    }
    
    private void filterMoviesByRunningTime() {
        List<Movie> sortedMovies = movieDatabase.getMoviesByRunningTime();
        updateMovieListWithFilteredMovies(sortedMovies);
    }

    private void updateMovieListWithFilteredMovies(List<Movie> movies) {
        DefaultListModel<Movie> model = new DefaultListModel<>();
        for (Movie movie : movies) {
            model.addElement(movie);
        }
        movieList.setModel(model);
    }
}
