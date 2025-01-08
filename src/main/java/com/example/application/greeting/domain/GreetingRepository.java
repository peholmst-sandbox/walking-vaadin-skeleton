package com.example.application.greeting.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GreetingRepository extends JpaRepository<Greeting, Long>, JpaSpecificationExecutor<Greeting> {

}
