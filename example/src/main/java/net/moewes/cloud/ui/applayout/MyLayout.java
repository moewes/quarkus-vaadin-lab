package net.moewes.cloud.ui.applayout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("app")
public class MyLayout extends AppLayout {

  public MyLayout() {

    //setPrimarySection(AppLayout.Section.DRAWER);

    Tabs tabs = new Tabs(getTab("Home", MainView.class),
        getTab("Details", DetailsView.class),
        getTab("Accordion", AccordionView.class));
    tabs.setOrientation(Tabs.Orientation.VERTICAL);

    addToDrawer(tabs);

    Span appName = new Span("Quarkus Vaadin Example");

    addToNavbar(true, new DrawerToggle(), appName);
  }

  private Tab getTab(String text, Class<? extends Component> viewClazz) {
    RouterLink link = new RouterLink(text, viewClazz);
    Tab tab = new Tab();
    tab.add(link);
    return tab;
  }
}
