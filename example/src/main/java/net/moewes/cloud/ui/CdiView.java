package net.moewes.cloud.ui;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import javax.inject.Inject;

@Route(value = "cdi", layout = MyLayout.class)
public class CdiView extends VerticalLayout {

  @Inject
  public CdiView(RequestBean bean) {

    Div a = new Div();

    a.setText("CDI Bean");

    Div b = new Div();

    if (bean == null) {
      b.setText("failed");
    } else {
      b.setText(bean.getText());
    }

    add(a);
    add(b);
  }
}
