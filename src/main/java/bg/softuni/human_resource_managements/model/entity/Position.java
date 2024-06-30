package bg.softuni.human_resource_managements.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "positions")
public class Position extends BaseEntity{
    private String name;
    private String description;
    @ManyToOne
    private Employee departmentManager;
    @OneToMany
    private List<Employee>employees;
}
