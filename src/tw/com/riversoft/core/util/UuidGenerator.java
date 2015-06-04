package tw.com.riversoft.core.util;



public class UuidGenerator {

  public static String generateUuid() {
    return org.safehaus.uuid.UUIDGenerator.getInstance().generateTimeBasedUUID().toString();
  }
  
}
