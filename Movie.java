public class Movie {
    private String title;
    private String director;
    private int releaseYear;
    private int runningTime;

    public Movie() {
        this.title = "Untitled";
        this.director = "Unknown";
        this.releaseYear = 2023; // Default release year
        this.runningTime = 0;    // Default running time
    }

    public Movie(String title, String director, int releaseYear, int runningTime) {
        setTitle(title);
        setDirector(director);
        setReleaseYear(releaseYear);
        setRunningTime(runningTime);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty.");
        }
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        if (director == null || director.isEmpty()) {
            throw new IllegalArgumentException("Director cannot be null or empty.");
        }
        this.director = director;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        if (releaseYear <= 0) {
            throw new IllegalArgumentException("Release year must be positive.");
        }
        this.releaseYear = releaseYear;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(int runningTime) {
        if (runningTime < 0) {
            throw new IllegalArgumentException("Running time must be non-negative.");
        }
        this.runningTime = runningTime;
    }

    @Override
    public String toString() {
        return title + " [" + releaseYear + "] - Directed by " + director;
    }

    public String getMovieDetails() {
        return "Title: " + getTitle() + "\nDirector: " + getDirector() +"\nRelease Year: " + getReleaseYear() + "\nRunning Time: " + getRunningTime() + " minutes.";
    }
}
