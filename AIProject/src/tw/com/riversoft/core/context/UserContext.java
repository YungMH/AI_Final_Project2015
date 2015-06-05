package tw.com.riversoft.core.context;

import java.io.Serializable;
import java.util.Map;

import modelet.context.Login;
import modelet.context.SessionContext;

import com.restfb.types.User;


public class UserContext implements SessionContext, Serializable {

  public static final String BEAN_ID = "defaultSessionContext";

  private String accessToken; // facebook的 accessToken
  private Login loginUser; // 登入者會員資料
  private User facebookUser; // 登入者facebook資料
  private Map<String, Object> categories; //暫存使用者選單
  

  public void clean() {

    setAccessToken(null);
    setLoginUser(null);
    setFacebookUser(null);
    setCategories(null);
  }
  
//  public String getLoginUserName() {
//    return getMemMember().getName();
//  }
  
  public String getAccessToken() {
    return accessToken;
  }
  
  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
  
  public Login getLoginUser() {
    return loginUser;
  }
  
  public void setLoginUser(Login loginUser) {
    this.loginUser = loginUser;
  }
  
  public User getFacebookUser() {
    return facebookUser;
  }
  
  public void setFacebookUser(User facebookUser) {
    this.facebookUser = facebookUser;
  }
  
  public Map<String, Object> getCategories() {
    return categories;
  }

  public void setCategories(Map<String, Object> categories) {
    this.categories = categories;
  }

  @Override
  public Login getLogin() {
    return getLoginUser();
  }

  @Override
  public void setLogin(Login arg0) {
    throw new RuntimeException("unsupported");
  }
  
}
