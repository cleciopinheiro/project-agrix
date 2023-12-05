package com.betrybe.agrix.models.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.betrybe.agrix.models.entities.Person;

/**
 * Person JPA repository.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {

  Optional<Person> findByUsername(String username);
}
