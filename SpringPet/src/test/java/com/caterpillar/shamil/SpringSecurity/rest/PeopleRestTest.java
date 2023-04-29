package com.caterpillar.shamil.SpringSecurity.rest;

import com.caterpillar.shamil.SpringSecurity.repository.PeopleRepository;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * <h3>SpringSecurity</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 21.04.2023
 */
@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = "/people_before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/people_after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@TestPropertySource("/application-test.properties")
public class PeopleRestTest {

    static String url = "/api/people";
    static int id = 1;

    @Autowired
    PeopleRepository peopleRepository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    MockMvc mockMvc;

    @Test
    public void getElement() throws Exception{
        var person = peopleRepository.findByIdAndActiveTrue(id).orElseThrow();
        var result = mockMvc
                                         .perform(MockMvcRequestBuilders.get(url + "/" + id)
                                                                        .contentType(MediaType.APPLICATION_JSON))
                                         .andExpect(MockMvcResultMatchers.status().isOk())
                                         .andReturn();
        Assertions.assertEquals(new Gson().toJson(person), result.getResponse().getContentAsString());
    }
    @Test
    public void getAll() throws Exception{
        var expected = new Gson().toJson(peopleRepository.findByActiveTrue());
        var actual = mockMvc
                                    .perform(MockMvcRequestBuilders.get(url)
                                            .contentType(MediaType.APPLICATION_JSON))
                                    .andExpect(MockMvcResultMatchers.status().isOk())
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void updateElement() throws Exception{
        var person = peopleRepository.findByIdAndActiveTrue(id).orElseThrow();
        person.setLastname("Воробей");
        person.setFirstname("Елена");
        person.setPatronymic("Викторовна");
        url +=  "/" + id + "?last_name=" + person.getLastname()
                             + "&first_name=" + person.getFirstname()
                             + "&patronymic=" + person.getPatronymic();
        var result = mockMvc
                                    .perform(MockMvcRequestBuilders.patch(url)
                                            .contentType(MediaType.APPLICATION_JSON))
                                    .andExpect(MockMvcResultMatchers.status().isOk())
                                    .andReturn();
        Assertions.assertEquals(new Gson().toJson(person), result.getResponse().getContentAsString());
    }

    @Test
    public void deleteElement() throws Exception{
        var person = peopleRepository.findByIdAndActiveTrue(id).orElseThrow();
        person.setActive(false);
        var result = mockMvc
                .perform(MockMvcRequestBuilders.delete(url + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assertions.assertEquals(new Gson().toJson(person), result.getResponse().getContentAsString());
    }
}
