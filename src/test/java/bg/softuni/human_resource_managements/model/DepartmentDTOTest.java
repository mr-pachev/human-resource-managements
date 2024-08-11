package bg.softuni.human_resource_managements.model;


import bg.softuni.human_resource_managements.model.dto.DepartmentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DepartmentDTOTest {
    private Validator validator;
    private DepartmentDTO departmentDTO;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        departmentDTO = new DepartmentDTO();
    }

    @Test
    public void testDepartmentNameNotBlank() {
        departmentDTO.setDepartmentName(""); //blank department name
        departmentDTO.setIdentificationNumber("1234567891");
        departmentDTO.setDescriptions("This is the HR department.");
        departmentDTO.setManager("Ivan Pachev");
        departmentDTO.setEmployees(List.of("Employee1", "Employee2"));
        departmentDTO.setProjects(List.of("Project1", "Project2"));

        Set<ConstraintViolation<DepartmentDTO>> violations = validator.validate(departmentDTO);

        assertEquals(2, violations.size());

        Set<String> violationMessages = violations.stream()
                .map(v -> v.getMessage())
                .collect(Collectors.toSet());

        assertTrue(violationMessages.contains("must not be blank"));
        assertTrue(violationMessages.contains("size must be between 3 and 50"));
    }

    @Test
    public void testDepartmentNameSize() {
        departmentDTO.setDepartmentName("AB"); //too short
        departmentDTO.setIdentificationNumber("1234567891");
        departmentDTO.setDescriptions("This is the HR department.");
        departmentDTO.setManager("Ivan Pachev");
        departmentDTO.setEmployees(List.of("Employee1", "Employee2"));
        departmentDTO.setProjects(List.of("Project1", "Project2"));

        Set<ConstraintViolation<DepartmentDTO>> violations = validator.validate(departmentDTO);

        assertEquals(1, violations.size());
        assertEquals("size must be between 3 and 50", violations.iterator().next().getMessage());

        departmentDTO.setDepartmentName("A".repeat(51)); //too long
        violations = validator.validate(departmentDTO);

        assertEquals(1, violations.size());
        assertEquals("size must be between 3 and 50", violations.iterator().next().getMessage());
    }

    @Test
    public void testManagerNotBlank() {
        departmentDTO.setDepartmentName("Human Resources");
        departmentDTO.setIdentificationNumber("1234567891");
        departmentDTO.setDescriptions("This is the HR department.");
        departmentDTO.setManager(""); //blank manager
        departmentDTO.setEmployees(List.of("Employee1", "Employee2"));
        departmentDTO.setProjects(List.of("Project1", "Project2"));

        Set<ConstraintViolation<DepartmentDTO>> violations = validator.validate(departmentDTO);

        Set<String> violationMessages = violations.stream()
                .map(v -> v.getMessage())
                .collect(Collectors.toSet());

        assertTrue(violationMessages.contains("must not be blank"));
        assertTrue(violationMessages.contains("size must be between 9 and 50"));
    }

    @Test
    public void testManagerSize() {
        departmentDTO.setDepartmentName("Human Resources");
        departmentDTO.setIdentificationNumber("1234567891");
        departmentDTO.setDescriptions("This is the HR department.");
        departmentDTO.setManager("12345678"); //too short
        departmentDTO.setEmployees(List.of("Employee1", "Employee2"));
        departmentDTO.setProjects(List.of("Project1", "Project2"));

        Set<ConstraintViolation<DepartmentDTO>> violations = validator.validate(departmentDTO);

        assertEquals(1, violations.size());
        assertEquals("size must be between 9 and 50", violations.iterator().next().getMessage());

        departmentDTO.setManager("A".repeat(51)); //too long
        violations = validator.validate(departmentDTO);

        assertEquals(1, violations.size());
        assertEquals("size must be between 9 and 50", violations.iterator().next().getMessage());
    }

    @Test
    public void testIdentificationNumberSize() {
        departmentDTO.setDepartmentName("Human Resources");
        departmentDTO.setIdentificationNumber("123456789"); //too short
        departmentDTO.setDescriptions("This is the HR department.");
        departmentDTO.setManager("Ivan Pachev");
        departmentDTO.setEmployees(List.of("Employee1", "Employee2"));
        departmentDTO.setProjects(List.of("Project1", "Project2"));

        Set<ConstraintViolation<DepartmentDTO>> violations = validator.validate(departmentDTO);

        assertEquals(1, violations.size());
        assertEquals("size must be between 10 and 10", violations.iterator().next().getMessage());

        departmentDTO.setIdentificationNumber("12345678901"); //too long
        violations = validator.validate(departmentDTO);

        assertEquals(1, violations.size());
        assertEquals("size must be between 10 and 10", violations.iterator().next().getMessage());
    }

    @Test
    public void testDescriptionsNotBlank() {
        departmentDTO.setDepartmentName("Human Resources");
        departmentDTO.setIdentificationNumber("1234567891");
        departmentDTO.setDescriptions(""); //blank description
        departmentDTO.setManager("Ivan Pachev");
        departmentDTO.setEmployees(List.of("Employee1", "Employee2"));
        departmentDTO.setProjects(List.of("Project1", "Project2"));

        Set<ConstraintViolation<DepartmentDTO>> violations = validator.validate(departmentDTO);

        assertEquals(2, violations.size());

        Set<String> violationMessages = violations.stream()
                .map(v -> v.getMessage())
                .collect(Collectors.toSet());

        assertTrue(violationMessages.contains("must not be blank"));
        assertTrue(violationMessages.contains("size must be between 10 and 255"));
    }

    @Test
    public void testDescriptionsSize() {
        departmentDTO.setDepartmentName("Human Resources");
        departmentDTO.setIdentificationNumber("1234567891");
        departmentDTO.setDescriptions("123456789"); //too short
        departmentDTO.setManager("Ivan Pachev");
        departmentDTO.setEmployees(List.of("Employee1", "Employee2"));
        departmentDTO.setProjects(List.of("Project1", "Project2"));

        Set<ConstraintViolation<DepartmentDTO>> violations = validator.validate(departmentDTO);

        assertEquals(1, violations.size());
        assertEquals("size must be between 10 and 255", violations.iterator().next().getMessage());

        departmentDTO.setDescriptions("A".repeat(256)); //to long
        violations = validator.validate(departmentDTO);

        assertEquals(1, violations.size());
        assertEquals("size must be between 10 and 255", violations.iterator().next().getMessage());
    }

    @Test
    public void testSettersAndGetters() {
        departmentDTO.setId(1);
        assertEquals(1, departmentDTO.getId());

        departmentDTO.setDepartmentName("Human Resources");
        assertEquals("Human Resources", departmentDTO.getDepartmentName());

        departmentDTO.setManager("Ivan Pachev");
        assertEquals("Ivan Pachev", departmentDTO.getManager());

        departmentDTO.setIdentificationNumber("1234567890");
        assertEquals("1234567890", departmentDTO.getIdentificationNumber());

        departmentDTO.setDescriptions("This is the HR department.");
        assertEquals("This is the HR department.", departmentDTO.getDescriptions());

        departmentDTO.setEmployees(List.of("Employee1", "Employee2"));
        assertEquals(List.of("Employee1", "Employee2"), departmentDTO.getEmployees());

        departmentDTO.setProjects(List.of("Project1", "Project2"));
        assertEquals(List.of("Project1", "Project2"), departmentDTO.getProjects());
    }
}
