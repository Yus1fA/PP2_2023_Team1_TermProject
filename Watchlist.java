package MovieData;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Watchlist {
    private List<Movie> watchlist;
    private String username;

    public Watchlist(String username) {
        this.username = username;
        this.watchlist = new ArrayList<>();
        loadWatchlistFromFile();
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

    private void saveWatchlist() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Watchlists/" + username + "watchlist.txt"))) {
            for (Movie movie : watchlist) {
                writer.write(movie.getTitle() + ":" + movie.getDirector() + ":" + movie.getReleaseYear() + ":" + movie.getRunningTime() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadWatchlistFromFile() {
        watchlist.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("Watchlists/" + username + "watchlist.txt"))) {
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
            e.printStackTrace();
        }
    }
}
