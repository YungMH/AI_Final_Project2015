package tw.com.riversoft.core.util;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("randomUtil")
public final class RandomUtil {

  private static final Logger LOGGER = Logger.getLogger("completeLog");
  private static final Logger EXCEPTION_LOGGER = Logger.getLogger("exceptionLog");
  
  private Map<Key, LinkedList<String>> randoms = new HashMap<Key, LinkedList<String>>();
  
  /**
   * 一次取回的亂數數量
   */
  @Value("${util.random.num}")
  private int imageFileNum;
  
  /**
   * 辨識簡訊，每一個亂數字串的長度
   */
  @Value("${util.random.smsCode.len}")
  private int smsCodeLen;
  
  /**
   * 圖檔檔名，每一個亂數號碼的長度
   */
  @Value("${util.random.imageFile.len}")
  private int imageFileLen;
  
  /**
   * 送禮訂單交易序號，每一個亂數號碼的長度
   */
  @Value("${util.random.txnOrder.txnCode.len}")
  private int orderTxnCodeLen;
  
  /**
   * 禮物兌換序號，每一個亂數號碼的長度
   */
  @Value("${util.random.txnOrderItem.accessCode.len}")
  private int giftAccessCodeLen;
  
  /**
   * 重設登入密碼，亂數密碼的長度
   */
  @Value("${util.random.memMember.password.len}")
  private int memberPasswordLen;
  
  
  public RandomUtil() {
    
    randoms.put(Key.SMS_CODE_KEY, new LinkedList<String>());
    randoms.put(Key.IMAGE_KEY, new LinkedList<String>());
    randoms.put(Key.ORDER_TXN_CODE_KEY, new LinkedList<String>());
    randoms.put(Key.GIFT_ACCESS_CODE_KEY, new LinkedList<String>());
    randoms.put(Key.RESET_PASSWORD_KEY, new LinkedList<String>());
    randoms.put(Key.TXN_EVENT_CODE_KEY, new LinkedList<String>());
  }
  
  /**
   * 取得一組新的圖片檔案名稱
   * 請注意：圖片檔案名稱第一次抓取時，會因為圖片檔案名稱的暫存區是空的，會到www.random.org取得一大批的檔案名稱備用。
   * 因此第一次抓取時，因為加上等候random.org的回傳，時間會較久。第二次之後，直接從暫存區回傳檔案名稱，速度就會很快。
   * 直到暫存區所有檔案都用完為止，再到random.org 抓取下一批備用。
   * 
   * @return 一個長度為10 bytes的字串
   */
  public synchronized String getImageName() {
    
    if (randoms.get(Key.IMAGE_KEY).isEmpty()) {
      applyForImageFileNames();
    }
    return randoms.get(Key.IMAGE_KEY).pop();
  }
  
  /**
   * 
   * @return 一個長度為6 bytes大寫英數字字串
   */
  public synchronized String getSmsCode() {

    if (randoms.get(Key.SMS_CODE_KEY).isEmpty()) {
      applyForSmsCodes();
    }
    return randoms.get(Key.SMS_CODE_KEY).pop();
  }

  
  /**
   * 
   * @return 一個長度為12 bytes大寫英數字字串
   */
  public synchronized String getOrderTxnCode() {

    if (randoms.get(Key.ORDER_TXN_CODE_KEY).isEmpty()) {
      applyForOrderTxnCodes();
    }
    return randoms.get(Key.ORDER_TXN_CODE_KEY).pop();
  }
  
  /**
   * 
   * @return 一個長度為10 bytes數字字串
   */
  public synchronized String getGiftAccessCode() {

    if (randoms.get(Key.GIFT_ACCESS_CODE_KEY).isEmpty()) {
      applyForGiftAccessCodes();
    }
    return randoms.get(Key.GIFT_ACCESS_CODE_KEY).pop();
  }

  /**
   * 
   * @return 一個長度為6 bytes英數字字串
   */
  public synchronized String getResetPassword() {

    if (randoms.get(Key.RESET_PASSWORD_KEY).isEmpty()) {
      applyForResetPassword();
    }
    return randoms.get(Key.RESET_PASSWORD_KEY).pop();
  }

  /**
   * 
   * @return 一個長度為6 bytes數字字串
   */
  public synchronized String getTxnEventCode() {

    if (randoms.get(Key.TXN_EVENT_CODE_KEY).isEmpty()) {
      applyForTxnEventCode();
    }
    return randoms.get(Key.TXN_EVENT_CODE_KEY).pop();
  }

  /**
   * 向www.random.org 取得一整批的隨機字串, 作為簡訊辨識代號.
   * @param num 要取給組隨機亂數回來
   * @param len 每組亂數的長度
   */
  private void applyForSmsCodes() {
    
    Map<String, String> valueMap = new HashMap<String, String>();
    valueMap.put("num", String.valueOf(this.imageFileNum));
    valueMap.put("len", String.valueOf(this.smsCodeLen));
    valueMap.put("digits", "on");
    valueMap.put("upperalpha", "on");
    valueMap.put("loweralpha", "off");
    valueMap.put("format", "plain");
    
    applyForNewRandoms(valueMap, Key.SMS_CODE_KEY);
  }
  
