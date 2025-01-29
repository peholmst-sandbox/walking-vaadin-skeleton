package com.example.application.greeting.service;

import com.example.application.greeting.domain.Greeting;
import com.example.application.greeting.domain.GreetingRepository;
//#if ui.framework == "react"
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
//#endif
import org.springframework.data.domain.Pageable;
//#if ui.framework == "flow"
import org.springframework.stereotype.Service;
//#endif
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.List;

//#if ui.framework == "flow"
@Service
//#endif
//#if ui.framework == "react"
@BrowserCallable
@AnonymousAllowed
//#endif
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class GreetingService {

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

    public List<Greeting> list(Pageable pageable) {
        return greetingRepository.findAllBy(pageable).toList();
    }

}
