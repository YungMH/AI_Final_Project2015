package tw.com.riversoft.core.util;


public class IsbnUtil {

  public static String calculateCheckSum10(String digits9) {
    
    int checkSum = 0;
    int sum = 0;
    int m = 10;
    for (int i=0; i<digits9.length(); i++) {
      sum = sum + Integer.parseInt(digits9.substring(i, i+1)) * m;
      m--;
    }
    
    int modRs = sum % 11;
    checkSum = 11 - modRs;
    
    if (checkSum == 11)
      return "0";
    else if (checkSum == 10) 
      return "X";
    else
      return String.valueOf(checkSum);
  }
  
  public static String calculateCheckSum13(String digits12) {
    
    int checkSum = 0;
    int sum = 0;
    for (int i=0; i<digits12.length()-1; i+=2) {
      int d1 = Integer.valueOf(digits12.substring(i, i+1)).intValue();
      int d2 = Integer.valueOf(digits12.substring(i+1, i+2)).intValue();
      sum = sum + (d1 * 1 + d2 * 3);
    }
    
    int rs = 10 - (sum % 10);
    if (rs < 10)
      checkSum = rs;
    
    return String.valueOf(checkSum);
  }
  
  
  public static void main(String[] args) {
    
//    System.out.println(calculateCheckSum13("978986012545"));
    System.out.println(calculateCheckSum10("957900948")); //1
    System.out.println(calculateCheckSum10("957900946")); //5
    System.out.println(calculateCheckSum10("957900868")); //X
    System.out.println(calculateCheckSum10("957900859")); //0
    System.out.println("97898612345678".substring(3,12));
  }
}
