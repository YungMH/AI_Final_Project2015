package tw.com.riversoft.core.entity;

/**
 * 圖片資訊entity都要implement此 interface
 */
public interface HasImage {

  // 真正存放圖片的路徑
  public String getPath(); 
  public void setPath(String path);
  
  // 圖片檔案存放的folder name
  public String getFolderName();
  
  public Long getId();
}
