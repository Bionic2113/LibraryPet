package com.caterpillar.shamil.LibraryPet.controllers;

import com.caterpillar.shamil.LibraryPet.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class UserController {
    final BooksRepository booksRepository;

    @Autowired
    public UserController(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @GetMapping("/user")
    String admin(Model model){
        model.addAttribute("table", booksRepository.findByActiveTrue());
        return "user";
    }
}
