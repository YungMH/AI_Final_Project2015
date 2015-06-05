package tw.com.riversoft.core.filter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class ParamValueModifiableWrapper extends HttpServletRequestWrapper {

	private Map params;
	private boolean turnOnEncodingConvert = false;
	
	public ParamValueModifiableWrapper(HttpServletRequest request) {
		
		super(request);
		this.params = new HashMap(request.getParameterMap());
	}
	
	public ParamValueModifiableWrapper(HttpServletRequest request, boolean turnOnEncodingConvert) {
    
    super(request);
    this.params = new HashMap(request.getParameterMap());
    this.turnOnEncodingConvert = turnOnEncodingConvert;
  }

	public Map getParameterMap() {
		return this.params;
	}

	public String getParameter(String name) {
		
		String returnValue = null;
		String[] temp = (String[]) this.params.get(name);
		if (temp != null && temp.length > 0) {
			returnValue = temp[0];
		}
		if (turnOnEncodingConvert)
		  return forceConvertEncoding(returnValue);
		else
		  return returnValue; 
	}

	public String[] getParameterValues(String name) {

		String[] result = null;
		String[] temp = (String[]) this.params.get(name);
		if (temp != null) {
			result = new String[temp.length];
			System.arraycopy(temp, 0, result, 0, temp.length);
		}
		for (int i=0; i<result.length; i++) {
		  if (turnOnEncodingConvert)
		    result[i] = forceConvertEncoding(result[i]);
		  else
		    result[i] = result[i];
		}
		return result;
	}
	
	public void setParameter(String name, String value) {
		
    String[] oneParam = {value};
    setParameter(name, oneParam);
  }
  
  public void setParameter(String name, String[] values){
    params.put(name, values);   
  }
  
  private String forceConvertEncoding(String src) {
    
    String faceOff = src;
    try {
      faceOff = new String((src.getBytes("iso-8859-1")), "big5");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return faceOff;
  }
	
}