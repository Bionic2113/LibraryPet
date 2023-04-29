package com.caterpillar.shamil.SpringSecurity.rest;

import com.caterpillar.shamil.SpringSecurity.entity.Books;
import com.caterpillar.shamil.SpringSecurity.entity.BooksPeople;
import com.caterpillar.shamil.SpringSecurity.entity.People;
import com.caterpillar.shamil.SpringSecurity.repository.BooksPeopleRepository;
import com.caterpillar.shamil.SpringSecurity.repository.BooksRepository;
import com.caterpillar.shamil.SpringSecurity.repository.PeopleRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/bookspeople", produces = "application/json;charset=UTF-8")
@Tag(name = "BooksPeople")
public class BooksPeopleRestController {
    private static Logger log = LoggerFactory.getLogger(BooksPeopleRestController.class);
    final BooksPeopleRepository booksPeopleRepository;
    final PeopleRepository peopleRepository;
    final BooksRepository booksRepository;

    @Autowired
    public BooksPeopleRestController(BooksPeopleRepository booksPeopleRepository,
                                     PeopleRepository peopleRepository,
                                     BooksRepository booksRepository) {
        this.booksPeopleRepository = booksPeopleRepository;
        this.peopleRepository = peopleRepository;
        this.booksRepository = booksRepository;
    }

    @Operation
    @GetMapping
    public List<BooksPeople> adminTable() {
        return booksPeopleRepository.findByActiveTrue();
    }

    @Operation
    @PostMapping("/new_with_id")
    public BooksPeople addNew(@RequestParam("id_book") String id_book,
                              @RequestParam("id_person") String id_person){
      return booksPeopleRepository.save(new BooksPeople(booksRepository.findByIdAndActiveTrue(Integer.parseInt(id_book)).orElseThrow(),
                peopleRepository.findById(Integer.parseInt(id_person)).orElseThrow()));
    }

    @Operation
    @PostMapping("/new")
    public BooksPeople addNew(@RequestParam("title") String title,
                              @RequestParam("author") String author,
                              @RequestParam("last_name") String last_name,
                              @RequestParam("first_name") String first_name,
                              @RequestParam("patronymic") String patronymic){
        return booksPeopleRepository.save(new BooksPeople(
                                                new Books(title,author),
                                                new People(last_name,first_name,patronymic)
                                            ));
    }

    @Operation
    @DeleteMapping("/{id}")
    public BooksPeople get_for_delete(@PathVariable("id") int id){
        var bp = booksPeopleRepository.findByIdAndActiveTrue(id).orElseThrow();
        bp.setActive(false);
        booksPeopleRepository.save(bp);
        return booksPeopleRepository.save(bp);
    }

    @Operation
    @GetMapping("/{id}")
    public BooksPeople getElement(@PathVariable("id") int id){
        return booksPeopleRepository.findByIdAndActiveTrue(id).orElseThrow();
    }
    @Operation
    @PatchMapping("/{id}")
    public BooksPeople updateElement(@PathVariable("id") int id,
                                     @RequestParam("title") String title,
                                     @RequestParam("author") String author,
                                     @RequestParam("last_name") String last_name,
                                     @RequestParam("first_name") String first_name,
                                     @RequestParam("patronymic") String patronymic){
        var bp = booksPeopleRepository.findById(id).orElseThrow();
        bp.setBooks(new Books(title,author));
        bp.setPeoples(new People(last_name,first_name,patronymic));
        return booksPeopleRepository.save(bp);
    }
}
