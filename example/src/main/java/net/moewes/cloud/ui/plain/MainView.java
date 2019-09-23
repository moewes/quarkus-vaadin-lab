package net.moewes.cloud.ui.plain;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "hallo")
public class MainView extends VerticalLayout {

  MainView() {
    Div div = new Div();
    div.setText("Hello from Vaadin");

    add(div);

    Button button = new Button();

    button.setText("My Button");

    button.addClickListener(event -> {
      UI.getCurrent().navigate(CdiView.class);
    });

    add(button);
  }
}
