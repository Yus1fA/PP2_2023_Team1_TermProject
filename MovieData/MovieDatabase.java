package MovieData;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class MovieDatabase {
    private Map<String, Movie> movies;
    private Set<Movie> watchlist;

    public MovieDatabase() {
        movies = new HashMap<>();
        watchlist = new HashSet<>();
    }

    public void addMovie(String title, String director, int releaseYear, int runningTime) throws MovieAlreadyAdded {
        validateMovieInput(title, director, releaseYear, runningTime);

        String key = generateKey(title, director, releaseYear);
        if (movies.containsKey(key)) {
            throw new MovieAlreadyAdded("Movie already exists in the database: " + title);
        }

        Movie newMovie = new Movie(title, director, releaseYear, runningTime);
        movies.put(key, newMovie);
        System.out.println("Movie added: " + newMovie.getTitle() + " (" + newMovie.getReleaseYear() + ")");
    }

    public void removeMovie(String title, String director, int releaseYear) throws MovieNotExist {
        String key = generateKey(title, director, releaseYear);
        if (!movies.containsKey(key)) {
            throw new MovieNotExist("Movie does not exist: " + title);
        }

        Movie removedMovie = movies.remove(key);
        System.out.println("Movie removed: " + removedMovie.getTitle());
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
                                .sorted(Comparator.comparing(Movie::getTitle))
                                .collect(Collectors.toList());
    }

    public List<Movie> getMoviesByDirector(String director) {
        return movies.values().stream()
                                .sorted(Comparator.comparing(Movie::getDirector))
                                .collect(Collectors.toList());
    }

    public List<Movie> getMoviesByReleaseYear() {
        return movies.values().stream()
                     .sorted(Comparator.comparingInt(Movie::getReleaseYear))
                     .collect(Collectors.toList());
    }

    public List<Movie> getMoviesByRunningTime() {
        return movies.values().stream()
                     .sorted(Comparator.comparingInt(Movie::getRunningTime))
                     .collect(Collectors.toList());
    }

    public void saveMoviesToFile(String filename) throws IOException {
        Path filePath = Paths.get(filename);
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (Movie movie : movies.values()) {
                writer.write(movie.getTitle() + ":" + movie.getDirector() + ":" + 
                             movie.getReleaseYear() + ":" + movie.getRunningTime());
                writer.newLine();
            }
        }
    }

    public void loadMoviesFromFile(String filename) throws IOException {
        Path filePath = Paths.get(filename);
        if (!Files.exists(filePath)) {
            filePath = Paths.get(filename);
        }
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                Movie movie = parseMovie(line);
                movies.put(generateKey(movie.getTitle(), movie.getDirector(), movie.getReleaseYear()), movie);
            }
        }
    }

    public void addToWatchlist(Movie movie) {
        if (movie != null) {
            watchlist.add(movie);
            System.out.println("Added '" + movie.getTitle() + "' to watchlist.");
        }
    }

    public void removeFromWatchlist(Movie movie) {
        if (movie != null && watchlist.contains(movie)) {
            watchlist.remove(movie);
            System.out.println("Removed '" + movie.getTitle() + "' from watchlist.");
        }
    }

    public List<Movie> getWatchlist() {
        return new ArrayList<>(watchlist);
    }

    public void deleteMovieFromDatabase(String title, String director, int releaseYear) throws MovieNotExist {
        String key = generateKey(title, director, releaseYear);
        if (!movies.containsKey(key)) {
            throw new MovieNotExist("Movie does not exist: " + title);
        }

        Movie deletedMovie = movies.remove(key);
        System.out.println("Movie removed from the database: " + deletedMovie.getTitle());
    }

    private Movie parseMovie(String line) throws IllegalArgumentException {
        String[] parts = line.split(":");
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
        return title + "-" + director + "-" + releaseYear;
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
