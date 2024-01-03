package MovieData;

public class MovieAlreadyInWatchlistException extends Exception {
    public MovieAlreadyInWatchlistException(String message) {
        super(message);
    }
}