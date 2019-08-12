package net.moewes.cloud.ui;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class RequestBean {

  public String getText() {
    return "Hello from RequestBean";
  }
}
