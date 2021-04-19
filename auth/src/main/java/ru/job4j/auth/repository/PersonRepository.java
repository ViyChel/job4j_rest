package ru.job4j.auth.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.auth.domain.Person;

/**
 * Interface PersonRepository.
 *
 * @author Vitaly Yagufarov (for.viy@gmail.com)
 * @version 1.0
 * @since 11.04.2021
 */
public interface PersonRepository extends CrudRepository<Person, Integer> {
}
