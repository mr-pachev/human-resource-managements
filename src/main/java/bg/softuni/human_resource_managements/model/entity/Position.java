package bg.softuni.human_resource_managements.model.entity;

import bg.softuni.human_resource_managements.model.enums.PositionName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "positions")
public class Position extends BaseEntity{
    @NotBlank
    @Enumerated(EnumType.STRING)
    private PositionName positionName;
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

    public Position() {
        this.employees = new ArrayList<>();
    }

    public Position(PositionName positionName, String description, Department departmentPositions) {
        this();

        this.positionName = positionName;
        this.description = description;
        this.departmentPositions = departmentPositions;
    }

    public PositionName getPositionName() {
        return positionName;
    }

    public void setPositionName(PositionName positionName) {
        this.positionName = positionName;
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
