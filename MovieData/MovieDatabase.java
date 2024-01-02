package MovieData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import UserData.User;

public class MovieDatabase {
    private Map<String, Movie> movies;
    private Map<String, User> users;

    public MovieDatabase() {
        movies = new HashMap<>();
        users = new HashMap<>();
    }

    public void addMovie(String title, String director, int releaseYear, int runningTime) throws MovieAlreadyAdded {
        try {
            validateMovieInput(title, director, releaseYear, runningTime);

            String key = generateKey(title, director, releaseYear);
            if (!movies.containsKey(key)) {
                Movie newMovie = new Movie(title, director, releaseYear, runningTime);
                movies.put(key, newMovie);
                System.out.println("Movie added: " + newMovie.getTitle() + " (" + newMovie.getReleaseYear() + ")");
            } else {
                throw new MovieAlreadyAdded("Movie already exists in the database: " + title);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error adding movie: " + e.getMessage());
        }
    }

    public void removeMovie(String title, String director, int releaseYear) throws MovieNotExist {
        String key = generateKey(title, director, releaseYear);
        if (!movies.containsKey(key)) {
            throw new MovieNotExist("Movie does not exist: " + title);
        }
        movies.remove(key);
        System.out.println("Movie removed: " + title);
    }

    public Movie getMovie(String title, String director, int releaseYear) throws MovieNotExist {
        String key = generateKey(title, director, releaseYear);
        if (!movies.containsKey(key)) {
            throw new MovieNotExist("Movie does not exist: " + title);
        }
        return movies.get(key);
    }

    public List<Movie> getAllMovies() {
        return new ArrayList<>(movies.values());
    }

    private String generateKey(String title, String director, int releaseYear) {
        // Existing generateKey implementation
    }

    private void validateMovieInput(String title, String director, int releaseYear, int runningTime) {
        // Existing validateMovieInput implementation
    }
}

