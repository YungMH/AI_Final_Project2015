package tw.com.riversoft.core.struts2.converter;

import java.util.Locale;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.ActionContext;


public class LocaleConverter extends StrutsTypeConverter {

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    
    Locale locale = (Locale) context.get(ActionContext.LOCALE);
    String value = values[0];
    if (value != null) {
      String[] fields = value.split("_");
      String language = fields[0];
      String country = fields[1];
      locale = new Locale(language, country);
    }
    return locale;
  }

  @Override
  public String convertToString(Map context, Object o) {
    
    Locale locale = (Locale) o;
    String language = locale.getLanguage();
    String country = locale.getCountry();
    StringBuffer s = new StringBuffer();
    s.append(language).append("_").append(country);
    return s.toString();
  }

}
