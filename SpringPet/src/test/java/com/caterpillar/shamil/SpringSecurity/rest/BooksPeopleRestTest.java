package com.caterpillar.shamil.SpringSecurity.rest;

import com.caterpillar.shamil.SpringSecurity.entity.Books;
import com.caterpillar.shamil.SpringSecurity.entity.BooksPeople;
import com.caterpillar.shamil.SpringSecurity.entity.People;
import com.caterpillar.shamil.SpringSecurity.repository.BooksPeopleRepository;
import com.caterpillar.shamil.SpringSecurity.repository.BooksRepository;
import com.caterpillar.shamil.SpringSecurity.repository.PeopleRepository;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


/**
 * <h3>SpringSecurity</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 21.04.2023
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BooksPeopleRestTest {

    private static String url = "/api/bookspeople";

    @MockBean
    private static BooksPeopleRepository bpRepo ;
    @MockBean
    private static BooksRepository booksRepo;
    @MockBean
    private static PeopleRepository peopleRepo;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    MockMvc mockMvc;

    private static Books firstBook = new Books(1,"First","Nobody",true);
    private static Books secondBook = new Books(2,"Second","Nobody",true);
    private static Books thirdBook = new Books(3,"Firth","Nobody",false);
    private static People firstPerson = new People(1,"Jones","Jon","Dan",true);
    private static People secondPerson = new People(2,"Mur","Dannie","Jon",false);
    private static People thirdPerson = new People(3,"Li","Lin","Alis",true);
    private static BooksPeople firstBP = new BooksPeople(firstBook,secondPerson);
    private static BooksPeople secondBP = new BooksPeople(secondBook,secondPerson);
    private static BooksPeople thirdBP = new BooksPeople(thirdBook,thirdPerson);
    private static BooksPeople fourthBP = new BooksPeople(thirdBook,firstPerson);
    private static BooksPeople fifthBP = new BooksPeople(secondBook,thirdPerson);
    private static BooksPeople sixthBP = new BooksPeople(firstBook,firstPerson);


    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAll() throws Exception{
        // Создаем тестовые данные
        when(bpRepo.findByActiveTrue()).thenReturn(List.of(fifthBP, sixthBP));

        // Выполняем запрос на сервер
        ResponseEntity<List<BooksPeople>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BooksPeople>>() {}
        );

        // Проверяем результаты
        List<BooksPeople> actual = responseEntity.getBody();
        System.out.println("----------\n\n\n"+ actual +"\n\n\n-----------");
        System.out.println("----------\n\n\n"+ bpRepo.findByActiveTrue() +"\n\n\n-----------");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(actual);
        assertThat(responseEntity.getBody(), is(bpRepo.findByActiveTrue()));

    }
    @Test
    public void getElement() throws Exception {
        when(bpRepo.findByIdAndActiveTrue(1)).thenReturn(null);
        when(bpRepo.findByIdAndActiveTrue(2)).thenReturn(null);
        when(bpRepo.findByIdAndActiveTrue(3)).thenReturn(null);
        when(bpRepo.findByIdAndActiveTrue(4)).thenReturn(null);
        when(bpRepo.findByIdAndActiveTrue(5)).thenReturn(Optional.of(fifthBP));
        when(bpRepo.findByIdAndActiveTrue(6)).thenReturn(Optional.of(sixthBP));

        assertThrows(NullPointerException.class, () -> bpRepo.findByIdAndActiveTrue(1).orElseThrow());

      for(int i = 1; i < 5; i++){
            ResponseEntity<BooksPeople> responseEntity = restTemplate.exchange(
                    url+"/"+i,
                    HttpMethod.GET,
                    null,
                    BooksPeople.class
            );
            assertThat(responseEntity.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
      }
            ResponseEntity<BooksPeople> r5 = restTemplate.exchange(
                    url+"/5",
                    HttpMethod.GET,
                    null,
                    BooksPeople.class
            );
            assertThat(r5.getStatusCode(), is(HttpStatus.OK));
            assertThat(r5.getBody(), is(fifthBP));
        ResponseEntity<BooksPeople> r6 = restTemplate.exchange(
                url+"/6",
                HttpMethod.GET,
                null,
                BooksPeople.class
        );
        assertThat(r6.getStatusCode(), is(HttpStatus.OK));
        assertThat(r6.getBody(), is(sixthBP));

    }

    @Test
    void updateElement() throws Exception{
        when(bpRepo.findById(5)).thenReturn(Optional.ofNullable(fifthBP));
        fifthBP.setBooks(new Books("NoNo","MuMu"));
        fifthBP.setPeoples(new People("Krokop","Mirco","Crock"));
        when(bpRepo.save(fifthBP)).thenReturn(fifthBP);
        var result = mockMvc
                                .perform(patch(url+"/5?title=NoNo&author=MuMu&last_name=Krokop&first_name=Mirco&patronymic=Crock"))
                                .andReturn()
                                .getResponse()
                                .getContentAsString();
        assertThat(new Gson().fromJson(result,BooksPeople.class), is(fifthBP));
    }
    @Test
    void deleteElement() throws Exception{
        when(bpRepo.findByIdAndActiveTrue(5)).thenReturn(Optional.of(sixthBP));
        sixthBP.setActive(false);
        when(bpRepo.save(sixthBP)).thenReturn(sixthBP);
        var result = mockMvc.perform(delete(url+"/5"))
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString();
        assertThat(new Gson().fromJson(result,BooksPeople.class), is(sixthBP));
    }
    @Test
    void postElement() throws Exception{
        when(bpRepo.save(new BooksPeople(
                new Books("NoNo","MuMu"),
                new People("Krokop","Mirco","Crock")
        ))).thenReturn(fifthBP);
        var result = mockMvc
                .perform(post(url+"/new?title=NoNo&author=MuMu&last_name=Krokop&first_name=Mirco&patronymic=Crock"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(new Gson().fromJson(result,BooksPeople.class), is(fifthBP));
    }
    @Test
    void postWithID() throws Exception{
        when(booksRepo.findByIdAndActiveTrue(Integer.parseInt("2"))).thenReturn(Optional.of(secondBook));
        when(peopleRepo.findById(Integer.parseInt("3"))).thenReturn(Optional.of(thirdPerson));
        when(bpRepo.save(new BooksPeople(
                            booksRepo.findByIdAndActiveTrue(Integer.parseInt("2")).orElseThrow(),
                            peopleRepo.findById(Integer.parseInt("3")).orElseThrow()))
                        )
                    .thenReturn(fifthBP);
        var result = mockMvc.perform(post(url+"/new_with_id?id_book=2&id_person=3"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(new Gson().fromJson(result,BooksPeople.class), is(fifthBP));
    }
}
