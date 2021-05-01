package ru.job4j.auth.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Class Person.
 *
 * @author Vitaly Yagufarov (for.viy@gmail.com)
 * @version 1.0
 * @since 11.04.2021
 */
@Entity
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login;
    private String password;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public static Person of(String login, String password) {
        var person = new Person();
        person.setLogin(login);
        person.setPassword(password);
        return person;
    }
}
