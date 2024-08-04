package bg.softuni.human_resource_managements.repository;

import bg.softuni.human_resource_managements.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByIdentificationNumber(String identificationNumber);
    Optional<User> findById(long id);
}
