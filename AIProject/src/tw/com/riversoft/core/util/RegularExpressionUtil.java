package tw.com.riversoft.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpressionUtil {

	public static final String PATTERN_IDN = "^[A-Z]{1}[12]{1}[0-9]{8}$"; //簡易身分證認定
	public static final String ONLY_CHARACTER_ALLOWED = "\\w{1,}"; //只能輸入文字, 不能有特殊字元
	public static final String FORGE_ANCHER = "<a href=.+a>"; //尋找 <a href=... </a>
	public static final String ONLY_NUMBER = "[0-9.-]{1,}"; //只能輸入數字
	
	public static final Pattern PTN_IDN = Pattern.compile(PATTERN_IDN);
	public static final Pattern PTN_ONLY_CHARACTER_ALLOWED = Pattern.compile(ONLY_CHARACTER_ALLOWED);
	public static final Pattern PTN_ONLY_NUMBER = Pattern.compile(ONLY_NUMBER);
	
	public static void main(String[] args) {
	  
//	  System.out.println(match("^978{1,3}[0-9]{4,14}$", "9780123456789"));
//	  System.out.println(match("[0-9A-Za-z]{1,}", "我是訊息"));
//	  System.out.println(match("[0-9A-Za-z-]{1,}", "ABC-123"));
//	  System.out.println(match("[0-9A-Za-z-]{1,}", "001-"));
	  List<String> rs = fetchMatched("(http|https)://.*?/", "https://www.cnn.com:8080/a/b/c/");
	  for (String element : rs) {
	    String s = element.replaceFirst("(http|https)://", "").replaceAll("(:|/).*", "");
	    System.out.println(s);
	  }
	}
	
	public static boolean match(String thePattern, String sourceString) {
	  
	  if (sourceString == null)
	    return false;
	  
		Pattern pattern = Pattern.compile(thePattern);
    Matcher match = pattern.matcher(sourceString);
    return match.matches();
	}
	
	public static List<String> fetchMatched(Pattern pattern, String source) {
	  
	  List<String> matched = new ArrayList<String>();
    
    Matcher m = pattern.matcher(source);
  
    while (true) {
      if (m.find()) {
        matched.add(m.group());
      }
      else
        break;
    }
    
    return matched;
	}
	
	/**
	 * You are encouraged to use fetchMatched(Pattern pattern, String source), its performance will be better.
	 */
	public static List<String> fetchMatched(String spattern, String source) {
	
	  List<String> matched = new ArrayList<String>();
	  
	  Pattern p = Pattern.compile(spattern);
    Matcher m = p.matcher(source);
  
    while (true) {
      if (m.find()) {
        matched.add(m.group());
      }
      else
        break;
    }
    
    return matched;
	}
	
	public static List<String> fetchMatched(String spattern, String source, String delimeter) {
  	
		List<String> matched = new ArrayList<String>();
		
  	Pattern p = Pattern.compile(spattern);
  	
  	int position = 0;
  	int endIndex = 0;
  	
  	while ((endIndex = source.indexOf(delimeter, position)) != -1) {
  		int limit = endIndex + delimeter.length();
  		String toBeReplaced = source.substring(position, limit);
  		Matcher m = p.matcher(toBeReplaced);
    	while (true) {
    		if (m.find()) {
    			matched.add(m.group());
    		}
    		else
    			break;
    	}
    	position = limit;
  	}
  	
  	return matched;
  }
}