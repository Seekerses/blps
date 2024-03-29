package ru.laboratory.blps.auth.repository;

import ru.laboratory.blps.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserById(Long id);
    Optional<User> getUserByCredentials_Username(String username);
}
