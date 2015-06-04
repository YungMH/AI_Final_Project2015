package moives.entity;

import java.util.Date;

public class Links extends AbstractMoviesEntity {
	
	public static final String TABLE_NAME = "links";
	
	private Long movieId;
	private Long imdbId;
	private Long tmdbId;
	
	public Long getMovieId() {
		return movieId;
	}
	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}
	public Long getImdbId() {
		return imdbId;
	}
	public void setImdbId(Long imdbId) {
		this.imdbId = imdbId;
	}
	public Long getTmdbId() {
		return tmdbId;
	}
	public void setTmdbId(Long tmdbId) {
		this.tmdbId = tmdbId;
	}
	
	@Override
	public String getTableName() {
	    // TODO Auto-generated method stub
	   return TABLE_NAME;
	}

}
