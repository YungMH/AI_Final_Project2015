package moives.entity;

public class Movies extends AbstractMoviesEntity {

	public static final String TABLE_NAME = "movies";
	
	private Long movieId;
	private String title;
	private String genres;
	
	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenres() {
		return genres;
	}

	public void setGenres(String genres) {
		this.genres = genres;
	}

	@Override
	public String getTableName() {
	    // TODO Auto-generated method stub
	   return TABLE_NAME;
	}
}
