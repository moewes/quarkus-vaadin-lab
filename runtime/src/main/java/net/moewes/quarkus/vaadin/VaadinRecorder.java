package net.moewes.quarkus.vaadin;

import io.quarkus.arc.runtime.BeanContainer;
import io.quarkus.runtime.annotations.Recorder;
import org.jboss.logging.Logger;

@Recorder
public class VaadinRecorder {

  private static final Logger log = Logger.getLogger(VaadinRecorder.class);

  public void registerRoute(BeanContainer beanContainer, String route) {
    log.info("register route " + route);
    RegisteredRoutesBean myBean = beanContainer.instance(RegisteredRoutesBean.class);
    myBean.addRoute(route);
  }
}
