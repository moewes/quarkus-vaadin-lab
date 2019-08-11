package net.moewes.cloud.ui;


import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

@Route("hallo")
public class MainView extends Div {

  MainView() {
    Div div = new Div();
    div.setText("Hallo from Vaadin");

    add(div);
  }
}
