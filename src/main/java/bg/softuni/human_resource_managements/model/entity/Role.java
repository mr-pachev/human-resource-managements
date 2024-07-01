package bg.softuni.human_resource_managements.model.entity;

import bg.softuni.human_resource_managements.model.enums.RoleName;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity{
    @NotEmpty
    private RoleName roleName;
    @NotEmpty
    @OneToMany(mappedBy = "role")
    private List<Employee> employees;

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
