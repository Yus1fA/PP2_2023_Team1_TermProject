package GUIPart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import MovieData.Movie;
import MovieData.MovieDatabase;
import MovieData.Watchlist;
import UserData.User;
import UserData.UsernameLengthException;
import UserData.UserNotFoundException;
import UserData.UserExistedException;

public class UserInterface extends JFrame {
    private MovieDatabase movieDatabase;
    private User currentUser;
    private Watchlist currentWatchlist;

    private JPanel loginPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JList<Movie> movieList = new JList<>();
    private JList<Movie> watchlistJList; // Add this line

    public UserInterface() {
        movieDatabase = new MovieDatabase();
        currentUser = null;
        currentWatchlist = null;
        JPanel userPanel = new JPanel(new BorderLayout());

        // Initialize the movieList JList with an empty DefaultListModel
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

        add(loginPanel, BorderLayout.CENTER);
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
        JTextField titleField = new JTextField();
        JTextField directorField = new JTextField();
        JTextField releaseYearField = new JTextField();
        JTextField runningTimeField = new JTextField();

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

        // Use the class-level movieList for the movie list display
        JScrollPane movieListScrollPane = new JScrollPane(movieList);
        movieListScrollPane.setBorder(BorderFactory.createTitledBorder("Movie List"));
        adminPanel.add(movieListScrollPane, BorderLayout.CENTER);

        // Add an event listener to display movie details when a movie is selected
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
            JOptionPane.showMessageDialog(this, "Movie added successfully!");

            // Add the movie to the movieListModel
            DefaultListModel<Movie> movieListModel = (DefaultListModel<Movie>) movieList.getModel();
            movieListModel.addElement(new Movie(title, director, releaseYear, runningTime));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void removeMovie(String title, String director, int releaseYear) {
        try {
            movieDatabase.removeMovie(title, director, releaseYear);
    
            // Remove the movie from the movieListModel
            DefaultListModel<Movie> movieListModel = (DefaultListModel<Movie>) movieList.getModel();
            for (int i = 0; i < movieListModel.getSize(); i++) {
                Movie movie = movieListModel.getElementAt(i);
                if (movie.getTitle().equals(title) &&
                    movie.getDirector().equals(director) &&
                    movie.getReleaseYear() == releaseYear) {
                    movieListModel.remove(i);
                    break;
                }
            }
    
            JOptionPane.showMessageDialog(this, "Movie removed successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    

    private void updateMovieList(JList<Movie> movieList) {
        DefaultListModel<Movie> model = new DefaultListModel<>();
        for (Movie movie : movieDatabase.getAllMovies()) {
            model.addElement(movie);
        }
        movieList.setModel(model);
    }

    private void showUserInterface() {
        JPanel userPanel = new JPanel(new BorderLayout());

        // Initialize the movieList JList with an empty DefaultListModel
        DefaultListModel<Movie> movieListModel = new DefaultListModel<>();
        movieList = new JList<>(movieListModel); // Use the class-level movieList
        updateMovieList(movieList);
        JScrollPane movieListScrollPane = new JScrollPane(movieList);
        movieListScrollPane.setBorder(BorderFactory.createTitledBorder("Browse Movies"));
        userPanel.add(movieListScrollPane, BorderLayout.CENTER);

        JPanel watchlistPanel = new JPanel(new BorderLayout());
        watchlistJList = new JList<>(); // Initialize watchlistJList
        updateWatchlist(watchlistJList);
        JScrollPane watchlistScrollPane = new JScrollPane(watchlistJList);
        watchlistScrollPane.setBorder(BorderFactory.createTitledBorder("Your Watchlist"));
        watchlistPanel.add(watchlistScrollPane, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, userPanel, watchlistPanel);
        splitPane.setDividerLocation(300);

        setContentPane(splitPane);
        revalidate();
        repaint();
    }

    private void showMovieDetailsDialog(Movie movie) {
        // Create a dialog to display movie details
        String details = "Title: " + movie.getTitle() + "\n" +
                "Director: " + movie.getDirector() + "\n" +
                "Release Year: " + movie.getReleaseYear() + "\n" +
                "Running Time: " + movie.getRunningTime() + " minutes";

        JOptionPane.showMessageDialog(this, details, "Movie Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private void addToWatchlist(Movie selectedMovie) {
        if (selectedMovie != null) {
            currentWatchlist.addToWatchlist(selectedMovie);
            updateWatchlist(watchlistJList);
            JOptionPane.showMessageDialog(this, "Movie added to watchlist!");
        } else {
            JOptionPane.showMessageDialog(this, "Select a movie to add to watchlist.");
        }
    }

    private void updateWatchlist(JList<Movie> watchlistJList) {
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
        try {
            // Validate username and password here (e.g., length requirements)
            if (username.isEmpty() || password.isEmpty()) {
                throw new IllegalArgumentException("Username and password cannot be empty.");
            }

            currentUser = new User(username, password);
            currentUser.login();
            currentWatchlist = new Watchlist(username);
            getContentPane().removeAll();
            showRoleSelection();
        } catch (UserNotFoundException | UsernameLengthException e) {
            JOptionPane.showMessageDialog(this, "Login failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Validation error: " + e.getMessage());
        }
    }

    private void registerUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        try {
            // Validate username and password here (e.g., length requirements)
            if (username.isEmpty() || password.isEmpty()) {
                throw new IllegalArgumentException("Username and password cannot be empty.");
            }

            currentUser = new User(username, password);
            currentUser.register();
            currentWatchlist = new Watchlist(username);
            getContentPane().removeAll();
            showRoleSelection();
        } catch (UserExistedException | UsernameLengthException e) {
            JOptionPane.showMessageDialog(this, "Registration failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Validation error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserInterface());
    }
}
