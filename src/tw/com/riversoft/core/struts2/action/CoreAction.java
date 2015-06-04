package tw.com.riversoft.core.struts2.action;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CoreAction extends ActionSupport implements ApplicationContextAware {

  protected static final Logger LOGGER = Logger.getLogger("completeLog");
  
  private ApplicationContext applicationContext;
  private String json;
  
  /**
   * TODO: 改成判斷真正的國別
   */
  public String getLocaleString() {
    return getLocale().toString().toLowerCase();
  }
  
  public String getValue(String expression) {
    return ActionContext.getContext().getValueStack().findString(expression + getLocaleString());
  }
  
  public String getJson() {
    return json;
  }

  public void setJson(String json) {
    this.json = json;
  }
  
  @Override
  public void setApplicationContext(ApplicationContext c) throws BeansException {
    this.applicationContext = c;
  }
  
  public static final class EType {
    public static final EType DISPATCHER = new EType();
    public static final EType CHAIN = new EType();
     
    private EType() {}
  }
}
