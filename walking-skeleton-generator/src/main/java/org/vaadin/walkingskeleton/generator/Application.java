package org.vaadin.walkingskeleton.generator;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.AppShellSettings;
import com.vaadin.flow.theme.Theme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Theme("default")
public class Application implements AppShellConfigurator {

    private final String contextPath;

    public Application(@Value("${server.servlet.context-path}") String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
    public void configurePage(AppShellSettings settings) {
        AppShellConfigurator.super.configurePage(settings);
        settings.addMetaTag("context-path", contextPath);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
