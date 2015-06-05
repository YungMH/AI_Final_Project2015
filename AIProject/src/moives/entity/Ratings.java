package moives.entity;

public class Ratings extends AbstractMoviesEntity {

	public static final String TABLE_NAME = "ratings";
	
	private Long userid;
	private Long movieId;
	private double rating;
	private int timestamp;
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Long getMovieId() {
		return movieId;
	}
	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public int getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public String getTableName() {
	    // TODO Auto-generated method stub
	   return TABLE_NAME;
	}
}
