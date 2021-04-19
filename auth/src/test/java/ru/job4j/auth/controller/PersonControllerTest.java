package ru.job4j.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.auth.AuthApplication;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class PersonControllerTest.
 *
 * @author Vitaly Yagufarov (for.viy@gmail.com)
 * @version 1.0
 * @since 11.04.2021
 */
@SpringBootTest(classes = AuthApplication.class)
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private PersonRepository repository;

    @Test
    @WithMockUser
    void whenReturnAllPersons() throws Exception {
        List<Person> people = Arrays.asList(
                Person.of("user1", "password1"),
                Person.of("user2", "password2")
        );
        when(repository.findAll()).thenReturn(people);
        mockMvc.perform(get("/person/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(people)));
    }

    @Test
    @WithMockUser
    void whenReturnPersonById() throws Exception {
        Person person = Person.of("user", "password");
        person.setId(1);
        when(repository.findById(person.getId())).thenReturn(Optional.of(person));
        mockMvc.perform(get("/person/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(person)));
    }

    @Test
    @WithMockUser
    void whenNotFoundPersonById() throws Exception {
        mockMvc.perform(get("/person/{id}", 1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void whenCreatePerson() throws Exception {
        Person person = Person.of("user", "password");
        person.setId(1);
        when(repository.findById(person.getId())).thenReturn(Optional.of(person));
        mockMvc.perform(post("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(person)))
                .andExpect(status().isCreated());
        ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
        verify(repository, times(1)).save(argument.capture());
        assertThat(argument.getValue().getLogin(), is("user"));
        assertThat(argument.getValue().getPassword(), is("password"));
    }

    @Test
    @WithMockUser
    void whenUpdatePersonData() throws Exception {
        Person person = Person.of("user", "password");
        person.setId(1);
        when(repository.findById(person.getId())).thenReturn(Optional.of(person));
        mockMvc.perform(put("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(person)))
                .andExpect(status().isOk());
        ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
        verify(repository, times(1)).save(argument.capture());
        assertThat(argument.getValue().getLogin(), is("user"));
    }

    @Test
    @WithMockUser
    void whenDetetePerson() throws Exception {
        mockMvc.perform(delete("/person/1"))
                .andExpect(status().isOk());
        verify(repository, times(1)).delete(any());
    }
}