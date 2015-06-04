package moives.action;

import java.util.List;

import moives.entity.Links;
import moives.entity.Movies;
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
	
	private List<Links> linksInfo;
	private List<Movies> moviesInfo;
	
	@Action(value="MoviesIndex", results={@Result(name=SUCCESS, type="tiles", location="movies/search")})
	public String Start() throws Exception {
		
		setMoviesInfo(getMoviesManager().getMoviesInfo());
		
		return SUCCESS;
	}

	public MoviesManager getMoviesManager() {
		return moviesManager;
	}

	public void setMoviesManager(MoviesManager moviesManager) {
		this.moviesManager = moviesManager;
	}

	public List<Links> getLinksInfo() {
		return linksInfo;
	}

	public void setLinksInfo(List<Links> linksInfo) {
		this.linksInfo = linksInfo;
	}

	public List<Movies> getMoviesInfo() {
		return moviesInfo;
	}

	public void setMoviesInfo(List<Movies> moviesInfo) {
		this.moviesInfo = moviesInfo;
	}
	
}		