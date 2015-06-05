package tw.com.riversoft.core.struts2.converter;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;


public class BooleanConverter extends StrutsTypeConverter {

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    
    String value = values[0];
    if (value != null && (value.equals("1") || value.equalsIgnoreCase("true") || value.equalsIgnoreCase("Y"))) {
      return Boolean.TRUE;
    }
    else {
      return Boolean.FALSE;
    }
  }

  @Override
  public String convertToString(Map context, Object o) {
    
    String showValue = "0";
    if (o != null) {
      if (((Boolean)o).booleanValue()) {
        showValue = "1";
      }
    }
    return showValue;
  }

}
