package tw.com.riversoft.core.struts2.converter;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import tw.com.riversoft.core.util.DateUtil;


public class DateConverter extends StrutsTypeConverter {

  private static final String PATTERN = DateUtil.PATTERN_YYYY_MM_DD;
  
  @Override
  /**
   * Converts one or more String values to the specified class.
   *
   * @param context the action context
   * @param values  the String values to be converted, such as those submitted from an HTML form
   * @param toClass the class to convert to
   * @return the converted object
   */
  public Object convertFromString(Map context, String[] values, Class toClass) {
    
    Date returnObject = null;
    String value = values[0];
    if (value != null && !value.trim().equals("")) {
      try {
        returnObject = DateUtil.parseDate(value, PATTERN);
      } catch (ParseException e) {
        // Just to ignore the parse exception
      }
    }
    return returnObject;
  }

  @Override
  /**
   * Converts the specified object to a String.
   *
   * @param context the action context
   * @param o       the object to be converted
   * @return the converted String
   */
  public String convertToString(Map context, Object o) {
    
    Date date = (Date) o;
    String formatedDate = DateUtil.dateFormater(date, PATTERN);
    return formatedDate;
  }
}
