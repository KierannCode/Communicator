package fr.orsys.communicator.repository;

import fr.orsys.communicator.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("from User user where user.username = binary(:username)")
    Optional<User> findByUsername(String username);
}
