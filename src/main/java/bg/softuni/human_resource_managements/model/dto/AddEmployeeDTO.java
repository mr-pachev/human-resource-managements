package bg.softuni.human_resource_managements.model.dto;

import bg.softuni.human_resource_managements.model.entity.*;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public class AddEmployeeDTO {
    @NotBlank
    @Size(min = 3, max = 10)
    private String firstName;
    @NotBlank
    @Size(min = 3, max = 10)
    private String middleName;
    @NotBlank
    @Size(min = 3, max = 10)
    private String LastName;
    @NotEmpty
    @Length(min = 10, max = 10)
    private int identificationNumber;
    @NotEmpty
    @Length(min = 2, max = 2)
    private int age;
    @NotEmpty
    private LocalDate startDate;
    @NotEmpty
    private LocalDate endDate;
    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;
    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private Employee supervisor;
    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "education_id")
    private Education education;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public int getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(int identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Employee getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Employee supervisor) {
        this.supervisor = supervisor;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }
}
