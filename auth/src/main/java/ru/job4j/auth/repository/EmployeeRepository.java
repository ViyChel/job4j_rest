package ru.job4j.auth.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.auth.domain.Employee;

/**
 * Interface EmployeeRepository.
 *
 * @author Vitaly Yagufarov (for.viy@gmail.com)
 * @version 1.0
 * @since 30.04.2021
 */
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
}
