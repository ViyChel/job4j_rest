package ru.job4j.auth.domain;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Class Report.
 *
 * @author Vitaly Yagufarov (for.viy@gmail.com)
 * @version 1.0
 * @since 22.04.2021
 */
@Data
public class Report {
    private int id;
    private String name;
    private Timestamp created;
    private Person person;

    public static Report of(int id, String name, Person person) {
        var r = new Report();
        r.id = id;
        r.name = name;
        r.person = person;
        r.created = new Timestamp(System.currentTimeMillis());
        return r;
    }
}
