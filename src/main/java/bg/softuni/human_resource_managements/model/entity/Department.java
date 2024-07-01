package bg.softuni.human_resource_managements.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "departments")
public class Department extends BaseEntity{
    @NotBlank
    @Size(min = 3, max = 30)
    private String name;
    @NotBlank
    @Size(min = 5, max = 200)
    private String descriptions;

    @NotEmpty
    @OneToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;
    @NotEmpty
    @OneToMany(mappedBy = "departmentPositions")
    private List<Position> position;
    @NotEmpty
    @OneToMany(mappedBy = "department")
    private List<Employee> employees;
    @NotEmpty
    @OneToMany(mappedBy = "responsibleDepartment")
    private List<Project>  projects;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public List<Position> getPosition() {
        return position;
    }

    public void setPosition(List<Position> position) {
        this.position = position;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
