package com.caterpillar.shamil.LibraryPet.repository;

import com.caterpillar.shamil.LibraryPet.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BooksRepository extends JpaRepository<Books,Integer> {
    Optional<Books> findByIdAndActiveTrue(int id);
    List<Books> findByActiveTrue();
}
