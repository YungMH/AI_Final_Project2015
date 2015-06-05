package tw.com.riversoft.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Component;

@Component
public class FileUtil {

  /**
   * 刪除該目錄(dirPath)及底下所有目錄及檔案，不是目錄則不會有動作 該目錄下如有開啟中的檔案，則該檔及所屬目錄將不會被刪除
   * 
   * @param 欲刪除的目錄
   * @return 刪除檔案的數目 - 不包含目錄
   */
  public static int deleteDirectory(File dir) {

    if (null == dir) {
      throw new IllegalArgumentException("null directory.");
    }
    if (!dir.exists() || !dir.canWrite() || !dir.isDirectory()) {
      return 0;
    }

    int result = 0;
    for (File file : dir.listFiles()) {
      if (file.isDirectory()) {
        deleteDirectory(file);// recursion
      }
      file.delete();
      result++;
    }
    dir.delete();
    return result;
  }
  
  /**
   * 讀取檔案，回傳utf-8純文字內容
   * @param path
   * @return
   * @throws Exception
   */
  public static String readFile(String path) throws Exception {

    File file = new File(path);
    
    InputStreamReader r = new InputStreamReader(new FileInputStream(file), "utf-8");
    char[] buffer = new char[1024];
    StringBuffer sb = new StringBuffer();
    int len = 0;
    
    while((len = r.read(buffer, 0, buffer.length)) != -1) {
      sb.append(buffer, 0, len);
    }
    r.close();
    return sb.toString();
  }
  
 public static byte[] prependBOMWhenUTF8(byte[] content ,String encode) throws UnsupportedEncodingException {
    
    if ("utf-8".equals(encode)) {
      final char[] UTF8_BOM = {'\uFEFF'};
      String ss = new String(UTF8_BOM);
      byte[] bom = ss.getBytes(encode);// { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF }; //
      byte[] combined = new byte[bom.length + content.length];
      System.arraycopy(bom, 0, combined, 0, bom.length);
      System.arraycopy(content, 0, combined, bom.length, content.length);
      return combined;
    }
    return content;
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {

    File dir = new File(StringUtil.buildPath(false, false, "c:", "a"));
    // deleteBelow(dir);
    System.out.println(deleteDirectory(dir));
  }

}
