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
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.runningTime = runningTime;
    }

    public void displayMovieDetails() {
        System.out.println("Movie Details:");
        System.out.println("Title: " + title);
        System.out.println("Director: " + director);
        System.out.println("Release Year: " + releaseYear);
        System.out.println("Running Time: " + runningTime + " minutes");
    }
