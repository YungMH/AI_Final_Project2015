package tw.com.riversoft.core.struts2.result;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.StrutsResultSupport;
import org.springframework.stereotype.Component;

import tw.com.riversoft.core.context.AppContext;

import com.opensymphony.xwork2.ActionInvocation;

@Component
public class DynamicDownloadResult extends StrutsResultSupport {

  @Resource(name="appContext")
  private AppContext appContext;
  
  public AppContext getAppContext() {
    return appContext;
  }
  
  public void setAppContext(AppContext appContext) {
    this.appContext = appContext;
  }
  
  @Override
  protected void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {
  
    /*取得 http request & response*/
    HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext().get(HTTP_REQUEST);
    HttpServletResponse response = (HttpServletResponse) invocation.getInvocationContext().get(HTTP_RESPONSE);
    
    /*組出檔案所在路徑*/
    String targetPath = request.getParameter("f");
    if (!targetPath.startsWith("/")) {
      targetPath = "/" + targetPath; 
    }
    
    String storagePath = getAppContext().getAppRoot() + targetPath;
    /*分離出檔案相對路徑和檔案名稱*/
    String fileName = storagePath.substring(storagePath.lastIndexOf("/") + 1);
    
    String mimeType = detectMimeType(fileName);

    File targetFile = new File(storagePath);
    if (targetFile.isFile()) {
  
      response.setContentLength((int)targetFile.length());
      response.setContentType(mimeType);
      response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "utf-8"));
      //appendCacheHeader(response, targetFile);
      
      InputStream in = new BufferedInputStream(new FileInputStream(targetFile));
      ServletOutputStream out = response.getOutputStream();
      
      int len = 0;
      byte[] buffer = new byte[1024];
      while((len = in.read(buffer, 0, buffer.length)) != -1) {
        out.write(buffer, 0, len);
      }
      in.close();
      out.flush();
      out.close();
    }
      
  }
  
  private String detectMimeType(String fileName) {

    String mimeType = "";
    if (fileName.endsWith(".doc"))
      mimeType = "application/msword";
    else if (fileName.endsWith(".xls"))
      mimeType = "application/vnd.ms-excel";
    else if (fileName.endsWith(".pdf"))
      mimeType = "application/pdf";
    else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg"))
      mimeType = "image/jpeg";

    return mimeType;
  }
  
}
