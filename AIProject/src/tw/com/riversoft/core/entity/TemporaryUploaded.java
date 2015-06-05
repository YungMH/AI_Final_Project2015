package tw.com.riversoft.core.entity;

import java.io.File;

public class TemporaryUploaded {

  private File uploaded;
  private String uploadedContentType;
  private String uploadedFileName;
  private String uploadedPath;
  
  public File getUploaded() {

    return uploaded;
  }

  public void setUploaded(File uploaded) {

    this.uploaded = uploaded;
  }

  public String getUploadedContentType() {

    return uploadedContentType;
  }

  public void setUploadedContentType(String uploadedContentType) {

    this.uploadedContentType = uploadedContentType;
  }

  public String getUploadedFileName() {

    return uploadedFileName;
  }

  public void setUploadedFileName(String uploadedFileName) {

    this.uploadedFileName = uploadedFileName;
  }
  
  public String getUploadedPath() {
  
    return uploadedPath;
  }
  
  public void setUploadedPath(String uploadedPath) {
  
    this.uploadedPath = uploadedPath;
  }
}
