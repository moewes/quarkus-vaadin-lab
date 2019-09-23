package net.moewes.cloud.ui.plain;

import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


@Route(value = "accordion")
public class AccordionView extends VerticalLayout {

  public AccordionView() {

    Accordion accordion = new Accordion();

    accordion.add("Panel 1", new Span("Panel content"))
        .addThemeVariants(DetailsVariant.FILLED);

    accordion.add("Panel 2", new Span("Panel content"))
        .addThemeVariants(DetailsVariant.FILLED);

    AccordionPanel disabledPannel = accordion.add("Panel 3", new Span("Panel content"));
    disabledPannel.addThemeVariants(DetailsVariant.FILLED);
    disabledPannel.setEnabled(false);

    add(accordion);
  }
}
