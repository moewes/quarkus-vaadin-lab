package net.moewes.quarkus.vaadin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RegisteredRoutesBean {

  private Set<String> routes = new HashSet<>();

  public void addRoute(String route) {
    this.routes.add(route);
  }

  public List<String> getRoutes() {
    return this.routes.stream().collect(Collectors.toList());
  }

}
