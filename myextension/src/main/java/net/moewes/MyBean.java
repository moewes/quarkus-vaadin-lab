package net.moewes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MyBean {

  private Set<String> routes = new HashSet<>();

  public void addRoute(String route) {
    this.routes.add(route);
  }

  public List<String> getRoutes() {
    return this.routes.stream().collect(Collectors.toList());
  }

}
