package bg.softuni.human_resource_managements.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task extends BaseEntity{
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
}
