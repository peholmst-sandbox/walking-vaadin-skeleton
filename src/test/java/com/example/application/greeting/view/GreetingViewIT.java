package com.example.application.greeting.view;

import com.example.application.Application;
import com.example.application.greeting.domain.GreetingRepository;
import com.example.application.greeting.service.GreetingService;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.testbench.unit.SpringUIUnitTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ContextConfiguration;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ContextConfiguration(classes = { GreetingViewIT.TestViewConfig.class, Application.class })
class GreetingViewIT extends SpringUIUnitTest {

    @Autowired
    GreetingService greetingService;

    @Autowired
    GreetingRepository greetingRepository;

    @AfterEach
    void cleanUp() {
        greetingRepository.deleteAll();
    }

    @Test
    public void clicking_the_greet_button_adds_a_new_greeting_to_the_grid() {
        var view = navigate(GreetingView.class);
        test(view.name).setValue("Joe Cool");
        test(view.greetBtn).click();

        var notification = $(Notification.class).first();
        assertThat(test(notification).getText()).isEqualTo("Greeting added");

        assertThat(test(view.greetingGrid).getCellText(0, 0)).isEqualTo("Hello Joe Cool!");
        assertThat(test(view.greetingGrid).getCellText(0, 1)).isEqualTo("9 Jan 2025, 13.45.55");
        assertThat(view.name.getValue()).isEmpty();
    }

    @Test
    public void refreshing_the_grid_shows_greetings_added_elsewhere() {
        var view = navigate(GreetingView.class);
        assertThat(test(view.greetingGrid).size()).isEqualTo(0);

        greetingService.greet("Alice");

        test(view.refreshBtn).click();

        assertThat(test(view.greetingGrid).getCellText(0, 0)).isEqualTo("Hello Alice!");
        assertThat(test(view.greetingGrid).getCellText(0, 1)).isEqualTo("9 Jan 2025, 13.45.55");
    }

    static class TestViewConfig {

        @Bean
        @Primary
        Clock fixedClock() {
            return Clock.fixed(Instant.ofEpochSecond(1736430355), ZoneId.of("UTC")); // 9 Jan 2025, 13.45.55 UTC
        }

    }
}
