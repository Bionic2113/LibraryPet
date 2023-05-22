package com.caterpillar.shamil.LibraryPet.controllers;


import com.caterpillar.shamil.LibraryPet.config.PassEncoder;
import com.caterpillar.shamil.LibraryPet.entity.Users;
import com.caterpillar.shamil.LibraryPet.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class RegisterController {

    private final PassEncoder passEnc;
    private final UsersRepository usersRepository;

    @Autowired
    public RegisterController(UsersRepository usersRepository, PassEncoder passEnc) {
        this.usersRepository = usersRepository;
        this.passEnc = passEnc;
    }

    @GetMapping("/register")
    String register(Model model){
        model.addAttribute("user",  new Users());
        return "register";
    }

    @PostMapping("/register")
    String save_user(@ModelAttribute("user") Users user,Model model){
        user.setPassword(passEnc.passwordEncoder().encode(user.getPassword()));
        usersRepository.save(user);
        return "redirect:/login";
    }
}
