package com.caterpillar.shamil.SpringSecurity.rest;

import com.caterpillar.shamil.SpringSecurity.entity.Books;
import com.caterpillar.shamil.SpringSecurity.repository.BooksRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/books", produces = "application/json;charset=UTF-8")
@Tag(name = "Books")
public class BooksRestController {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksRestController(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Operation
    @GetMapping
    List<Books> allTable(){
        return booksRepository.findByActiveTrue();
    }

    @Operation
    @PostMapping("/new")
    public Books addNew(@RequestParam("title") String title,
                        @RequestParam("author") String author){
        return booksRepository.save(new Books(title,author));
    }

    @Operation
    @DeleteMapping("/{id}")
    public Books deleteElement(@PathVariable("id") int id){
        var book = booksRepository.findById(id).orElseThrow();
        book.setActive(false);
        booksRepository.save(book);
        return booksRepository.save(book);
    }

    @Operation
    @GetMapping("/{id}")
    public Books getElement(@PathVariable("id") int id){
        return booksRepository.findByIdAndActiveTrue(id).orElseThrow();
    }

    @Operation
    @PatchMapping("/{id}")
    public Books updateElement(@PathVariable("id") int id,
                                     @RequestParam("title") String title,
                                     @RequestParam("author") String author){
        var bp = booksRepository.findByIdAndActiveTrue(id).orElseThrow();
        bp.setTitle(title);
        bp.setAuthor(author);
        return booksRepository.save(bp);
    }

}
