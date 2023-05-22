package com.caterpillar.shamil.LibraryPet.controllers.graphql;

import com.caterpillar.shamil.LibraryPet.comparators.BooksPeopleComparator;
import com.caterpillar.shamil.LibraryPet.entity.BooksPeople;
import com.caterpillar.shamil.LibraryPet.repository.BooksPeopleRepository;
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
public class BooksPeopleGraphQLController {

    private final BooksPeopleRepository booksPeopleRepository;
    private static int counter = 0;


    public BooksPeopleGraphQLController(BooksPeopleRepository booksPeopleRepository) {
        this.booksPeopleRepository = booksPeopleRepository;
    }

    @QueryMapping
    List<BooksPeople> allBooksPeople(@Argument int first, @Argument String after){
        var bp = booksPeopleRepository.findByActiveTrue();
        bp.sort(new BooksPeopleComparator());
        try{
            if(Objects.nonNull(after)){
                bp = bp.subList(counter, counter + first);
                log.log(Level.INFO, "After NOT null");
            }
            else{
                log.log(Level.INFO, "After IS null");
                counter = 0;
                bp = bp.subList(0,first);
            }
            log.log(Level.INFO, "BooksPeople is {} ", bp);
            counter += first;
        }
        catch (IndexOutOfBoundsException e){
            counter = Math.min(counter, bp.size()-1);
            bp = bp.subList(counter, bp.size()-1);
            counter = 0;
            return bp;
        }
        return bp;
    }
}
