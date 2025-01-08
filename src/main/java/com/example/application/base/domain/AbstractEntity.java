package com.example.application.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.MappedSuperclass;
import org.jspecify.annotations.Nullable;
import org.springframework.data.util.ProxyUtils;

@MappedSuperclass
public abstract class AbstractEntity<ID> {

    @JsonIgnore // Hilla should not generate an unknown ID in the AbstractEntityModel.
    public abstract @Nullable ID getId();

    @Override
    public String toString() {
        return "%s{id=%s}".formatted(getClass().getSimpleName(), getId());
    }

    @Override
    public int hashCode() {
        var id = getId();
        return id == null ? super.hashCode() : id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj == this) {
            return true;
        } else if (!getClass().equals(ProxyUtils.getUserClass(obj))) {
            return false;
        }
        var id = getId();
        if (id == null) {
            return false;
        } else {
            return id.equals(((AbstractEntity<?>) obj).getId());
        }
    }

}
