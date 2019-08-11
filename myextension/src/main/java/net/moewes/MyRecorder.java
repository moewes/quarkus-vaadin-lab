package net.moewes;

import io.quarkus.arc.runtime.BeanContainer;
import io.quarkus.runtime.annotations.Recorder;

@Recorder
public class MyRecorder {

  public void sayHello(String name) {
    System.out.println("Hello" + name);
  }

  public void registerRoute(BeanContainer beanContainer, String route) {
    System.out.print("register route " + route);
    MyBean myBean = beanContainer.instance(MyBean.class);
    myBean.addRoute(route);
  }
}
