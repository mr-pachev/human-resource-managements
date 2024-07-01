package bg.softuni.human_resource_managements.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "positions")
public class Position extends BaseEntity{
    @NotBlank
    @Size(min = 2, max = 20)
    private String name;
    @NotBlank
    @Size(min = 3, max = 200)
    private String description;
    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department departmentPositions;
    @NotEmpty
    @OneToMany(mappedBy = "position")
    private List<Employee> employees;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Department getDepartmentPositions() {
        return departmentPositions;
    }

    public void setDepartmentPositions(Department departmentPositions) {
        this.departmentPositions = departmentPositions;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

}
