package com.caterpillar.shamil.LibraryPet.controllers.graphql;

import com.caterpillar.shamil.LibraryPet.comparators.PeopleComparator;
import com.caterpillar.shamil.LibraryPet.entity.People;
import com.caterpillar.shamil.LibraryPet.repository.PeopleRepository;
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
public class PeopleGraphQLController {

    private final PeopleRepository peopleRepository;
    private static int counter = 0;

    public PeopleGraphQLController(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @QueryMapping
    List<People> allPeople(@Argument int first, @Argument String after){
        var people = peopleRepository.findByActiveTrue();
        people.sort(new PeopleComparator());
        try{
            if (Objects.nonNull(after)){
                people = people.subList(counter, counter + first);
                log.log(Level.INFO, "After NOT null");
            }
            else{
                log.log(Level.INFO, "After IS null");
                counter = 0;
                people = people.subList(0,first);
            }
            log.log(Level.INFO, "people is {} ", people);
            counter += first;
        }
        catch (IndexOutOfBoundsException e){
            counter = Math.min(counter, people.size()-1);
            people = people.subList(counter, people.size()-1);
            counter = 0;
            return people;
        }
        return people;

    }
}
