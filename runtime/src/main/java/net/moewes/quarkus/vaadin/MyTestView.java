package net.moewes.quarkus.vaadin;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

@Route("test")
public class MyTestView extends Div {

  public MyTestView() {
    add(new Text("Hallo"));
  }
}
