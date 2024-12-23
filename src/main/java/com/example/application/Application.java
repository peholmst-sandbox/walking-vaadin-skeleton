package com.example.application;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.ErrorHandler;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

@SpringBootApplication
@Theme("walking-skeleton")
public class Application implements AppShellConfigurator {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone(); // You can also use Clock.systemUTC()
    }

    @Bean
    public VaadinServiceInitListener errorHandlerInitializer(ErrorHandler errorHandler) {
        return (event) -> event.getSource().addSessionInitListener(sessionInitEvent -> sessionInitEvent.getSession().setErrorHandler(errorHandler));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
