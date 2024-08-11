package bg.softuni.human_resource_managements.model;

import bg.softuni.human_resource_managements.model.dto.ProjectDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidProjectDTO() {
        ProjectDTO projectDTO = new ProjectDTO();

        projectDTO.setId(1);
        projectDTO.setName("Test Project");
        projectDTO.setDescription("This is a valid project description.");
        projectDTO.setStartDate("2024-08-11");
        projectDTO.setEndDate("2024-12-31");
        projectDTO.setResponsibleDepartment("IT_DEPARTMENT");
        projectDTO.setEmployees(List.of("Ivan Ivanov", "Nikolai Nikolov"));

        Set<ConstraintViolation<ProjectDTO>> violations = validator.validate(projectDTO);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testNameTooShort() {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("Te");

        Set<ConstraintViolation<ProjectDTO>> violations = validator.validate(projectDTO);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("size must be between 3 and 30")));
    }

    @Test
    public void testDescriptionTooShort() {
        ProjectDTO projectDTO = new ProjectDTO();

        projectDTO.setDescription("Too short");

        Set<ConstraintViolation<ProjectDTO>> violations = validator.validate(projectDTO);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("size must be between 10 and 255")));
    }

    @Test
    public void testStartDateNotBlank() {
        ProjectDTO projectDTO = new ProjectDTO();

        projectDTO.setStartDate("");

        Set<ConstraintViolation<ProjectDTO>> violations = validator.validate(projectDTO);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("must not be blank")));
    }

    @Test
    public void testResponsibleDepartmentTooShort() {
        ProjectDTO projectDTO = new ProjectDTO();

        projectDTO.setResponsibleDepartment("RD");

        Set<ConstraintViolation<ProjectDTO>> violations = validator.validate(projectDTO);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("size must be between 3 and 50")));
    }

    @Test
    public void testGettersAndSetters() {
        ProjectDTO projectDTO = new ProjectDTO();

        projectDTO.setId(1);
        projectDTO.setName("Project Name");
        projectDTO.setDescription("Project Description");
        projectDTO.setStartDate("2024-08-11");
        projectDTO.setEndDate("2024-12-31");
        projectDTO.setResponsibleDepartment("IT_DEPARTMENT");
        projectDTO.setEmployees(List.of("Pencho Ginin", "Georgi Georgiev"));

        assertEquals(1, projectDTO.getId());
        assertEquals("Project Name", projectDTO.getName());
        assertEquals("Project Description", projectDTO.getDescription());
        assertEquals("2024-08-11", projectDTO.getStartDate());
        assertEquals("2024-12-31", projectDTO.getEndDate());
        assertEquals("IT_DEPARTMENT", projectDTO.getResponsibleDepartment());
        assertEquals(List.of("Pencho Ginin", "Georgi Georgiev"), projectDTO.getEmployees());
    }
}