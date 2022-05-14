package ru.laboratory.blps.model.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.laboratory.blps.model.user.Token;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> getTokenByRefreshToken(String refreshToken);

    @Modifying
    @Query(nativeQuery = true,  value = "DELETE FROM accounts_token WHERE token_id = :id")
    void deleteFromMergeTable(@Param("id") long id);
}
