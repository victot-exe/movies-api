package edu.ada.grupo5.movies_api.Repositories;

import edu.ada.grupo5.movies_api.model.Token;
import edu.ada.grupo5.movies_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    UserDetails findByLogin(String login);

    User findUserByLogin(String login);

    User findUserByToken(Token token);

    boolean existsBylogin(String login);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.token = :token WHERE u.id = :userId")
    void updateToken(Integer userId, Token token);
}
