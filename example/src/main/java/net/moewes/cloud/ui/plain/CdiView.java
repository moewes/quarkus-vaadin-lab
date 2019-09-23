package net.moewes.cloud.ui.plain;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import javax.inject.Inject;
import net.moewes.cloud.ui.RequestBean;

@Route(value = "cdi")
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
