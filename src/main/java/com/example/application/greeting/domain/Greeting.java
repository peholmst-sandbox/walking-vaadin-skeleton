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
    // Hilla should stop ignoring the ID property (from AbstractEntity) and generate
    // it into the GreetingModel.
    private Long id;

    @Column(name = "greeting", nullable = false, length = GREETING_MAX_LENGTH)
    @Size(max = GREETING_MAX_LENGTH)
    private String greeting;

    @Column(name = "greeting_date", nullable = false)
    private Instant greetingDate;

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
