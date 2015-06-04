package movies.manager;

import modelet.model.DefaultModel;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractMoviesManager {

	protected static final Logger LOGGER_COMPLETE = Logger.getLogger("completeLog");
	  protected static final Logger LOGGER_EXCEPTION = Logger.getLogger("exceptionLog");
	  
	  @Autowired
	  private DefaultModel defaultModel;

	  public DefaultModel getDefaultModel() {
	    return defaultModel;
	  }
	  
	  public void setDefaultModel(DefaultModel defaultModel) {
	    this.defaultModel = defaultModel;
	  }
}
