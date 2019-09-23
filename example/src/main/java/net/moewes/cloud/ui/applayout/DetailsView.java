package net.moewes.cloud.ui.applayout;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "app/details", layout = MyLayout.class)
public class DetailsView extends VerticalLayout {

  public DetailsView() {

    Details component = new Details("Expandable Details",
        new Text("Toggle using mouse, Enter and Space keys."));
    component.addOpenedChangeListener(e ->
        Notification.show(e.isOpened() ? "Opened" : "Closed"));

    add(component);
  }
}
