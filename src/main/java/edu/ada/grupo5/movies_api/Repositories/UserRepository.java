package edu.ada.grupo5.movies_api.Repositories;

import edu.ada.grupo5.movies_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    UserDetails findByLogin(String login);

    boolean existsBylogin(String login);

    boolean existsById(String id);
}
