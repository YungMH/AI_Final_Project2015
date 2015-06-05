package tw.com.riversoft.core.struts2.rule;

import java.util.Calendar;
import java.util.Date;

import tw.com.riversoft.core.util.DateUtil;

/**
 * 存放web service token 相關資訊的物件
 */
public class WebServiceToken {

  private String key; // web service的token key，用來讓webservice乎叫時，判斷access是否合法
  private Long memMember_id; // token 對應的會員資料
  private Date expireTime; // token 到期時間

  
  public String getKey() {
    return key;
  }
  
  public void setKey(String key) {
    this.key = key;
  }
  
  public Long getMemMember_id() {
    return memMember_id;
  }
  
  public void setMemMember_id(Long memMember_id) {
    this.memMember_id = memMember_id;
  }
  
  public Date getExpireTime() {
    return expireTime;
  }
  
  /**
   * 更新token到期時間
   */
  public void refreshExpireTime() {

      Calendar c = DateUtil.now();
      c.add(Calendar.MINUTE, 15);
      this.expireTime = c.getTime();
  }
  
}
