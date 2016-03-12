package at.baulu.readies.persistence.repository;

import at.baulu.readies.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Mario on 11.03.2016.
 */
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
}
