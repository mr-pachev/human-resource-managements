package bg.softuni.human_resource_managements.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "departments")
public class Department extends BaseEntity{
    private String name;
    private String descriptions;


}
