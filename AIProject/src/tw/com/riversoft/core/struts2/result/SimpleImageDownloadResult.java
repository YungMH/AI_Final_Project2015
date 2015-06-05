package tw.com.riversoft.core.struts2.result;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.dispatcher.StrutsResultSupport;
import org.springframework.stereotype.Component;

import tw.com.riversoft.core.context.AppContext;
import tw.com.riversoft.core.util.StringUtil;

import com.opensymphony.xwork2.ActionInvocation;

@Component
public class SimpleImageDownloadResult extends StrutsResultSupport {

  protected static final Logger EXCEPTION_LOGGER = Logger.getLogger("exceptionLog");
  
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
    String targetPath = request.getParameter("i");
    if (!targetPath.startsWith("/")) {
      targetPath = "/" + targetPath; 
    }
    
    StringBuffer coverImagePath = new StringBuffer();
    coverImagePath.append(appContext.getAppRoot()).append(targetPath);
    File targetFile = new File(coverImagePath.toString());
    if (!targetFile.exists()) {
      coverImagePath = new StringBuffer();
      coverImagePath.append(appContext.getAppRoot()).append("/../images/no-image.png");
    }

    if (targetFile.isFile()) {
  
      try {
        
        if (StringUtil.isImageFile(targetFile.getName())) {
          // do nothing
        } else if (StringUtil.isZipFile(targetFile.getName())) {
          targetFile = new File(getAppContext().getAppRoot() + "/../images/icon_zip.png");
          targetPath = "icon_zip.png";
        } else if (StringUtil.isPdfFile(targetFile.getName())) {
          targetFile = new File(getAppContext().getAppRoot() + "/../images/icon_pdf.png");
          targetPath = "icon_pdf.png";
        } else {
          targetFile = new File(getAppContext().getAppRoot() + "/../images/icon_file.png");
          targetPath = "icon_file.png";
        }
        
        response.setContentLength((int)targetFile.length());
        response.setHeader("Content-Disposition", "inline; filename=" + targetPath);
        appendCacheHeader(response, targetFile);
        
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
      catch (IOException e) {
        EXCEPTION_LOGGER.info("Write image back to client got error, it could because of client abort connection.");
        EXCEPTION_LOGGER.error(e);
      }
    }
  }
  
  /**
   * Append cache header if target file is image file
   * Related caching knowledge:
   * https://code.google.com/speed/page-speed/docs/caching.html#LeverageBrowserCaching
   */
  private void appendCacheHeader(HttpServletResponse response, File targetFile) {
    
    Calendar c = GregorianCalendar.getInstance();
    c.setTimeInMillis(targetFile.lastModified());
    
    response.setHeader("Last-Modified", formateDate(c.getTime()));
    c.add(Calendar.DATE, 2);
    response.setHeader("Expires", formateDate(c.getTime()));
  }
  
  private String formateDate(Date date) {
    return new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z").format(date);
  }
}