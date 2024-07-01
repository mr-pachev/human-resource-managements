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

    @Enumerated(EnumType.STRING)
    private PositionName positionName;
    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "position")
    private List<Employee> employees;

    public Position() {
        this.employees = new ArrayList<>();
    }

    public Position(PositionName positionName, String description) {
        this();

        this.positionName = positionName;
        this.description = description;
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

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

}
