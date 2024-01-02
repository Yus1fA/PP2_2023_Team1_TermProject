package MovieData;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Watchlist {
    private List<Movie> watchlist;
    private String username;

    public Watchlist(String username) {
        this.username = username;
        this.watchlist = new ArrayList<>();
        try {
            loadWatchlistFromFile();
        } catch (WatchlistNotExist e) {
            System.out.println(e.getMessage());
        }
    }

    public void addToWatchlist(Movie movie) {
        watchlist.add(movie);
        saveWatchlist();
    }

    public void removeFromWatchlist(Movie movie) {
        watchlist.remove(movie);
        saveWatchlist();
    }

    public List<Movie> getWatchlist() {
        return watchlist;
    }

    public List<Movie> sortMoviesByTitle() {
        return watchlist.stream()
                .sorted(Comparator.comparing(Movie::getTitle))
                .collect(Collectors.toList());
    }

    public void clearWatchlist() {
        watchlist.clear();
        saveWatchlist();
    }

    private void saveWatchlist() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Resources/Watchlists/" + username + "watchlist.txt"))) {
            for (Movie movie : watchlist) {
                writer.write(movie.getTitle() + ":" + movie.getDirector() + ":" + movie.getReleaseYear() + ":" + movie.getRunningTime() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadWatchlistFromFile() throws WatchlistNotExist {
        watchlist.clear();
        File watchlistFile = new File("Resources/Watchlists/" + username + "watchlist.txt");

        if (!watchlistFile.exists()) {
            throw new WatchlistNotExist("Watchlist does not exist for user: " + username);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(watchlistFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");

                if (parts.length == 4) {
                    String title = parts[0];
                    String director = parts[1];
                    int releaseYear = Integer.parseInt(parts[2]);
                    int runningTime = Integer.parseInt(parts[3]);

                    Movie movie = new Movie(title, director, releaseYear, runningTime);
                    watchlist.add(movie);
                }
            }
        } catch (IOException e) {
            throw new WatchlistNotExist("Error reading watchlist for user: " + username);
        }
    }
}