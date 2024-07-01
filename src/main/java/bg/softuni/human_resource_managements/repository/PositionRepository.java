package bg.softuni.human_resource_managements.repository;

import bg.softuni.human_resource_managements.model.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
}
