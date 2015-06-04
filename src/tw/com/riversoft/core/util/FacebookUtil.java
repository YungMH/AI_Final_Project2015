package tw.com.riversoft.core.util;

import java.io.UnsupportedEncodingException;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class FacebookUtil {

  /**
   * 當Facebook登入成功，call back 我方指定的URL時，會伴隨signed request過來。從中可以判斷該fb user是否已經對Page已經按過了讚
   * @param signedRequest
   * @return
   */
  public boolean isPageLiked(String signedRequest) {
    
    boolean isLike = false;
    //it is important to enable url-safe mode for Base64 encoder 
    Base64 base64 = new Base64(true);
    
    //split request into signature and data
    String[] aSignedRequest = signedRequest.split("\\.", 2);
    
    //parse json object to java object
    //It is something look like this "{"algorithm":"HMAC-SHA256","issued_at":1333251649,"user":{"country":"tw","locale":"zh_TW","age":{"min":0,"max":12}}}"
    JSONObject data;
    try {
      data = (JSONObject) JSONSerializer.toJSON(new String(base64.decode(aSignedRequest[1].getBytes("UTF-8"))));
      isLike = data.getJSONObject("page").getBoolean("liked");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    
    return isLike;
  }
  
}
