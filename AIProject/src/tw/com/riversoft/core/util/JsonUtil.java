package tw.com.riversoft.core.util;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public final class JsonUtil {

  public static String toJson(Object obj) {
    
    Gson g = initGson();
    return g.toJson(obj);
  }
  
  public static <T> T fromJson(String json, Type collectionType) {
    
    T obj = initGson().fromJson(json, collectionType);
    return obj;
  }
  
  public static <T> T fromJson(String json, Class<T> clazz) {
   
    T obj = initGson().fromJson(json, clazz);
    return obj;
  }
  
  private static Gson initGson() {
   
    Gson g = new GsonBuilder()
    .setDateFormat("yyyy-MM-dd") 
    .create();
    /*HH:mm:ss*/
    return g;
  }

}