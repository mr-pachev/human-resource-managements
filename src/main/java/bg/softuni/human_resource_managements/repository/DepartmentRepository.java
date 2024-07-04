package bg.softuni.human_resource_managements.repository;

import bg.softuni.human_resource_managements.model.entity.Department;
import bg.softuni.human_resource_managements.model.enums.DepartmentName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findByDepartmentName(DepartmentName departmentName);
}
