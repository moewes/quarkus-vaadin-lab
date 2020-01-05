package net.moewes.quarkus.vaadin;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.function.DeploymentConfiguration;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.ServiceException;
import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.server.VaadinServletService;
import com.vaadin.flow.server.startup.ApplicationRouteRegistry;
import java.io.IOException;
import java.util.logging.Logger;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QuarkusVaadinServlet extends VaadinServlet {

  Logger log = Logger.getLogger("MyVaadin");

  @Inject
  BeanManager beanManager;

  @Inject
  RegisteredRoutesBean myBean;

  private static final ThreadLocal<String> servletName = new ThreadLocal<>();

  @Override
  public void init(ServletConfig servletConfig) throws ServletException {
    try {
      ServletContext servletContext = servletConfig.getServletContext();

      ApplicationRouteRegistry routeRegistry = ApplicationRouteRegistry
          .getInstance(servletContext);

      RouteConfiguration routeConfiguration = RouteConfiguration.forRegistry(routeRegistry);

      for (String route : myBean.getRoutes()) {
        log.info("try to register " + route);
        try {
          ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
          Class<?> aClass = Class.forName(route, true, contextClassLoader);
          if (!routeConfiguration.isRouteRegistered((Class<? extends Component>) aClass)) {
            routeConfiguration.setAnnotatedRoute((Class<? extends Component>) aClass);
            log.info("sucess " + route);
          }
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        }
      }

      log.info("init");
      servletName.set(servletConfig.getServletName());
      super.init(servletConfig);
    } finally {
      servletName.set(null);
    }
  }

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      servletName.set(getServletName());
      super.service(request, response);
    } finally {
      servletName.set(null);
    }
  }

  /**
   * Name of the Vaadin servlet for the current thread.
   * <p>
   * Until VaadinService appears in CurrentInstance, it have to be used to get the servlet name.
   * <p>
   * This method is meant for internal use only.
   *
   * @return currently processing vaadin servlet name
   * @see VaadinServlet#getCurrent()
   */
  public static String getCurrentServletName() {
    return servletName.get();
  }

  @Override
  protected VaadinServletService createServletService(
      DeploymentConfiguration configuration) throws ServiceException {

    final QuarkusVaadinServletService service =
        new QuarkusVaadinServletService(this, configuration, beanManager);
    service.init();
    return service;
  }


}
