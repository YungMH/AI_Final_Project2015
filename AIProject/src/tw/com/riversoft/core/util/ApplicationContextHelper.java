package tw.com.riversoft.core.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * TODO: APPLICATION_CONTEXT_QUARTZ 的路徑目前是Amazon EC2上面server的絕對路徑，要想辦法修改為相對路徑。或是可設定式的路徑。
 * @author matt
 *
 */
public final class ApplicationContextHelper {

  private static String APPLICATION_CONTEXT = "file:WebContent/WEB-INF/conf/spring/applicationContext.xml";
  private static String APPLICATION_CONTEXT_QUARTZ = "file:C/WEB-INF/conf/spring/applicationContextForQuartz.xml";
  
  private static AbstractApplicationContext applicationContext;
  
  public static ApplicationContext getApplicationContext() {
    return getApplicationContext(true);
  }
  
  public static ApplicationContext getApplicationContext(boolean initMockSession) {
    return getApplicationContext(initMockSession, APPLICATION_CONTEXT);
  }
  
  public static ApplicationContext getApplicationContextForQuartz() {
    return getApplicationContext(true, APPLICATION_CONTEXT_QUARTZ);
  }

  public ApplicationContextHelper() {
    // empty
  }

  @SuppressWarnings("unchecked")
  public static <T> T getBean(String id) {
    return (T) getApplicationContext().getBean(id);
  }
  
  private static ApplicationContext getApplicationContext(boolean initMockSession, String contextPath) {

    if (applicationContext == null) {
      ConfigurableResourceBaseApplicationContext.setResourceBase("/");
      applicationContext = new ConfigurableResourceBaseApplicationContext(contextPath);
      applicationContext.registerShutdownHook();
      if (initMockSession)
        ((ConfigurableResourceBaseApplicationContext)applicationContext).getBeanFactory().registerScope("session", new MockSessionScope());
    }
    return applicationContext;
  }
}