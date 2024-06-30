package bg.softuni.human_resource_managements.model.entity;

import bg.softuni.human_resource_managements.model.enums.EducationName;

import java.util.List;

public class Education extends BaseEntity{
    private EducationName educationName;
    private List<Employee> employees;
}
