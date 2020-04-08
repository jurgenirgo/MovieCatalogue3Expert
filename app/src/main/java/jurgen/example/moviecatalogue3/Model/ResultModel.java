package jurgen.example.moviecatalogue3.Model;

public class ResultModel {
    private String posterPath;
    private Integer id;
    private Object backdropPath;
    private String title;
    private Integer voteAverage;
    private String overview;
    private String releaseDate;

    public ResultModel(String posterPath, Integer id, Object backdropPath, String title, Integer voteAverage, String overview, String releaseDate) {
        this.posterPath = posterPath;
        this.id = id;
        this.backdropPath = backdropPath;
        this.title = title;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public Integer getId() {
        return id;
    }

    public Object getBackdropPath() {
        return backdropPath;
    }

    public String getTitle() {
        return title;
    }

    public Integer getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
