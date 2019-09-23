package net.moewes.cloud.ui.plain;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("checkbox")
public class CheckboxView extends VerticalLayout {

  public CheckboxView() {

    Checkbox valueChangeCheckbox = new Checkbox(
        "Checkbox with a ValueChangeListener");
    Div message = new Div();
    valueChangeCheckbox.addValueChangeListener(event -> {
      message.setText(
          String.format("Checkbox value changed from '%s' to '%s'",
              event.getOldValue(), event.getValue()));
    });

    add(valueChangeCheckbox);
    add(message);
  }
}
