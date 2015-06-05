package tw.com.riversoft.core.struts2.action;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import tw.com.riversoft.core.context.AppContext;
import tw.com.riversoft.core.util.DateUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

@Controller
@Scope("prototype")
@ParentPackage("riversoft-default")
@Namespace("/")
@InterceptorRef("noneStack")
@ResultPath("/WEB-INF/pages/")
public class DefaultExceptionHandler extends CoreAction {

  private static final Logger exceptionLogger = Logger.getLogger("exceptionLog");
  
  private String dispatcherDest;
  private String chainDest;
  private AppContext appContext;
  
  
  @Action(value="defaultExceptionHandler", results={
                                             @Result(name="success1", type="dispatcher", location="${dispatcherDest}"),
                                             @Result(name="success2", type="chain", location="${chainDest}"),
                                             @Result(name="success3", type="dispatcher", location="exception/error.jsp"),
                                             @Result(name="success4", type="httpheader", params={"status","500","errorMessage","Please restart transaction."})
                                           }
  )
  public String execute() throws Exception {
   
    /*
     * Once exception occurred, Struts2's built in intercepter "ExceptionIntercepter" will push Exception into value stack.
     * So I can use "exception" to find exception just occurred.
     */
    ValueStack vs = ActionContext.getContext().getActionInvocation().getStack();
    
    Exception e = (Exception) vs.findValue("exception");
    if (e != null) {
      exceptionLogger.error(e.getMessage(), e);
      
      //發email
      StringBuffer message = new StringBuffer(); //最後要送出的mail內容
      
      for(StackTraceElement msg : e.getStackTrace()) {
        message.append("<br/>").append(msg.toString()) ; 
      }
      //getSmtpAgent().sendHtmlMail(null, new String[]{"winds0915@gmail.com"}, "ThankYouMyFriends發生Exception" + e, "Exception為:" + e + "<br/>" + message.toString(), getAppContext().getMailFrom());
    }
    
    return retrieveErrorDestincation();
  }

  private String retrieveErrorDestincation() {
    
    String returnCode = "success3";
    
    EType exceptionType = (EType) ActionContext.getContext().getActionInvocation().getStack().findValue("exceptionType");
    String exceptionLocation = ActionContext.getContext().getActionInvocation().getStack().findString("exceptionLocation");
    String isAjaxCall = ServletActionContext.getRequest().getHeader("Ajax-Call");
    
    if (isAjaxCall != null && isAjaxCall.equals("true")) {
      returnCode = "success4";
    }
    else if (exceptionType == EType.CHAIN) {
      setChainDest(exceptionLocation);
      returnCode = "success2";
    }
    else if (exceptionType == EType.DISPATCHER) {
      setDispatcherDest(exceptionLocation);
      returnCode = "success1";
    }
    return returnCode;
  }
  
  public String getDispatcherDest() {
    return dispatcherDest;
  }
  
  public void setDispatcherDest(String dispatcherDest) {
    this.dispatcherDest = dispatcherDest;
  }
  
  public String getChainDest() {
    return chainDest;
  }
  
  public void setChainDest(String chainDest) {
    this.chainDest = chainDest;
  }

  public AppContext getAppContext() {
    return appContext;
  }

  public void setAppContext(AppContext appContext) {
    this.appContext = appContext;
  }
  
}