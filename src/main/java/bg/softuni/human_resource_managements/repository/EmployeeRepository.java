package bg.softuni.human_resource_managements.repository;

import bg.softuni.human_resource_managements.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findAllByIdentificationNumber(String IdentificationNumber);
    Employee findById(long id);
}
