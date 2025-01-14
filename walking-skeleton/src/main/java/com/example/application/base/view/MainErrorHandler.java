package com.example.application.base.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.server.ErrorEvent;
import com.vaadin.flow.server.ErrorHandler;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringComponent
class MainErrorHandler implements ErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(MainErrorHandler.class);

    @Override
    public void error(ErrorEvent event) {
        log.error("An unexpected error occurred", event.getThrowable());
        event.getComponent().flatMap(Component::getUI).ifPresent(ui -> {
            var notification = new Notification("An unexpected error has occurred. Please try again later.");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.setPosition(Notification.Position.TOP_CENTER);
            notification.setDuration(3000);
            ui.access(notification::open);
        });
    }

}
