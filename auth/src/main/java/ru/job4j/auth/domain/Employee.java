package ru.job4j.auth.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Class Employee.
 *
 * @author Vitaly Yagufarov (for.viy@gmail.com)
 * @version 1.0
 * @since 22.04.2021
 */
@Entity
@Table(name = "employee")
@Data
@EqualsAndHashCode(exclude = "hired")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private long inn;
    private Timestamp hired;

    public static Employee of(int id, String name, String surname, long inn) {
        var employee = new Employee();
        employee.id = id;
        employee.name = name;
        employee.surname = surname;
        employee.inn = inn;
        employee.hired = new Timestamp(System.currentTimeMillis());
        return employee;
    }
}
