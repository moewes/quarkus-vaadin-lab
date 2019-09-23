package net.moewes.cloud.ui.plain;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("listbox")
public class ListBoxView extends VerticalLayout {

  public ListBoxView() {

    Text message = new Text("");
    ListBox<String> listBox = new ListBox<>();
    listBox.setItems("Bread", "Butter", "Milk");

    listBox.addValueChangeListener(event -> message.setText(String.format(
        "Selection changed from %s to %s, selection is from client: %s",
        event.getOldValue(), event.getValue(), event.isFromClient())));

    Button button = new Button("Select Milk",
        event -> listBox.setValue("Milk"));

    add(listBox);
    add(button);
    add(message);
  }
}
