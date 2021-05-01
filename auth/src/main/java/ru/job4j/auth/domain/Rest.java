package ru.job4j.auth.domain;

/**
 * Class Rest.
 *
 * @author Vitaly Yagufarov (for.viy@gmail.com)
 * @version 1.0
 * @since 30.04.2021
 */
public enum Rest {
    PERSON_API("http://localhost:8080/person/"),
    PERSON_API_ID("http://localhost:8080/person/{id}");

    private final String path;

    Rest(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
