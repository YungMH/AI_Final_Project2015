package moives.entity;

public class Tags extends AbstractMoviesEntity {

	public static final String TABLE_NAME = "tags";
	
	private Long userid;
	private Long movieId;
	private String tag;
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

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
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
