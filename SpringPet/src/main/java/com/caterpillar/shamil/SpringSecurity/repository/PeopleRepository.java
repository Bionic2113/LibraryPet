package com.caterpillar.shamil.SpringSecurity.repository;


import com.caterpillar.shamil.SpringSecurity.entity.People;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PeopleRepository extends JpaRepository<People, Integer> {
    Optional<People> findByIdAndActiveTrue(int id);
    List<People> findByActiveTrue();
}
