package tw.com.riversoft.core.struts2.rule;

import java.io.File;
import java.util.List;

import tw.com.riversoft.core.entity.TemporaryUploaded;

public interface FileUploadAware {

  /**
   * 批次檔案上傳
   * Struts2上傳檔案後，會以File的格式存在，批次上傳多個檔案，所以以list的方式宣告
   * 並且封裝3個必需的屬性在 TemporaryUploaded 類別中
   */
  List<TemporaryUploaded> getTemporaryUploadeds();
  
  /**
   * Struts2上傳檔案後，會以File的格式存在。因為一次可以上傳多個檔案，所以以array的方式宣告
   */
  File[] getUploaded();

  /**
   * 上傳檔案的檔案mime type
   */
  String[] getUploadedContentType();

  /**
   * 上傳檔案的真實檔案名稱
   */
  String[] getUploadedFileName();

  /**
   * @deprecated
   * 檔案上傳後，會呼叫action的method，由action決定檔案是否應該被儲存
  Boolean[] isSaveAllowed();
   */

  /**
   * 設定
   */

  /**
   * 檔案上傳後，應該儲存的路徑。該路徑是相對於web root
   */
  String getRelativeStoragePath();

  /**
   * 設定檔案上傳的檔案陣列
   * @param uploaded
   */
  void setUploaded(File[] uploaded);

  /**
   * 設定檔案上傳的檔案型態陣列
   * @param uploadedContentType
   */
  void setUploadedContentType(String[] uploadedContentType);

  /**
   * 設定檔案上傳的檔案名稱陣列
   * @param uploadedFileName
   */
  void setUploadedFileName(String[] uploadedFileName);

  /**
   * @deprecated
   * 設定是否允許儲存上傳的檔案陣列
   * @param saveAllowed
  void setSaveAllowed(Boolean[] saveAllowed);
   */
}
