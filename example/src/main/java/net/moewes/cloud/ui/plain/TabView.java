package net.moewes.cloud.ui.plain;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;

@Route("tabs")
public class TabView extends VerticalLayout {

  public TabView() {
    Tab tab1 = new Tab("Tab one");
    Tab tab2 = new Tab("Tab two");
    Tab tab3 = new Tab("Tab three");
    Tabs tabs = new Tabs(tab1, tab2, tab3);

    add(tabs);

    Text newText = new Text("");
    Text oldText = new Text("");
    VerticalLayout l = new VerticalLayout();
    l.setPadding(true);
    l.setSpacing(true);

    l.add(newText, oldText);

    add(l);

    tabs.addSelectedChangeListener(event -> {
      newText.setText("Current tab : " + event.getSelectedTab().getLabel());

      if (event.getPreviousTab() != null) {
        oldText.setText(
            "Previous tab : " + event.getPreviousTab().getLabel());
      }
    });
  }
}
