import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieDatabase {
    private Map<String, Movie> movies;
    private Map<String, User> users;

    public MovieDatabase() {
        movies = new HashMap<>();
        users = new HashMap<>();
    }

    public void addMovie(String title, String director, int releaseYear, int runningTime) {
        try {
            validateMovieInput(title, director, releaseYear, runningTime);

            String key = generateKey(title, director, releaseYear);
            if (!movies.containsKey(key)) {
                Movie newMovie = new Movie(title, director, releaseYear, runningTime);
                movies.put(key, newMovie);
                System.out.println("Movie added: " + newMovie.getTitle() + " (" + newMovie.getReleaseYear() + ")");
            } else {
                System.out.println("Movie already exists in the database.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error adding movie: " + e.getMessage());
        }
    }

    public void removeMovie(String title, String director, int releaseYear) {
        try {
            validateMovieInput(title, director, releaseYear, 0);

            String key = generateKey(title, director, releaseYear);
            if (movies.containsKey(key)) {
                Movie removedMovie = movies.remove(key);
                System.out.println("Movie removed: " + removedMovie.getTitle() + " (" + removedMovie.getReleaseYear() + ")");
            } else {
                System.out.println("Movie not found in the database.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error removing movie: " + e.getMessage());
        }
    }

    public Movie getMovie(String title, String director, int releaseYear) {
        String key = generateKey(title, director, releaseYear);
        return movies.get(key);
    }

    public List<Movie> getAllMovies() {
        return new ArrayList<>(movies.values());
    }

    private String generateKey(String title, String director, int releaseYear) {
        return title + "_" + director + "_" + releaseYear;
    }

    private void validateMovieInput(String title, String director, int releaseYear, int runningTime) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty.");
        }

        if (director == null || director.isEmpty()) {
            throw new IllegalArgumentException("Director cannot be null or empty.");
        }

        if (releaseYear <= 0) {
            throw new IllegalArgumentException("Release year must be a positive value.");
        }

        if (runningTime < 0) {
            throw new IllegalArgumentException("Running time must be a non-negative value.");
        }
    }

    // Other methods for user and database management...

    public static void main(String[] args) {
        MovieDatabase movieDatabase = new MovieDatabase();
        movieDatabase.addMovie("Inception", "Christopher Nolan", 2010, 148);
        movieDatabase.addMovie("The Shawshank Redemption", "Frank Darabont", 1994, 142);
        Movie movie = movieDatabase.getMovie("Inception", "Christopher Nolan", 2010);
        if (movie != null) {
            movie.displayMovieDetails();
        }
        movieDatabase.removeMovie("The Shawshank Redemption", "Frank Darabont", 1994);
    }
}
