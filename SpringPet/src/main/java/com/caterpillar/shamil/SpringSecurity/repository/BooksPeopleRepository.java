package com.caterpillar.shamil.SpringSecurity.repository;


import com.caterpillar.shamil.SpringSecurity.entity.BooksPeople;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BooksPeopleRepository extends JpaRepository<BooksPeople, Integer> {
    Optional<BooksPeople> findByIdAndActiveTrue(int id);
    List<BooksPeople> findByActiveTrue();
}
