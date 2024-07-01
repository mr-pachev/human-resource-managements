package bg.softuni.human_resource_managements.model.entity;

import bg.softuni.human_resource_managements.model.enums.EducationName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
@Entity
@Table(name = "educations")
public class Education extends BaseEntity{
    @NotEmpty
    @Enumerated(EnumType.STRING)
    private EducationName educationName;
    @NotEmpty
    @OneToMany(mappedBy = "education")
    private List<Employee> employees;

    public EducationName getEducationName() {
        return educationName;
    }

    public void setEducationName(EducationName educationName) {
        this.educationName = educationName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
