package ru.laboratory.blps.model.user.repository;

import ru.laboratory.blps.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserById(Long id);
    Optional<User> getUserByCredentials_Username(String username);
}
