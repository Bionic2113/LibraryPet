package com.caterpillar.shamil.LibraryPet.rest;

import com.caterpillar.shamil.LibraryPet.entity.People;
import com.caterpillar.shamil.LibraryPet.repository.PeopleRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <h3>SpringSecurity</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 21.04.2023
 */
@RestController
@RequestMapping(value = "/api/people", produces = "application/json;charset=UTF-8")
@Tag(name = "People")
public class PeopleRestController {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleRestController(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Operation
    @GetMapping
    List<People> allTable(){
        return peopleRepository.findByActiveTrue();
    }

    @Operation
    @PostMapping("/new")
    public People addNew(@RequestParam("last_name") String last_name,
                         @RequestParam("first_name") String first_name,
                         @RequestParam("patronymic") String patronymic){
        return peopleRepository.save(new People(last_name,first_name,patronymic));
    }

    @Operation
    @DeleteMapping("/{id}")
    public People deleteElement(@PathVariable("id") int id){
        var people = peopleRepository.findByIdAndActiveTrue(id).orElseThrow();
        people.setActive(false);
        return peopleRepository.save(people);
    }

    @Operation
    @GetMapping("/{id}")
    public People getElement(@PathVariable("id") int id){
        return peopleRepository.findById(id).orElseThrow();
    }

    @Operation
    @PatchMapping("/{id}")
    public People updateElement(@PathVariable("id") int id,
                                @RequestParam("last_name") String last_name,
                                @RequestParam("first_name") String first_name,
                                @RequestParam("patronymic") String patronymic){
        var people = peopleRepository.findByIdAndActiveTrue(id).orElseThrow();
        people.setFirstname(first_name);
        people.setLastname(last_name);
        people.setPatronymic(patronymic);
        return peopleRepository.save(people);
    }
}
