package movies.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import moives.entity.Movies;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class MoviesContext {

	private List<Movies> moviesList = new ArrayList<Movies>();
	private List<Map<String, Object>> moviesRating = new ArrayList<Map<String, Object>>();
	
	public List<Movies> getMoviesList() {
		return moviesList;
	}

	public void setMoviesList(List<Movies> moviesList) {
		this.moviesList = moviesList;
	}

	public List<Map<String, Object>> getMoviesRating() {
		return moviesRating;
	}

	public void setMoviesRating(List<Map<String, Object>> moviesRating) {
		this.moviesRating = moviesRating;
	}

}
