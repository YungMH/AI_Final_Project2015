package movies.manager;

import java.util.List;

import moives.entity.Links;
import moives.entity.Movies;

import org.springframework.stereotype.Service;

@Service("moviesManager")
public class MoviesManager extends AbstractMoviesManager {

	public List<Links> getLinksInfo() {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ").append(Links.TABLE_NAME);
		
		List<Links> rs = getDefaultModel().find(sql.toString(), null, Links.class);

		return rs;
	}
	
	public List<Movies> getMoviesInfo() {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ").append(Movies.TABLE_NAME);
		
		List<Movies> rs = getDefaultModel().find(sql.toString(), null, Movies.class);

		return rs;
	}
}
