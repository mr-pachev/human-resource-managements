package bg.softuni.human_resource_managements.repository;

import bg.softuni.human_resource_managements.model.entity.Position;
import bg.softuni.human_resource_managements.model.enums.PositionName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    Position findByPositionName(PositionName positionName);
}
