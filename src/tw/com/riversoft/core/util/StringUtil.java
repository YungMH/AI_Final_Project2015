package tw.com.riversoft.core.util;

import java.io.File;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;

public class StringUtil {

  public static boolean isNotEmpty(String str) {
    return !isEmpty(str);
  }
  
  public static boolean isEmpty(String str) {

    if (str != null && !str.equals("")) {
      return false;
    }
    return true;
  }
  
  public static String blankWhenNull(Object o) {

    if (o == null) {
      return "";
    } else {
      return o.toString();
    }
  }
  
  public static String makeUpZero(String number, int length) {
    
    StringBuffer s = new StringBuffer(number);
    int srcLength = number.length();
    for (int i=0; i<(length-srcLength); i++) {
      s.insert(0, "0");
    }
    return s.toString();
  }
  
  public static boolean isImageFile(String fileName) {
    
    fileName = fileName.toLowerCase();
    if (fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".jpeg") || fileName.endsWith(".gif"))
      return true;
    else
      return false;
  }
  
  public static boolean isZipFile(String fileName) {

    fileName = fileName.toLowerCase();
    if (fileName.endsWith(".zip")) {
      return true;
    } else {
      return false;
    }
  }
  
  
  public static boolean isPdfFile(String fileName) {

    fileName = fileName.toLowerCase();
    if (fileName.endsWith(".pdf")) {
      return true;
    } else {
      return false;
    }
  }
  
  public static String encryptPassword(String originalStr) {
    return DigestUtils.shaHex(DigestUtils.shaHex(originalStr));
  }
  
  /**
   * 以File.separator的分隔符號，組合一個完整的路徑，可以設定是否路徑首尾增加分隔符號
   * 沒有參數則回傳空白
   * 
   * Example:
   * 
   *  buildPath(true, true)
   *  return "";
   *  
   *  buildPath(true, true, "a")
   *  return \a\
   *  
   *  buildPath(false, false, "a", "b")
   *  return a\b\c
   *   
   * @param addFirst 是否加上首分隔符號(根目錄)
   * @param addLast 是否加上尾分隔符號
   * @param args 建置路徑的元素
   */
  public static String buildPath(boolean addFirst, boolean addLast, String... args ){
    
    StringBuffer path = new StringBuffer("");
    if (args != null && args.length > 0){
      path.append(args[0]);
      
      for(int index=1; index<args.length; index++)
          path.append(File.separator).append(args[index]);
      
      if(addFirst)
        path.insert(0, File.separator);
      if(addLast)
        path.insert(path.length(), File.separator);
    }
    return path.toString();
  }
  
  public static void main(String[] args) {
    
//    System.out.println(buildPath(false, false, "a", "b", "c"));
//    System.out.println(buildPath(true, false, "a", "b", "c"));
//    System.out.println(buildPath(false, false, "a"));
//    System.out.println(buildPath(false, true, "a"));
//    System.out.println(buildPath(true, true, "a"));
//    System.out.println(buildPath(true, true));
    System.out.println(encryptPassword("1234"));
   
    
  }
  
  /**
   * 以map批次對字串作文字取代 會取代掉格式為 {key} 的文字
   * @param template 要處理的字串資料 
   * @param params 取代文字的map
   * @return
   */
  public static String replaceTemplete(String template, Map<String, String> params) {

    for (Entry<String, String> entry : params.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      if (value == null) {
        value = "";
      }
      template = template.replaceAll("\\{" + key + "\\}", value.replaceAll("\\$", "\\\\\\$"));
    }
    
    return template;
  }

  public static String decimalFormat(BigDecimal decimal) {
    return String.format("%1.2f", decimal).replaceAll("\\.?0+$", "");
  }
  

  /**
   * 將一字串中非數字的字去掉
   * @param str
   * @return
   */
  public static String filterNonNumber(String str) {
      
    if(StringUtil.isNotEmpty(str)) {
      String regEx = "[^0-9]";
      Pattern p = Pattern.compile(regEx);
      Matcher m = p.matcher(str);
      return m.replaceAll("").trim();
    } else {
      return null;
    }
      
  }
}
