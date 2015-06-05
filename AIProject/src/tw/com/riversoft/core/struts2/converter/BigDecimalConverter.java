package tw.com.riversoft.core.struts2.converter;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;


public class BigDecimalConverter extends StrutsTypeConverter {

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {

    BigDecimal bigDecimal = null;
    
    String value = values[0];

    if (value != null && !value.trim().equals("")) {
      bigDecimal = new BigDecimal(value);
    }
    return bigDecimal;
  }

  @Override
  public String convertToString(Map context, Object o) {

    String showValue = "";
    if (o != null) {
      BigDecimal bigDecimal = (BigDecimal) o;
      showValue = String.format("%1.2f", bigDecimal).replaceAll("\\.?0+$", "");
    }
    return showValue;
  }
}
