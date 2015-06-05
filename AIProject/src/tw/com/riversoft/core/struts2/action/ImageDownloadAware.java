package tw.com.riversoft.core.struts2.action;

public interface ImageDownloadAware {

  public String getTargetFilePath(); // 圖片路徑
  public void setTargetFilePath(String filePath);
  
  public Boolean getHasDefaultImage(); // 如果沒有找到圖片檔案，是否顯示預設圖片
  public void setHasDefaultImage(Boolean hasDefaultImage);
  
  //not in use, so delete it
//  public String getTargetFileName();
//  public void setTargetFileName(String fileName);
}