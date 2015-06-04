package tw.com.riversoft.core.filter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


public class VerifyParameters implements Filter {
	
  protected static final Logger SECURITY_LOGGER = Logger.getLogger("securityLog");
  
	private List<String> illegalCharacters = new ArrayList<String>();
	private List<String> defaultCharacters = new ArrayList<String>();
	
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
//		String uri = ((HttpServletRequest)request).getRequestURI();
	
	  ParamValueModifiableWrapper requestWrapper = new ParamValueModifiableWrapper((HttpServletRequest)request);
	  
		if (hasIllegalParameterValue(request)) {
		  SECURITY_LOGGER.info("Found unfriendly character in request parameters.");
			cleanIllegalParameterValue(requestWrapper);
			//			cleanValueInAttributes(myWrapper);
			((HttpServletResponse)response).sendRedirect("/");
			//			((HttpServletRequest)request).getRequestDispatcher("/").forward(myWrapper, response);
		}
		else {
			chain.doFilter(requestWrapper, response);
		}
	}
	
	private boolean hasIllegalParameterValue(ServletRequest request) {
		
		boolean hasIllegalCharacter = false;
		Map parameters = request.getParameterMap();
		Set keys = parameters.keySet();
		Collection values = parameters.values();

		//check query string
		String queryString = ((HttpServletRequest)request).getQueryString();
		if (queryString != null) {
			for (int idx=0; idx<queryString.length(); idx++) {
				String c = String.valueOf(queryString.charAt(idx));
				if (this.defaultCharacters.contains(c)) {
					hasIllegalCharacter = true;
					break;
				}
			}
		}
		
		//test request URI has illegal value
		String uri = ((HttpServletRequest)request).getRequestURI();
		for (int idx=0; idx<uri.length(); idx++) {
			String c = String.valueOf(uri.charAt(idx));
			if (this.illegalCharacters.contains(c)) {
				hasIllegalCharacter = true;
				break;
			}
		}
		
		//test name has illegal value
		for (Iterator k=keys.iterator(); k.hasNext();) {
			String paramName = (String) k.next();
			for (int idx=0; idx<paramName.length(); idx++) {
				String c = String.valueOf(paramName.charAt(idx));
				if (this.illegalCharacters.contains(c)) {
					hasIllegalCharacter = true;
					break;
				}
			}
		}
		
		//test value has illegal value
		for (Iterator i=values.iterator(); i.hasNext();) {
			String[] sarray = (String[]) i.next();
			for (int idx=0; idx<sarray[0].length(); idx++) {
				String c = String.valueOf(sarray[0].charAt(idx));
				if (this.illegalCharacters.contains(c)) {
					hasIllegalCharacter = true;
					break;
				}
			}
		}
		return  hasIllegalCharacter;
	}


	public void init(FilterConfig config) throws ServletException {
		
	  SECURITY_LOGGER.info("Init parameter checking ...");
		
		String escaped = config.getInitParameter("characterToBeEscaped");
		if (escaped != null) {
			for (int i=0; i<escaped.length(); i++) {
				String c = String.valueOf(escaped.charAt(i));
				illegalCharacters.add(c);
			}
		}
		
		defaultCharacters.add("'");
		defaultCharacters.add("\"");
	}
	
	private void cleanIllegalParameterValue(ParamValueModifiableWrapper myWrapper) {
		myWrapper.getParameterMap().clear();
	}
	
	private void cleanValueInAttributes(ParamValueModifiableWrapper myWrapper) {
		
		Enumeration enums = myWrapper.getAttributeNames();
		while (enums.hasMoreElements()) {
			String attributeName = String.valueOf(enums.nextElement());
			myWrapper.removeAttribute(attributeName);
		}
	}
	
	public static void main(String[] args) {
		
//		String s = "!@#$%^&*()<>=,~/\\[]{}-";
		String s = ";&()<>{}";
		for (int i=0; i<s.length(); i++) {
			char c = s.charAt(i);
			int unsignedbyte = (int)c & 255;
			System.out.println(unsignedbyte);
		}
	}
}