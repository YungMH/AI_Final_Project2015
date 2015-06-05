package movies.manager;

import java.util.List;
import java.util.Map;

import moives.entity.Links;
import moives.entity.Movies;
import moives.entity.Ratings;
import moives.entity.Tags;

import org.springframework.stereotype.Service;

@Service("moviesManager")
public class MoviesManager extends AbstractMoviesManager {

	public List<Links> getLinksInfo() {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ").append(Links.TABLE_NAME);
		
		List<Links> rs = getDefaultModel().find(sql.toString(), null, Links.class);

		return rs;
	}
	
	public List<Movies> getMovies() {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ").append(Movies.TABLE_NAME);
		
		List<Movies> rs = getDefaultModel().find(sql.toString(), null, Movies.class);

		return rs;
	}
	
	public List<Ratings> getRating() {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ").append(Ratings.TABLE_NAME);
		
		List<Ratings> rs = getDefaultModel().find(sql.toString(), null, Ratings.class);

		return rs;
	}
	
	public List<Tags> getTags() {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ").append(Tags.TABLE_NAME);
		
		List<Tags> rs = getDefaultModel().find(sql.toString(), null, Tags.class);

		return rs;
	}
	
	public List<Map<String,Object>> getMovieRatingWithTitle() {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select m.movieId as movieId, title, genres, userId, rating, r.timestamp as timestamp from ").append(Movies.TABLE_NAME);
		sql.append(" m join ").append(Ratings.TABLE_NAME).append(" r on m.movieId = r.movieId");
		
		List<Map<String,Object>> rs = getDefaultModel().find(sql.toString(), null);
		
		return rs;
	}
}
