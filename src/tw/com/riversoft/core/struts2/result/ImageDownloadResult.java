package tw.com.riversoft.core.struts2.result;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StrutsResultSupport;
import org.springframework.stereotype.Component;

import tw.com.riversoft.core.context.AppContext;
import tw.com.riversoft.core.struts2.action.ImageDownloadAware;
import tw.com.riversoft.core.util.DateUtil;
import tw.com.riversoft.core.util.HttpUtil;
import tw.com.riversoft.core.util.StringUtil;

import com.opensymphony.xwork2.ActionInvocation;

@Component
public class ImageDownloadResult extends StrutsResultSupport {

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
//    HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext().get(HTTP_REQUEST);
    HttpServletResponse response = (HttpServletResponse) invocation.getInvocationContext().get(HTTP_RESPONSE);
    
//    response.setDateHeader("Expires", 0);
    
    ImageDownloadAware fileAware = (ImageDownloadAware) invocation.getAction();
    
    /*組出檔案所在路徑*/
    String filePath = fileAware.getTargetFilePath();
    Boolean hasDefaultImage = fileAware.getHasDefaultImage();
    
    if (StringUtil.isEmpty(filePath) && hasDefaultImage) {
      filePath = "../images/no-image.png";
    }
    else if (filePath == null) {
      filePath = "";
    }
    
    if (isHttp(filePath)) {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      HttpUtil.sendRequest(filePath, "GET", null, bos); // 由HttpUtil前往目的地位址抓回來後，再吐回給client side。traffic的時間增加一倍。
      response.setContentLength(bos.size());
      //response.setHeader("Last-Modified", formateDate(new Date()));
      Calendar threeMonthLater = DateUtil.now();
      threeMonthLater.add(Calendar.YEAR, 1);
      response.setHeader("Expires", formateDate(threeMonthLater.getTime()));
    
      byte[] byteData = bos.toByteArray();
      if (byteData.length > 0) {
        ServletOutputStream out = response.getOutputStream();
        out.write(bos.toByteArray());
        bos.close();
        out.flush();
        out.close();
        return ;
      } else {
        if (hasDefaultImage) {
          filePath = "../images/no-image.png";
        }
      }
    }
    //else {
    
      if (!filePath.startsWith("/")) {
        filePath = File.separator + filePath;
      }
      String storagePath = getAppContext().getAppRoot() + filePath;
      /*分離出檔案相對路徑和檔案名稱*/
      String fileName = storagePath.substring(storagePath.lastIndexOf("/") + 1);
      /*將相對路徑轉換為絕對路徑*/
      if (!getAppContext().getAbsolutePath()){
        storagePath = ServletActionContext.getServletContext().getRealPath(storagePath);
      }
      
      File targetFile = new File(storagePath);
      if (!targetFile.isFile()) {
        
        if (hasDefaultImage) {
          filePath = "../images/no-image.png";
        }
        if (!filePath.startsWith("/")) {
          filePath = File.separator + filePath;
        }
        storagePath = getAppContext().getAppRoot() + filePath;
        /*分離出檔案相對路徑和檔案名稱*/
        fileName = storagePath.substring(storagePath.lastIndexOf("/") + 1);
        /*將相對路徑轉換為絕對路徑*/
        if (!getAppContext().getAbsolutePath()){
          storagePath = ServletActionContext.getServletContext().getRealPath(storagePath);
        }
        
        targetFile = new File(storagePath);
      }

      if (targetFile.isFile()) {
        
        if (StringUtil.isImageFile(targetFile.getName())) {
          // do nothing
        } else if (StringUtil.isZipFile(targetFile.getName())) {
          targetFile = new File(getAppContext().getAppRoot() + "/../images/icon_zip.png");
          fileName = "icon_zip.png";
        } else if (StringUtil.isPdfFile(targetFile.getName())) {
          targetFile = new File(getAppContext().getAppRoot() + "/../images/icon_pdf.png");
          fileName = "icon_pdf.png";
        } else {
          targetFile = new File(getAppContext().getAppRoot() + "/../images/icon_file.png");
          fileName = "icon_file.png";
        }
        
        response.setContentLength((int)targetFile.length());
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
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
      
    //}
  }
  
  private boolean isHttp(String filePath) {
    
    if (filePath != null && filePath.startsWith("http"))
      return true;
    else
      return false;
  }
  
  /**
   * Append cache header if target file is image file
   * Related caching knowledge:
   * https://code.google.com/speed/page-speed/docs/caching.html#LeverageBrowserCaching
   */
  private void appendCacheHeader(HttpServletResponse response, File targetFile) {
    
    if (StringUtil.isImageFile(targetFile.getName())) {
      Calendar c = GregorianCalendar.getInstance();
      c.setTimeInMillis(targetFile.lastModified());
      
      response.setHeader("Last-Modified", formateDate(c.getTime()));
      c.add(Calendar.YEAR, 1);
      response.setHeader("Expires", formateDate(c.getTime()));
    }
  }
 
  private String formateDate(Date date) {
    return new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z").format(date);
  }
}
