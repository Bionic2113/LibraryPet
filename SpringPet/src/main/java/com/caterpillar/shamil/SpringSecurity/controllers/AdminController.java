package com.caterpillar.shamil.SpringSecurity.controllers;

import com.caterpillar.shamil.SpringSecurity.entity.Books;
import com.caterpillar.shamil.SpringSecurity.entity.BooksPeople;
import com.caterpillar.shamil.SpringSecurity.entity.People;
import com.caterpillar.shamil.SpringSecurity.repository.BooksPeopleRepository;
import com.caterpillar.shamil.SpringSecurity.repository.BooksRepository;
import com.caterpillar.shamil.SpringSecurity.repository.PeopleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private static Logger log = LoggerFactory.getLogger(AdminController.class);
    final BooksPeopleRepository booksPeopleRepository;
    final PeopleRepository peopleRepository;
    final BooksRepository booksRepository;

    @Autowired
    public AdminController(BooksPeopleRepository booksPeopleRepository,
                           PeopleRepository peopleRepository,
                           BooksRepository booksRepository) {
        this.booksPeopleRepository = booksPeopleRepository;
        this.peopleRepository = peopleRepository;
        this.booksRepository = booksRepository;
    }

    @GetMapping
    String adminTable(Model model) {
        model.addAttribute("table", booksPeopleRepository.findByActiveTrue());
        return "admin";
    }

    @GetMapping("/bookspeople/new")
    String allTables(Model model){
        model.addAttribute("table_books", booksRepository.findByActiveTrue());
        model.addAttribute("table_people", peopleRepository.findByActiveTrue());
        return "add_new_bookspeople";
    }

    @PostMapping("/bookspeople/new")
    String  addNew(@RequestParam("id_book") String id_book,
                   @RequestParam("id_person") String id_person){
        booksPeopleRepository.save(new BooksPeople(booksRepository
                                                            .findByIdAndActiveTrue(Integer.parseInt(id_book))
                                                            .orElseThrow(),
                                                    peopleRepository
                                                            .findByIdAndActiveTrue(Integer.parseInt(id_person))
                                                            .orElseThrow()));
        return "redirect:/admin";
    }
    @DeleteMapping("/bookspeople/{id}")
    String deleteElement(@ModelAttribute("bookspeople") BooksPeople bp){
        bp.setActive(false);
        booksPeopleRepository.save(bp);
        return "redirect:/admin";
    }

    @GetMapping("/bookspeople/{id}")
    String getElement(@PathVariable("id") int id, Model model){
        var bp = booksPeopleRepository.findByIdAndActiveTrue(id).orElseThrow();
        log.info("BOOKS IS ${}",bp.getBooks().toString());
        log.info("PEOPLE IS ${}",bp.getPeoples().toString());
        model.addAttribute("table_books", booksRepository.findByActiveTrue());
        model.addAttribute("table_people", peopleRepository.findByActiveTrue());
        model.addAttribute("bookspeople", bp);
        model.addAttribute("book", bp.getBooks());
        model.addAttribute("person", bp.getPeoples());
        return "bookspeople_edit";
    }
    @PatchMapping("/bookspeople/{id}")
    String updateElement(@PathVariable("id") int id,
                         @ModelAttribute("bookspeople") BooksPeople bp,
                         @ModelAttribute("book") Books book,
                         @ModelAttribute("person") People person){
        log.info("BooksPeople is {}", bp);
        log.info("Book is {}", book);
        log.info("Person is {}", person);
        booksRepository.save(book);
        peopleRepository.save(person);
        bp.setBooks(book);
        bp.setPeoples(person);
        bp.setId(id);
        log.info("BooksPeople is {}", bp);
        booksPeopleRepository.save(bp);
        return "redirect:/admin";
    }
}
