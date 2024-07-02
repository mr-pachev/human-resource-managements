package bg.softuni.human_resource_managements.repository;

import bg.softuni.human_resource_managements.model.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, Long> {
}
