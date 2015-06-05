package tw.com.riversoft.core.struts2.interceptor;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StrutsRequestWrapper;

import tw.com.riversoft.core.context.AppContext;
import tw.com.riversoft.core.entity.TemporaryUploaded;
import tw.com.riversoft.core.struts2.rule.FileUploadAware;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * This interceptor must be placed after FileUploadInterceptor
 * 
 */
public class FileSaveInterceptor implements Interceptor {

  @Resource(name="appContext")
  private AppContext appContext;
  
  public AppContext getAppContext() {
    return appContext;
  }
  
  public void setAppContext(AppContext appContext) {
    this.appContext = appContext;
  }

  public void destroy() {

  }

  public void init() {

  }

  public String intercept(ActionInvocation invocation) throws Exception {

    Object action = invocation.getAction();

    String result = invocation.invoke();

    //Do save after action invoked.
    if (action instanceof FileUploadAware) {
      StrutsRequestWrapper wrapper = (StrutsRequestWrapper) ServletActionContext.getRequest();

      FileUploadAware aware = (FileUploadAware) action;
      
      List<TemporaryUploaded> temporaryUploadeds = aware.getTemporaryUploadeds();
      
      File[] uploaded = aware.getUploaded();
      String[] fileNames = aware.getUploadedFileName();
      // Boolean[] allowed = aware.isSaveAllowed();
      
      String relativeStorePath;
      String realPath;
      
      if (uploaded != null) {
        relativeStorePath = (aware.getRelativeStoragePath() == null) ? "temp" : aware.getRelativeStoragePath();
        //realPath = wrapper.getRealPath(relativeStorePath) + "/";
        realPath = appContext.getAppRoot() + "/" + relativeStorePath + "/";
        realPath = testToAddDelimiter(realPath);
        for (int i = 0; i < uploaded.length; i++) {
          //if (allowed[i].booleanValue()) {
            File uploadFile = (File) uploaded[i];
            if (uploadFile != null) {
              byte[] uploadContent = load(uploadFile);

              //write relative path file
              new File(realPath).mkdirs();
              OutputStream output = new BufferedOutputStream(new FileOutputStream(realPath + fileNames[i]));
              output.write(uploadContent);
              output.flush();
              output.close();
            }
          //}
        }
      } else if (temporaryUploadeds != null && temporaryUploadeds.size() > 0) {
        
        for (TemporaryUploaded tmp : temporaryUploadeds) {
            if (tmp != null && tmp.getUploaded() != null) {
              byte[] uploadContent = load(tmp.getUploaded());

              relativeStorePath = (tmp.getUploadedPath() == null) ? "temp" : tmp.getUploadedPath();
              //realPath = wrapper.getRealPath(relativeStorePath) + "/";
              realPath = appContext.getAppRoot() + "/" + relativeStorePath + "/";
              realPath = testToAddDelimiter(realPath);
              
              //write relative path file
              new File(realPath).mkdirs();
              OutputStream output = new BufferedOutputStream(new FileOutputStream(realPath + tmp.getUploadedFileName()));
              output.write(uploadContent);
              output.flush();
              output.close();
            }
        }
      }

    }

    return result;
  }

  private byte[] load(File file) throws IOException {

    InputStream in = new FileInputStream(file);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    byte[] buffer = new byte[512];
    int length;
    while ((length = in.read(buffer)) > 0) {
      out.write(buffer, 0, length);
    }
    in.close();
    return out.toByteArray();
  }

  private String testToAddDelimiter(String aim) {

    if (aim != null && aim.lastIndexOf("/") != aim.length() - 1)
      aim = aim + "/";

    return aim;
  }
}
