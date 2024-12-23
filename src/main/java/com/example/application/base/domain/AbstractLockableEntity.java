package com.example.application.base.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

@MappedSuperclass
public abstract class AbstractLockableEntity<ID> extends AbstractEntity<ID> {

    @Version
    @Column(name = "_opt_lock")
    private int version;

    public int getVersion() {
        return version;
    }

    protected void setVersion(int version) {
        this.version = version;
    }
}
