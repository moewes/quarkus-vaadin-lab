package net.moewes;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class MyBean {

  private Set<String> routes = new HashSet<String>();

  public void addRoute(String route) {
    this.routes.add(route);
  }

  public List<String> getRoutes() {
    return this.routes.stream().collect(Collectors.toList());
  }

}
