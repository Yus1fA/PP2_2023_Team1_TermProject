public class MovieClass {
    private String title;
    private String director;
    private int releaseYear;
    private int runningTime;

    public MovieClass(String title, String director, int releaseYear, int runningTime) {
        setTitle(title);
        setDirector(director);
        setReleaseYear(releaseYear);
        setRunningTime(runningTime);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }


    public void displayMovieDetails() {
        System.out.println("Title: " + getTitle());
        System.out.println("Director: " + getDirector());
        System.out.println("Release Year: " + getReleaseYear());
        System.out.println("Running Time: " + getRunningTime() + " minutes");
    }
}


