package tw.com.riversoft.core.context;

import java.io.Serializable;

public class AppContext implements Serializable {

  private String appRoot; //應用系統做在作業系統之絕對路徑
  private String rootUrl; // 根網址
  private String imageFileNameExtensions;
  private Boolean absolutePath; //該appRoot是否為絕對路徑
  private String mailFrom; //mail寄送者

  
  public String getAppRoot() {
    return appRoot;
  }
  
  public void setAppRoot(String appRoot) {
    this.appRoot = appRoot;
  }
  
  public String getRootUrl() {
    return rootUrl;
  }

  public void setRootUrl(String rootUrl) {
    this.rootUrl = rootUrl;
  }

  public String getImageFileNameExtensions() {
    return imageFileNameExtensions;
  }
  
  public void setImageFileNameExtensions(String imageFileNameExtensions) {
    this.imageFileNameExtensions = imageFileNameExtensions;
  }
  
  public Boolean getAbsolutePath() {
    return absolutePath;
  }

  public void setAbsolutePath(Boolean absolutePath) {
    this.absolutePath = absolutePath;
  }

  public String getMailFrom() {
    return mailFrom;
  }
  
  public void setMailFrom(String mailFrom) {
    this.mailFrom = mailFrom;
  }
  
}