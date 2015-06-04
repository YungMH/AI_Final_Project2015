package tw.com.riversoft.core.struts2.result;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.StrutsResultSupport;

import com.opensymphony.xwork2.ActionInvocation;


public class JsonResult extends StrutsResultSupport {
  
  public static final String JSON = "json";
  
  private String encoding = "utf-8";

  protected void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {
    
    HttpServletResponse response = (HttpServletResponse) invocation.getInvocationContext().get(HTTP_RESPONSE);
    response.setHeader("Cache-Control","no-store, no-cache, must-revalidate,post-check=0, pre-check=0");
    //response.setHeader("Pragma","No-cache");
    response.setHeader("Pragma", "public");
    response.setHeader("Content-type","text/html; charset=" + encoding);
    response.setDateHeader("Expires",0 );
    String sb = (String) invocation.getStack().findValue(conditionalParse(JSON, invocation));
    PrintWriter oOutput = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), getEncoding()));
    oOutput.write(sb == null? "":sb.replaceAll("\t", " "));
    oOutput.flush();
    oOutput.close();
  }

  public String getEncoding() {
    return encoding;
  }

  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }
}
