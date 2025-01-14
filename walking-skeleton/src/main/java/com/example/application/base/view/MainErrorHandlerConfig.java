package com.example.application.base.view;

import com.vaadin.flow.server.ErrorHandler;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MainErrorHandlerConfig {

    @Bean
    public VaadinServiceInitListener errorHandlerInitializer(ErrorHandler errorHandler) {
        return (event) -> event.getSource().addSessionInitListener(
                sessionInitEvent -> sessionInitEvent.getSession().setErrorHandler(errorHandler));
    }
}
