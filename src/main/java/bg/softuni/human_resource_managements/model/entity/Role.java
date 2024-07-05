package bg.softuni.human_resource_managements.model.entity;

import bg.softuni.human_resource_managements.model.enums.RoleName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity{

    @Enumerated(EnumType.STRING)
    private RoleName roleName;
    @OneToMany(mappedBy = "role")
    private List<User> users;

    public Role() {
        this.users = new ArrayList<>();
    }

    public Role(RoleName roleName) {
        this();

        this.roleName = roleName;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
