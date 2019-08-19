package net.moewes.cloud.ui;

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

    setPrimarySection(AppLayout.Section.DRAWER);

    addToNavbar(new DrawerToggle());

    RouterLink link = new RouterLink(null, MainView.class);
    //link.add(VaadinIcon.ARROW_RIGHT.create());
    link.add("link text");
    Tab tab = new Tab();
    tab.add(link);

    Tabs tabs = new Tabs(new Tab("Home"), new Tab("About"), tab);
    tabs.setOrientation(Tabs.Orientation.VERTICAL);
    // appLayout.
    addToDrawer(tabs);

    Span appName = new Span("Branding");

    addToNavbar(true, appName);

    //add(appLayout);
  }
}
