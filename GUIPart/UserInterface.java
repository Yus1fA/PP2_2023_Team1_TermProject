package GUIPart;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import MovieData.Movie;
import MovieData.MovieDatabase;
import UserData.User;

public class UserInterface extends JFrame {
    private MovieDatabase movieDatabase;
    private User currentUser;

    public UserInterface() {
        movieDatabase = new MovieDatabase();
        currentUser = null;

        JLabel welcome = new JLabel("Welcome to Movie Database!");
        add(welcome, BorderLayout.NORTH);

        JTabbedPane tabbed = new JTabbedPane();

        JPanel browse = new JPanel(new BorderLayout());
        JList<Movie> movieList = new JList<>();
        updateMovieList(movieList);
        JScrollPane movieListScrollPane = new JScrollPane(movieList);
        movieListScrollPane.setBorder(BorderFactory.createTitledBorder("Movies"));
        browse.add(movieListScrollPane, BorderLayout.CENTER);
        tabbed.addTab("Browse Movies", browse);

        JPanel watchlist = new JPanel(new BorderLayout());
        JList<Movie> watchlistJList = new JList<>();
        updateWatchlist(watchlistJList);
        JScrollPane watchlistScrollPane = new JScrollPane(watchlistJList);
        watchlistScrollPane.setBorder(BorderFactory.createTitledBorder("Watchlist"));
        watchlist.add(watchlistScrollPane, BorderLayout.CENTER);
        tabbed.addTab("Watchlist", watchlist);

        JPanel registration = new JPanel(new BorderLayout());
        JButton register = new JButton("Register");
        JButton login = new JButton("Login");

        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentUser == null) {
                    JOptionPane.showMessageDialog(null, "Please register or log in first.");
                } else {
                    Movie selectedMovie = movieList.getSelectedValue();
                    if (selectedMovie != null) {
                        currentUser.addToWatchlist(selectedMovie);
                        updateWatchlist(watchlistJList);
                        JOptionPane.showMessageDialog(null, "Movie added to watchlist!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Select a movie to add to watchlist.");
                    }
                }
            }
        });

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               if (currentUser == null) {
                    JOptionPane.showMessageDialog(null, "Please register or log in first.");
                } else {
                    Movie selectedMovie = watchlistJList.getSelectedValue();
                    if (selectedMovie != null) {
                        currentUser.removeFromWatchlist(selectedMovie);
                        updateWatchlist(watchlistJList);
                        JOptionPane.showMessageDialog(null, "Movie removed from watchlist!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Select a movie to remove from watchlist.");
                    }
                }
            }
        });

        registration.add(register, BorderLayout.WEST);
        registration.add(login, BorderLayout.EAST);
        tabbed.addTab("Registration", registration);

        add(tabbed, BorderLayout.CENTER);

        JButton addWatchlist = new JButton("Add to Watchlist");
        watchlist.add(addWatchlist, BorderLayout.SOUTH);

        addWatchlist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentUser == null) {
                    JOptionPane.showMessageDialog(null, "Please register or log in first.");
                } else {
                    Movie selectedMovie = movieList.getSelectedValue();
                    if (selectedMovie != null) {
                        currentUser.addToWatchlist(selectedMovie);
                        updateWatchlist(watchlistJList);
                        JOptionPane.showMessageDialog(null, "Movie added to watchlist!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Select a movie to add to watchlist.");
                    }
                }
            }
        });

        JButton removeWatchlist = new JButton("Remove from Watchlist");
        watchlist.add(removeWatchlist, BorderLayout.NORTH);

        removeWatchlist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentUser == null) {
                    JOptionPane.showMessageDialog(null, "Please register or log in first.");
                } else {
                    Movie selectedMovie = watchlistJList.getSelectedValue();
                    if (selectedMovie != null) {
                        currentUser.removeFromWatchlist(selectedMovie);
                        updateWatchlist(watchlistJList);
                        JOptionPane.showMessageDialog(null, "Movie removed from watchlist!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Select a movie to remove from watchlist.");
                    }
                }
            }
        });

        setTitle("Movie Database");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(400, 400);
        setVisible(true);
    }

    private void updateMovieList(JList<Movie> movieList) {
        List<Movie> movies = new ArrayList<>(movieDatabase.getAllMovies());
        DefaultListModel<Movie> listModel = new DefaultListModel<>();
        for (Movie movie : movies) {
            listModel.addElement(movie);
        }
        movieList.setModel(listModel);
    }

    private void updateWatchlist(JList<Movie> watchlistJList) {
        if (currentUser != null) {
            List<Movie> watchlist = new ArrayList<>(currentUser.getWatchlist());
            DefaultListModel<Movie> listModel = new DefaultListModel<>();
            for (Movie movie : watchlist) {
                listModel.addElement(movie);
            }
            watchlistJList.setModel(listModel);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserInterface());
    }
}

