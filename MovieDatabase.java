import java.util.HashMap;
import java.util.Map;

public class MovieDatabase {
    private Map<String, Movie> movies;

    public MovieDatabase() {
        movies = new HashMap<>();
    }

    public void addMovie(String title, String director, int releaseYear, int runningTime) {
        String key = generateKey(title, director, releaseYear);
        if (!movies.containsKey(key)) {
            Movie newMovie = new Movie(title, director, releaseYear, runningTime);
            movies.put(key, newMovie);
            System.out.println("Movie added: " + newMovie.getTitle() + " (" + newMovie.getReleaseYear() + ")");
        } else {
            System.out.println("Movie already exists in the database.");
        }
    }

    public void removeMovie(String title, String director, int releaseYear) {
        String key = generateKey(title, director, releaseYear);
        if (movies.containsKey(key)) {
            Movie removedMovie = movies.remove(key);
            System.out.println("Movie removed: " + removedMovie.getTitle() + " (" + removedMovie.getReleaseYear() + ")");
        } else {
            System.out.println("Movie not found in the database.");
        }
    }

    public Movie getMovie(String title, String director, int releaseYear) {
        String key = generateKey(title, director, releaseYear);
        return movies.get(key);
    }

    private String generateKey(String title, String director, int releaseYear) {
        return title + "_" + director + "_" + releaseYear;
    }

    // For testing
    public static void main(String[] args) {
        MovieDatabase movieDatabase = new MovieDatabase();

        // Adding movies
        movieDatabase.addMovie("Inception", "Christopher Nolan", 2010, 148);
        movieDatabase.addMovie("The Shawshank Redemption", "Frank Darabont", 1994, 142);

        // Retrieving and printing movie details
        Movie movie = movieDatabase.getMovie("Inception", "Christopher Nolan", 2010);
        if (movie != null) {
            System.out.println("Movie details: " + movie.getTitle() + " (" + movie.getReleaseYear() + ")");
        }

        // Removing a movie
        movieDatabase.removeMovie("The Shawshank Redemption", "Frank Darabont", 1994);
    }
}
