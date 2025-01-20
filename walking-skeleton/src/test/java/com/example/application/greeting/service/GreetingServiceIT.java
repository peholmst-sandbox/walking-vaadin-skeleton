package com.example.application.greeting.service;

import com.example.application.greeting.domain.Greeting;
import com.example.application.greeting.domain.GreetingRepository;
import com.vaadin.hilla.crud.filter.OrFilter;
import com.vaadin.hilla.crud.filter.PropertyStringFilter;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class GreetingServiceIT {

    @Autowired
    GreetingService greetingService;

    @Autowired
    GreetingRepository greetingRepository;

    @Autowired
    Clock clock;

    @AfterEach
    void cleanUp() {
        greetingRepository.deleteAll();
    }

    @Test
    public void greetings_are_stored_in_the_database_with_the_current_timestamp() {
        var now = clock.instant();
        greetingService.greet("Joe Cool");
        assertThat(greetingRepository.findAll()).singleElement()
                .matches(greeting -> greeting.getGreeting().equals("Hello Joe Cool!")
                        && greeting.getGreetingDate().isAfter(now));
    }

    @Test
    public void greetings_are_validated_before_they_are_stored() {
        assertThatThrownBy(() -> greetingService.greet("X".repeat(Greeting.GREETING_MAX_LENGTH)))
                .isInstanceOf(ValidationException.class);
        assertThat(greetingRepository.count()).isEqualTo(0);
    }

    @Test
    public void greeting_list_can_be_filtered_by_greeting() {
        greetingService.greet("Alice");
        greetingService.greet("Bob");
        greetingService.greet("Eve");

        // TODO There should be a better Java API for this:
        var filter = new PropertyStringFilter();
        filter.setPropertyId("greeting");
        filter.setFilterValue("Alice");
        filter.setMatcher(PropertyStringFilter.Matcher.CONTAINS);

        assertThat(greetingService.list(PageRequest.ofSize(10), filter)).singleElement()
                .matches(greeting -> greeting.getGreeting().equals("Hello Alice!"));
    }

    @Test
    public void count_can_be_filtered_by_greeting() {
        greetingService.greet("Alice");
        greetingService.greet("Bob");
        greetingService.greet("Eve");

        // TODO There should be a better Java API for this:
        var aliceFilter = new PropertyStringFilter();
        aliceFilter.setPropertyId("greeting");
        aliceFilter.setFilterValue("Alice");
        aliceFilter.setMatcher(PropertyStringFilter.Matcher.CONTAINS);

        var eveFilter = new PropertyStringFilter();
        eveFilter.setPropertyId("greeting");
        eveFilter.setFilterValue("Eve");
        eveFilter.setMatcher(PropertyStringFilter.Matcher.CONTAINS);

        var filter = new OrFilter();
        filter.setChildren(List.of(aliceFilter, eveFilter));

        assertThat(greetingService.count(filter)).isEqualTo(2);
    }
}
