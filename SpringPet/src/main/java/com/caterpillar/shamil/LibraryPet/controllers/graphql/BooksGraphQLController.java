package com.caterpillar.shamil.LibraryPet.controllers.graphql;

import com.caterpillar.shamil.LibraryPet.comparators.BooksComparator;
import com.caterpillar.shamil.LibraryPet.entity.Books;
import com.caterpillar.shamil.LibraryPet.repository.BooksRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Objects;

/**
 * <h3>SpringPet</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 06.05.2023
 */

@Controller
@Log4j2
public class BooksGraphQLController {
    private static int counter = 0;
    private final
    BooksRepository booksRepository;

    public BooksGraphQLController(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @QueryMapping
    public List<Books> allBooks(@Argument int first, @Argument String after) {
        var books = booksRepository.findByActiveTrue();
        books.sort(new BooksComparator());
       try{
           if (Objects.nonNull(after)){
               books = books.subList(counter, counter + first);
               log.log(Level.INFO, "After NOT null");
           }
           else{
               log.log(Level.INFO, "After IS null");
               counter = 0;
               books = books.subList(0,first);
           }
           log.log(Level.INFO, "Books is {} ", books);
           counter += first;
       }
       catch (IndexOutOfBoundsException e){
           counter = Math.min(counter, books.size()-1);
           books = books.subList(counter, books.size()-1);
           counter = 0;
           return books;
       }
        return books;
    }

//    @MutationMapping
//    public Books addBook(String title, String author) {
//        log.log(Level.INFO, "Title is {} ; Author is {}", title, author);
//        var book = new Books(title,author);
//        log.log(Level.INFO, "Book is {}", book);
//        booksRepository.save(book);
//        return book;
//    }

}

