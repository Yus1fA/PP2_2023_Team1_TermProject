class Movie {
    private String title;
    private String director;
    private int releaseYear;
    private int runningTime;

    public Movie(String title, String director, int releaseYear, int runningTime) {
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.runningTime = runningTime;
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    String getDirector() {
        return director;
    }

    void setDirector(String director) {
        this.director = director;
    }

    int getReleaseYear() {
        return releaseYear;
    }

    void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    int getRunningTime() {
        return runningTime;
    }

    void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

   
    displayMovieDetails() {
        System.out.println("Title: " + title);
        System.out.println("Director: " + director);
        System.out.println("Release Year: " + releaseYear);
        System.out.println("Running Time: " + runningTime + " minutes");
    }
}


