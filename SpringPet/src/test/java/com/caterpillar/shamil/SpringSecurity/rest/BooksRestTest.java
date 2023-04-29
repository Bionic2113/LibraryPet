package com.caterpillar.shamil.SpringSecurity.rest;

import com.caterpillar.shamil.SpringSecurity.entity.Books;
import com.caterpillar.shamil.SpringSecurity.repository.BooksRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <h3>SpringSecurity</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 21.04.2023
 */
@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = "/books_before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/books_after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@TestPropertySource("/application-test.properties")
public class BooksRestTest {

    static String url = "/api/books";
    static int id = 177;
    @Autowired
    BooksRepository booksRepository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    MockMvc mockMvc;

    @Test
    public void getElement() throws Exception{
        Books book = booksRepository.findByIdAndActiveTrue(id).orElseThrow();
        mockMvc.perform(get(url + "/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.active").value(book.isActive()));

    }
    @Test
    public void getAll() throws Exception{
        mockMvc.perform(get(url))
                .andExpect(status().isOk());
        MvcResult mvcResult = mockMvc.perform(get("/api/books").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();
        Assertions.assertEquals(booksRepository.findByActiveTrue(),
                                new ObjectMapper()
                                        .readValue(mvcResult.getResponse().getContentAsString(),
                                                   new TypeReference<List<Books>>() {}));
    }

    @Test
    public void updateElement() throws Exception{
        var author = "А.С.Пушкин";
        var title = "Капитанская дочка";
        mockMvc
                .perform(patch(url + "/" + id + "?author="+ author+"&title="+title)
                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(book))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.author").value(author))
                .andExpect(jsonPath("$.title").value(title));

    }
    @Test
    public void deleteElement() throws Exception{
        Books book = booksRepository.findByIdAndActiveTrue(id).orElseThrow();
        book.setActive(false);
        mockMvc
                .perform(delete(url + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active").value(book.isActive()));

    }
}
