package com.caterpillar.shamil.LibraryPet.repository;

import com.caterpillar.shamil.LibraryPet.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * <h3>SpringSecurity</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 14.04.2023
 */
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByLogin(String login);
}
