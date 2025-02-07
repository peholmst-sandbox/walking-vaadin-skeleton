package com.example.application.todo.domain;

import com.example.application.base.domain.AbstractEntity;
//#if ui.framework == "react"
import com.fasterxml.jackson.annotation.JsonProperty;
//#endif
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.jspecify.annotations.Nullable;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "todo")
public class Todo extends AbstractEntity<Long> {

    public static final int DESCRIPTION_MAX_LENGTH = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    //#if ui.framework == "react"
    @JsonProperty
    //#endif
    private Long id;

    @Column(name = "description", nullable = false, length = DESCRIPTION_MAX_LENGTH)
    @Size(max = DESCRIPTION_MAX_LENGTH)
    private String description;

    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @Column(name = "due_date")
    @Nullable
    private LocalDate dueDate;

    @Override
    public @Nullable Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String greeting) {
        this.description = greeting;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant greetingDate) {
        this.creationDate = greetingDate;
    }

    public @Nullable LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(@Nullable LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
