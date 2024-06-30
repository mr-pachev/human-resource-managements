package bg.softuni.human_resource_managements.model.entity;

import bg.softuni.human_resource_managements.model.enums.RoleName;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity{
    private RoleName roleName;
    @OneToMany(mappedBy = "role")
    private List<Employee> employees;
}
