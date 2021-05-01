package ru.job4j.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.domain.Report;
import ru.job4j.auth.domain.Rest;

import java.util.ArrayList;
import java.util.List;

/**
 * Class ReportController.
 *
 * @author Vitaly Yagufarov (for.viy@gmail.com)
 * @version 1.0
 * @since 22.04.2021
 */
@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    RestTemplate rest;

    @GetMapping("/")
    public List<Report> findAll() {
        List<Report> rsl = new ArrayList<>();
        List<Person> persons = rest.exchange(
                Rest.PERSON_API.getPath(),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {
                }).getBody();
        for (Person person : persons) {
            var report = Report.of(1, "First", person);
            rsl.add(report);
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
