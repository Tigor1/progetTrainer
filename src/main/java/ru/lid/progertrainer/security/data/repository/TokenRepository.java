package ru.lid.progertrainer.security.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.lid.progertrainer.security.data.entity.Token;
import ru.lid.progertrainer.security.data.entity.User;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("""
                SELECT t FROM Token t 
                JOIN t.user u
                WHERE u.id = :id AND (t.expired = false OR t.revoked = false)
            """)
    List<Token> findAllValidTokenByUser(Long id);

    Optional<Token> findByToken(String token);

    List<Token> deleteAllByUser(User user);
}
