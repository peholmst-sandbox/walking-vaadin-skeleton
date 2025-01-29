package com.example.application.greeting.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GreetingRepository extends JpaRepository<Greeting, Long>, JpaSpecificationExecutor<Greeting> {

    // If you don't need a total row count, Slice is better than Page.
    Slice<Greeting> findAllBy(Pageable pageable);
}
