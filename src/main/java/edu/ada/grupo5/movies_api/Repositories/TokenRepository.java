package edu.ada.grupo5.movies_api.Repositories;

import edu.ada.grupo5.movies_api.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Token findByToken(String token);
}
