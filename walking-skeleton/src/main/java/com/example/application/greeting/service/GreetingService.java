package com.example.application.greeting.service;

import com.example.application.greeting.domain.Greeting;
import com.example.application.greeting.domain.GreetingRepository;
//#if ui.framework == "react"
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
//#endif
import com.vaadin.hilla.crud.CountService;
import com.vaadin.hilla.crud.JpaFilterConverter;
import com.vaadin.hilla.crud.ListService;
import com.vaadin.hilla.crud.filter.Filter;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.List;

@Service
//#if ui.framework == "react"
@BrowserCallable
@AnonymousAllowed
//#endif
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class GreetingService implements ListService<Greeting>, CountService {

    private final GreetingRepository greetingRepository;

    private final Clock clock;

    GreetingService(GreetingRepository greetingRepository, Clock clock) {
        this.greetingRepository = greetingRepository;
        this.clock = clock;
    }

    public void greet(String name) {
        if ("fail".equals(name)) {
            throw new RuntimeException("This is for testing the error handler");
        }
        var greeting = new Greeting();
        greeting.setGreeting("Hello %s!".formatted(name));
        greeting.setGreetingDate(clock.instant());
        greetingRepository.saveAndFlush(greeting);
    }

    @Override
    public @NonNull List<Greeting> list(Pageable pageable, @Nullable Filter filter) {
        return greetingRepository.findAll(JpaFilterConverter.toSpec(filter, Greeting.class), pageable).toList();
    }

    @Override
    public long count(@Nullable Filter filter) {
        return greetingRepository.count(JpaFilterConverter.toSpec(filter, Greeting.class));
    }

}
