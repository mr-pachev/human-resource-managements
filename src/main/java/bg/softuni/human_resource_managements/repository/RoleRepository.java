package bg.softuni.human_resource_managements.repository;

import bg.softuni.human_resource_managements.model.entity.Role;
import bg.softuni.human_resource_managements.model.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(RoleName roleName);
}
