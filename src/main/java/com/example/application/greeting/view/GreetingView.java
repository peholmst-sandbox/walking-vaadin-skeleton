package com.example.application.greeting.view;

import com.example.application.greeting.domain.Greeting;
import com.example.application.greeting.service.GreetingService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.time.Clock;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Route("")
@PageTitle("Greetings from Flow")
@Menu(order = 0, icon = "vaadin:cubes")
public class GreetingView extends Main {

    private final GreetingService greetingService;

    private final DataProvider<Greeting, Void> dataProvider;

    private final TextField name;

    public GreetingView(GreetingService greetingService, Clock clock) {
        this.greetingService = greetingService;
        dataProvider = DataProvider.fromCallbacks(
                query -> greetingService.list(VaadinSpringDataHelpers.toSpringPageRequest(query), null).stream(),
                query -> (int) greetingService.count(null));

        name = new TextField();
        name.setPlaceholder("What is your name?");
        name.setMaxLength(Greeting.GREETING_MAX_LENGTH);

        var greetBtn = new Button("Greet", event -> greet());
        var refreshBtn = new Button("Refresh", event -> refresh());

        var dateFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withZone(clock.getZone())
                .withLocale(getLocale());

        var greetingGrid = new Grid<>(dataProvider);
        greetingGrid.addColumn(Greeting::getGreeting).setHeader("Greeting");
        greetingGrid.addColumn(greeting -> dateFormatter.format(greeting.getGreetingDate())).setHeader("Date");
        greetingGrid.setSizeFull();

        setSizeFull();
        setAriaLabelledBy("view-title");
        addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Padding.MEDIUM, LumoUtility.Gap.SMALL);

        var toolbar = new Header(name, greetBtn, refreshBtn);
        toolbar.addClassNames(LumoUtility.Display.FLEX, LumoUtility.Gap.SMALL);

        add(toolbar);
        add(greetingGrid);
    }

    private void greet() {
        greetingService.greet(name.getValue());
        dataProvider.refreshAll();
        name.clear();
        Notification.show("Greeting added", 3000, Notification.Position.BOTTOM_END)
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private void refresh() {
        dataProvider.refreshAll();
    }

}
