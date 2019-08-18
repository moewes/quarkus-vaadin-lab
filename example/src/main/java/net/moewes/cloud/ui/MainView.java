package net.moewes.cloud.ui;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("hallo")
public class MainView extends VerticalLayout {

  MainView() {
    Div div = new Div();
    div.setText("Hallo from Vaadin");

    add(div);

    Button button = new Button();

    button.setText("Mein Button");

    button.addClickListener(event -> {
      UI.getCurrent().navigate(CdiView.class);
    });

    add(button);
  }
}
