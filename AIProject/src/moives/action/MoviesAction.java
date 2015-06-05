package moives.action;

import java.util.List;
import java.util.Map;

import moives.entity.Links;
import moives.entity.Movies;
import movies.context.MoviesContext;
import movies.manager.MoviesManager;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@ParentPackage("riversoft-default")
@InterceptorRef("riversoftStack")
public class MoviesAction extends MoviesCoreAction {
	
	@Autowired
	MoviesManager moviesManager = new MoviesManager();
	
	@Autowired
	MoviesContext moviesContext = new MoviesContext();
	
	private List<Movies> moviesList;
	private List<Map<String, Object>> movieRating;
	
	@Action(value="MoviesIndex", results={@Result(name=SUCCESS, type="tiles", location="movies/search")})
	public String Start() throws Exception {
		
		if(getMoviesContext().getMoviesRating().isEmpty()) {
			//getMoviesContext().setMoviesList(getMoviesManager().getMovies());
			getMoviesContext().setMoviesRating(getMoviesManager().getMovieRatingWithTitle());
		}
		
		return SUCCESS;
	}

	public MoviesManager getMoviesManager() {
		return moviesManager;
	}

	public void setMoviesManager(MoviesManager moviesManager) {
		this.moviesManager = moviesManager;
	}

	public List<Movies> getMoviesList() {
		return moviesList;
	}

	public void setMoviesList(List<Movies> moviesList) {
		this.moviesList = moviesList;
	}

	public List<Map<String, Object>> getMovieRating() {
		return movieRating;
	}

	public void setMovieRating(List<Map<String, Object>> movieRating) {
		this.movieRating = movieRating;
	}

	public MoviesContext getMoviesContext() {
		return moviesContext;
	}

	public void setMoviesContext(MoviesContext moviesContext) {
		this.moviesContext = moviesContext;
	}
	
	
}		