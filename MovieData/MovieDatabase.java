package MovieData;
import java.util.*;
import java.util.stream.Collectors;
import java.io.*;
import java.nio.file.*;
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

    public List<Movie> getMoviesByTitle(String title) {
        return movies.values().stream()
                     .filter(movie -> movie.getTitle().equalsIgnoreCase(title))
                     .collect(Collectors.toList());
    }

    public List<Movie> getMoviesByDirector(String director) {
        return movies.values().stream()
                    .filter(movie -> movie.getDirector().equals(director))
                    .collect(Collectors.toList());
    }

    public List<Movie> getMoviesByReleaseYear(int releaseYear) {
        return movies.values().stream()
                     .filter(movie -> movie.getReleaseYear() == releaseYear)
                     .collect(Collectors.toList());
    }

    public List<Movie> getMoviesByRunningTime(int runningTime) {
        return movies.values().stream()
                     .filter(movie -> movie.getRunningTime() == runningTime)
                     .collect(Collectors.toList());
    }

    public void saveMoviesToFile(String filename) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("Resources//MovieDatabase.txt"))) {
            for (Movie movie : movies.values()) {
                writer.write(movie.toString());
                writer.newLine();
            }
        }
    }

    public void loadMoviesFromFile(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("Resources/MovieDatabase.txt"));
        for (String line : lines) {
            Movie movie = parseMovie(line);
            movies.put(generateKey(movie.getTitle(), movie.getDirector(), movie.getReleaseYear()), movie);
        }
    }

    private Movie parseMovie(String line) {
        String[] parts = line.split(",");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid movie format.");
        }
    
        String title = parts[0];
        String director = parts[1];
        int releaseYear = Integer.parseInt(parts[2]);
        int runningTime = Integer.parseInt(parts[3]);
    
        return new Movie(title, director, releaseYear, runningTime);
    }

    private String generateKey(String title, String director, int releaseYear) {
        return title + "" + director + "" + releaseYear;
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
}
