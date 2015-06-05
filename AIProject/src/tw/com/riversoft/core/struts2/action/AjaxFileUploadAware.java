package tw.com.riversoft.core.struts2.action;

import java.io.File;

/**
 * 使用ajax方式上傳請implement這個interface
 */
public interface AjaxFileUploadAware {

  public File[] getUpload();
  public void setUpload(File[] upload);

  public String[] getUploadContentType();
  public void setUploadContentType(String[] uploadContentType);
  
  public String[] getUploadFileName();
  public void setUploadFileName(String[] uploadFileName);
}