  /**
   * 向www.random.org 取得一整批的隨機字串, 作為上傳檔案存檔時的檔名(不使用原始上傳的檔案名稱).
   * @param num 要取給組隨機亂數回來
   * @param len 每組亂數的長度
   */
  private void applyForImageFileNames() {
    
    Map<String, String> valueMap = new HashMap<String, String>();
    valueMap.put("num", String.valueOf(this.imageFileNum));
    valueMap.put("len", String.valueOf(this.imageFileLen));
    valueMap.put("digits", "on");
    valueMap.put("upperalpha", "on");
    valueMap.put("loweralpha", "off");
    valueMap.put("format", "plain");
    
    applyForNewRandoms(valueMap, Key.IMAGE_KEY);
  }
  
  /**
   * 向www.random.org 取得一整批的隨機字串, 作為送禮訂單交易序號
   * @param num 要取給組隨機亂數回來
   * @param len 每組亂數的長度
   */
  private void applyForOrderTxnCodes() {

    Map<String, String> valueMap = new HashMap<String, String>();
    valueMap.put("num", String.valueOf(this.imageFileNum));
    valueMap.put("len", String.valueOf(this.orderTxnCodeLen));
    valueMap.put("digits", "on");
    valueMap.put("upperalpha", "on");
    valueMap.put("loweralpha", "off");
    valueMap.put("format", "plain");
    
    applyForNewRandoms(valueMap, Key.ORDER_TXN_CODE_KEY);
  }
  
  /**
   * 向www.random.org 取得一整批的隨機字串, 作為禮物兌換序號
   * @param num 要取給組隨機亂數回來
   * @param len 每組亂數的長度
   */
  private void applyForGiftAccessCodes() {

    Map<String, String> valueMap = new HashMap<String, String>();
    valueMap.put("num", String.valueOf(this.imageFileNum));
    valueMap.put("len", String.valueOf(this.giftAccessCodeLen));
    valueMap.put("digits", "on");
    valueMap.put("upperalpha", "off");
    valueMap.put("loweralpha", "off");
    valueMap.put("format", "plain");
    
    applyForNewRandoms(valueMap, Key.GIFT_ACCESS_CODE_KEY);
  }

  /**
   * 向www.random.org 取得一整批的隨機字串, 作為重設密碼
   * @param num 要取給組隨機亂數回來
   * @param len 每組亂數的長度
   */
  private void applyForResetPassword() {

    Map<String, String> valueMap = new HashMap<String, String>();
    valueMap.put("num", String.valueOf(this.imageFileNum));
    valueMap.put("len", String.valueOf(this.memberPasswordLen));
    valueMap.put("digits", "on");
    valueMap.put("upperalpha", "off");
    valueMap.put("loweralpha", "on");
    valueMap.put("format", "plain");
    
    applyForNewRandoms(valueMap, Key.RESET_PASSWORD_KEY);
  }

  /**
   * 向www.random.org 取得一整批的隨機字串, 作為重設密碼
   * @param num 要取給組隨機亂數回來
   * @param len 每組亂數的長度
   */
  private void applyForTxnEventCode() {

    Map<String, String> valueMap = new HashMap<String, String>();
    valueMap.put("num", String.valueOf(this.imageFileNum));
    valueMap.put("len", String.valueOf(this.memberPasswordLen));
    valueMap.put("digits", "on");
    valueMap.put("upperalpha", "off");
    valueMap.put("loweralpha", "off");
    valueMap.put("format", "plain");
    
    applyForNewRandoms(valueMap, Key.TXN_EVENT_CODE_KEY);
  }

  private void applyForNewRandoms(Map<String, String> params, Key key) {
    
    //http://www.random.org/strings/?num=10&len=10&digits=on&upperalpha=on&unique=on&format=plain&rnd=new
    
    Map<String, String> valueMap = new HashMap<String, String>();
    valueMap.put("num", params.get("num"));
    valueMap.put("len", params.get("len"));
    valueMap.put("digits", params.get("digits")); //on off
    valueMap.put("upperalpha", params.get("upperalpha")); //on off
    valueMap.put("loweralpha", params.get("loweralpha")); //on off
    valueMap.put("unique", "on"); //on off
    valueMap.put("format", params.get("format")); //plain html
    valueMap.put("rnd", "new");
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    try {
      HttpUtil.sendRequest("http://www.random.org/strings/", "GET", valueMap, bos);
    } catch (Exception e) {
      e.printStackTrace();
      EXCEPTION_LOGGER.error("Error occured while retrieving random string from www.random.org", e);
    }
    
    String rs = bos.toString();
    LOGGER.info(rs);
    List<String> lrs = Arrays.asList(rs.split("\n"));
    randoms.put(key, new LinkedList<String>(lrs));
  }
  
  public int getImageFileNum() {
    return imageFileNum;
  }
  
  public void setImageFileNum(int imageFileNum) {
    this.imageFileNum = imageFileNum;
  }
  
  public int getImageFileLen() {
    return imageFileLen;
  }
  
  public void setImageFileLen(int imageFileLen) {
    this.imageFileLen = imageFileLen;
  }
  
  private static class Key {
    /**
     * 辨識簡訊使用的key
     */
    public static final Key SMS_CODE_KEY = new Key();
    /**
     * 上傳圖檔使用的key
     */
    public static final Key IMAGE_KEY = new Key();
    /**
     * 送禮訂單交易序號使用key
     */
    public static final Key ORDER_TXN_CODE_KEY = new Key();
    /**
     * 禮物兌換序號使用key
     */
    public static final Key GIFT_ACCESS_CODE_KEY = new Key();
    /**
     * 重設會員密碼使用key
     */
    public static final Key RESET_PASSWORD_KEY = new Key();
    /**
     * 產生活動序號使用key
     */
    public static final Key TXN_EVENT_CODE_KEY = new Key();

  }
}
