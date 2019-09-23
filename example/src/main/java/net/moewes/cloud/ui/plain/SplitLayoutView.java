package net.moewes.cloud.ui.plain;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.splitlayout.SplitLayoutVariant;
import com.vaadin.flow.router.Route;
import java.util.concurrent.atomic.AtomicInteger;

@Route("splitlayout")
public class SplitLayoutView extends VerticalLayout {

  public SplitLayoutView() {

    H1 title = new H1("Split Layout");

    add(title);

    H2 subtitle1 = new H2("Split Layout with Event");

    SplitLayout layout1 = new SplitLayout();
    layout1.setWidthFull();
    layout1.addToPrimary(new Label("First content component"));
    layout1.addToSecondary(new Label("Second content component"));

    Label message1 = new Label("Drag and drop the splitter");
    AtomicInteger resizeCounter = new AtomicInteger();
    layout1.addSplitterDragendListener(event -> message1.setText(
        "SplitLayout Resized " + resizeCounter.incrementAndGet() + " times."));

    add(subtitle1, layout1, message1);

    H2 subtitle2 = new H2("Split Layout with Min and Max Widths");

    SplitLayout layout2 = new SplitLayout();
    layout2.addToPrimary(new Label("First content component"));
    layout2.addToSecondary(new Label("Second content component"));

    layout2.setPrimaryStyle("minWidth", "100px");
    layout2.setPrimaryStyle("maxWidth", "150px");
    layout2.setPrimaryStyle("background", "salmon");

    add(subtitle2, layout2);

    H2 subtitle3 = new H2("Split Layout with Theme Variants");

    SplitLayout layout3 = new SplitLayout(
        new Label("First content component"),
        new Label("Second content component"));
    layout3.addThemeVariants(SplitLayoutVariant.LUMO_SMALL);
    Button button = new Button("Remove 'small' variant");
    button.addClickListener(event -> {
      layout3.removeThemeVariants(SplitLayoutVariant.LUMO_SMALL);
      button.setDisableOnClick(true);
    });

    add(subtitle3, layout3, button);

  }
}
