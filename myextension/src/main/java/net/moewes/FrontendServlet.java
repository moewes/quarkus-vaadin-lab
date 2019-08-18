package net.moewes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FrontendServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String pathInfo = req.getPathInfo();

    String webjarsResourceURI =
        "/META-INF/resources" + req.getRequestURI()
            .replaceFirst(req.getContextPath() + "frontend/bower_components/", "webjars/");

    int i = pathInfo.lastIndexOf("/");
    String substring = pathInfo.substring(i + 1);

    InputStream in = Thread.currentThread().getContextClassLoader()
        .getResourceAsStream(webjarsResourceURI);
    if (in != null) {
      String s = URLConnection.guessContentTypeFromStream(in);
      if (s == null) {
        if (webjarsResourceURI.endsWith(".js")) {
          s = "text/javascript; charset=utf-8";
        } else {
          s = "text/html; charset=utf-8";
        }
      }
      System.out.println("Type " + s + " File: " + webjarsResourceURI);
      resp.setContentType(s);
      byte[] pic = new byte[in.available()];
      in.read(pic);
      in.close();
      OutputStream out = resp.getOutputStream();
      out.write(pic);
    } else {
      resp.getWriter().println(webjarsResourceURI);
    }
  }
}
