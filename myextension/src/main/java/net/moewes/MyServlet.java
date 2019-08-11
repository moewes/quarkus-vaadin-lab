package net.moewes;


import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyServlet extends HttpServlet {

  @Inject
  MyBean myBean;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    /*
    SmallRyeHealth health = reporter.getHealth();
    if (health.isDown()) {
      resp.setStatus(503);
    }
    */
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
    // reporter.reportHealth(resp.getOutputStream(), health);
    resp.getWriter().println("Hallo from MyMarker");
    for (String route : myBean.getRoutes()) {
      resp.getWriter().println("registered route: " + route);
    }
  }
}
