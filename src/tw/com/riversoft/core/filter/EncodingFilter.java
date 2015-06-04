package tw.com.riversoft.core.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class EncodingFilter implements Filter {

  private String encoding = null;
  
  public void destroy() {
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    
    request.setCharacterEncoding(this.encoding);
    response.setCharacterEncoding(this.encoding);
    
    chain.doFilter(request, response);
  }

  public void init(FilterConfig config) throws ServletException {
   
    this.encoding = config.getInitParameter("encoding");
  }

}