package ru.job4j.auth.controller;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.auth.domain.Employee;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.domain.Rest;
import ru.job4j.auth.repository.EmployeeRepository;

import java.util.*;

/**
 * Class EmployeeController.
 *
 * @author Vitaly Yagufarov (for.viy@gmail.com)
 * @version 1.0
 * @since 22.04.2021
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeRepository employees;
    private final RestTemplate rest;

    public EmployeeController(EmployeeRepository employees, RestTemplate rest) {
        this.employees = employees;
        this.rest = rest;
    }

    @GetMapping("/")
    public Map<Employee, Set<Person>> findAll() {
        Map<Employee, Set<Person>> rsl = new HashMap<>();
        Iterable<Employee> emp = employees.findAll();
        List<Person> persons = rest.exchange(
                Rest.PERSON_API.getPath(),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {
                }).getBody();
        for (Employee employee : emp) {
            Set<Person> accounts = new HashSet<>();
            persons.stream().filter(p -> p.getEmployee().equals(employee)).forEach(accounts::add);
            rsl.put(employee, accounts);
        }
        return rsl;
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        Person rsl = rest.postForObject(Rest.PERSON_API.getPath(), person, Person.class);
        return new ResponseEntity<>(
                rsl,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        rest.put(Rest.PERSON_API.getPath(), person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        rest.delete(Rest.PERSON_API_ID.getPath(), id);
        return ResponseEntity.ok().build();
    }
}
