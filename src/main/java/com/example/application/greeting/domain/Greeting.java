package com.example.application.greeting.domain;

import com.example.application.base.domain.AbstractLockableEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.jspecify.annotations.Nullable;

import java.time.Instant;

@Entity
@Table(name = "dummy")
public class Greeting extends AbstractLockableEntity<Long> {

    public static final int GREETING_MAX_LENGTH = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dummy_id")
    @JsonProperty
    // Hilla should stop ignoring the ID property (from AbstractEntity) and generate it into the GreetingModel.
    private Long id;

    @Column(name = "greeting", nullable = false, length = GREETING_MAX_LENGTH)
    @Size(max = GREETING_MAX_LENGTH)
    private String greeting;

    @Column(name = "greeting_date", nullable = false)
    private Instant greetingDate;

    // TODO I have a problem with this class. I would like to implement it in such a way that it is not possible
    //  to even instantiate it in an invalid state. That would mean getting rid of the Bean Validation annotations
    //  and implement the validations as code inside the setters. Furthermore, it would involve adding an initializing
    //  constructor that takes the required fields so that the entity is also instantiated in a valid state.
    //  However, this would make the entity unsuitable for use together with Binder.
    //  We need to support both approaches anyway, I'm just not sure which one should go into the skeleton and which
    //  one you should add manually if or when you need it. I can see pros and cons with having either in the skeleton.

    @Override
    public @Nullable Long getId() {
        return id;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public Instant getGreetingDate() {
        return greetingDate;
    }

    public void setGreetingDate(Instant greetingDate) {
        this.greetingDate = greetingDate;
    }
}
