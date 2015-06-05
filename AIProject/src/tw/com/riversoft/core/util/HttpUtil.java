package tw.com.riversoft.core.util;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * update version: 2011-02-13
 */
public class HttpUtil {
    
  public enum ParamEncoding {
    
    UTF8("UTF-8"), UTF16BE("UTF-16BE");
    
    private String encoding;
    
    ParamEncoding(String encoding) {
      this.encoding = encoding;
    }
    
    public String getEncoding() {
      return this.encoding;
    }
  }
  
  
 public static String sendRequest(String url, String method, Map<String, String> params) throws Exception {
    
    ParamEncoding encoding = ParamEncoding.UTF8;
    String bos = sendRequest(url, method, encoding, params);
    return bos;
  }
 
  /**
   * Simple HTTP request and response retriever
   * @param url is where you want to reach
   * @param method "POST", "PUT". currently no "GET" supported!!
   * @param params is a Map used as HTTP parameter
   * @return a String of remote response
   * @throws Exception
   */
  public static String sendRequest(String url, String method, ParamEncoding encoding, Map<String, String> params) throws Exception {
    
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    sendRequest(url, method, encoding, params, bos);
    return bos.toString("utf-8");
  }
  
  public static void sendRequest(String url, String method, Map<String, String> params, OutputStream callback) throws Exception {
    
    ParamEncoding encoding = ParamEncoding.UTF8;
    sendRequest(url, method, encoding, params, callback);
  }
  /**
   * @param url is where you want to reach
   * @param method "POST", "PUT". currently no "GET" supported!!
   * @param params is a Map used as HTTP parameter
   * @param callback Remote server response will be flushed to this call back OutputStream, please be aware to close it by yourself!!!
   * @throws Exception
   */
  public static void sendRequest(String url, String method, ParamEncoding encoding, Map<String, String> params, OutputStream callback) throws Exception {
    
    int paramLen = 0;
    String reqStr = null;
    if (params != null) {
      reqStr = convertParams(params, encoding.getEncoding());
      paramLen = reqStr.length();
      
      if (method.equalsIgnoreCase("get")) {
        url = url + "?" + reqStr;
      }
    }
    
    URL u = new URL(url); 
    HttpURLConnection rc = (HttpURLConnection) u.openConnection();
    rc.setRequestMethod(method);
    rc.setDoOutput(true);
    rc.setDoInput(true);
    rc.setInstanceFollowRedirects(false);
    
    if (method.equalsIgnoreCase("post")) {
      rc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
      rc.setRequestProperty("Content-Length", Integer.toString(paramLen));
    }
    
    rc.connect();
    
    if (method.equalsIgnoreCase("post") && params != null) {
      OutputStreamWriter out = new OutputStreamWriter(rc.getOutputStream()); 
      out.write(reqStr, 0, paramLen);
      out.flush();
    }

    BufferedInputStream inputStream = new BufferedInputStream(rc.getInputStream());
    int len = 0;
    byte[] buffer = new byte[1024];
    while((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
      callback.write(buffer, 0, len);
    }
    inputStream.close();
    rc.disconnect();
  }

  private static String convertParams(Map<String, String> params , String encoding) {
    
    StringBuffer s = new StringBuffer();
    
    Set keys = params.keySet();
    for (Iterator i=keys.iterator(); i.hasNext();) {
      String key = (String) i.next();
      String value = params.get(key).toString();
      try {
        s.append(key).append("=").append(URLEncoder.encode(value, encoding)).append("&");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      } 
    }
    
    s.delete(s.length()-1, s.length());
    return s.toString();
  }
  
  public static void main(String args[]) throws Exception {
    
    //http://www.random.org/strings/?num=10&len=10&digits=on&upperalpha=on&unique=on&format=plain&rnd=new
    
    Map valueMap = new HashMap();
    valueMap.put("num", "10");
//    valueMap.put("len", "10");
//    valueMap.put("digits", "on");
//    valueMap.put("upperalpha", "on");
//    valueMap.put("unique", "on");
//    valueMap.put("format", "plain");
//    valueMap.put("rnd", "new");
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    HttpUtil.sendRequest("http://leadallmyway.appspot.com/l", "POST",ParamEncoding.UTF8,  valueMap, bos);
    System.out.println(bos.toString());
    
  }
}