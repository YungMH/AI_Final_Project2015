package moives.action;

import java.net.URLEncoder;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import tw.com.riversoft.core.context.AppContext;
import tw.com.riversoft.core.context.UserContext;
import tw.com.riversoft.core.struts2.action.CoreAction;
import tw.com.riversoft.core.util.StringUtil;

@Controller
@ParentPackage("riversoft-default")
@InterceptorRef("riversoftStack")
public class MoviesCoreAction extends CoreAction {
	
	@Autowired
	  private UserContext userContext;
	  
	  @Autowired
	  private AppContext appContext;
	  

	  public UserContext getUserContext() {
	    return userContext;
	  }

	  public void setUserContext(UserContext userContext) {
	    this.userContext = userContext;
	  }

	  public AppContext getAppContext() {
	    return appContext;
	  }

	  public void setAppContext(AppContext appContext) {
	    this.appContext = appContext;
	  }

	  public String getRootUrl() {

	    String s = getAppContext().getRootUrl();
	    
	    return s; //request.getRequestURL().toString().replaceAll("^([^/]+?://[^/]+?/[^/]+?/).*$", "$1");
	  }
	  
	  public String getHttpRootUrl() {
	    
	    return "http://" + getRootUrl();
	  }
	  
	  public String completeImagePath(String path) throws Exception {
	    
	    if (StringUtil.isNotEmpty(path)) {
	      return getHttpRootUrl() + URLEncoder.encode("/image?i=", "UTF-8") + path;
	    } else {
	      return "";
	    }
	  }
	  
}
