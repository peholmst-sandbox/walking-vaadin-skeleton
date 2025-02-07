package com.example.application.greeting.ui.view;

import com.example.application.base.ui.component.ViewToolbar;
import com.example.application.greeting.domain.Greeting;
import com.example.application.greeting.service.GreetingService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.time.Clock;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import static com.vaadin.flow.spring.data.VaadinSpringDataHelpers.toSpringPageRequest;

@Route("")
@PageTitle("Greetings from Flow")
@Menu(order = 0, icon = "vaadin:handshake", title = "Greetings (Flow)")
public class GreetingView extends Main {

    private final GreetingService greetingService;

    final TextField name;
    final Button greetBtn;
    final Button refreshBtn;
    final Grid<Greeting> greetingGrid;

    public GreetingView(GreetingService greetingService, Clock clock) {
        this.greetingService = greetingService;

        name = new TextField();
        name.setPlaceholder("What is your name?");
        name.setAriaLabel("Enter your name");
        name.setMaxLength(Greeting.GREETING_MAX_LENGTH);
        name.addKeyPressListener(Key.ENTER, event -> greet());

        greetBtn = new Button("Greet", event -> greet());
        greetBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        refreshBtn = new Button("Refresh", event -> refresh());

        var dateFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withZone(clock.getZone())
                .withLocale(getLocale());

        greetingGrid = new Grid<>();
        greetingGrid.setItems(query -> greetingService.list(toSpringPageRequest(query)).stream());
        greetingGrid.addColumn(Greeting::getGreeting).setHeader("Greeting");
        greetingGrid.addColumn(greeting -> dateFormatter.format(greeting.getGreetingDate())).setHeader("Date");
        greetingGrid.setSizeFull();

        setSizeFull();
        addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Padding.MEDIUM, LumoUtility.Gap.SMALL);

        add(new ViewToolbar("Greetings from Flow", ViewToolbar.group(name, greetBtn), refreshBtn));
        add(greetingGrid);
    }

    private void greet() {
        greetingService.greet(name.getValue());
        greetingGrid.getDataProvider().refreshAll();
        name.clear();
        Notification.show("Greeting added", 3000, Notification.Position.BOTTOM_END)
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private void refresh() {
        greetingGrid.getDataProvider().refreshAll();
    }

}
