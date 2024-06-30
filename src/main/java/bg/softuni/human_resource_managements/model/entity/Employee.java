package bg.softuni.human_resource_managements.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity{
    private String firstName;
    private String middleName;
    private String LastName;
    private int identificationNumber;
    private int age;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToOne
    private Position position;
    @ManyToOne
    private Employee supervisor;
    @ManyToOne
    private Department department;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
