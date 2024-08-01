package bg.softuni.human_resource_managements.model;

import bg.softuni.human_resource_managements.model.dto.AddProjectDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddProjectDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidAddProjectDTO() {
        AddProjectDTO addProjectDTO = new AddProjectDTO();
        addProjectDTO.setName("Valid Project");
        addProjectDTO.setDescription("This is a valid description.");
        addProjectDTO.setStartDate("2023-08-11");
        addProjectDTO.setEndDate("2023-12-31");
        addProjectDTO.setResponsibleDepartment("IT_DEPARTMENT");

        Set<ConstraintViolation<AddProjectDTO>> violations = validator.validate(addProjectDTO);
        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidName() {
        AddProjectDTO addProjectDTO = new AddProjectDTO();
        addProjectDTO.setName("Yo");
        addProjectDTO.setDescription("This is a valid description.");
        addProjectDTO.setStartDate("2023-08-11");
        addProjectDTO.setEndDate("2023-12-31");
        addProjectDTO.setResponsibleDepartment("IT_DEPARTMENT");

        Set<ConstraintViolation<AddProjectDTO>> violations = validator.validate(addProjectDTO);
        assertEquals(1, violations.size());
    }

    @Test
    public void testInvalidDescription() {
        AddProjectDTO addProjectDTO = new AddProjectDTO();
        addProjectDTO.setName("Valid Project");
        addProjectDTO.setDescription("Short");
        addProjectDTO.setStartDate("2023-08-11");
        addProjectDTO.setEndDate("2023-12-31");
        addProjectDTO.setResponsibleDepartment("IT_DEPARTMENT");

        Set<ConstraintViolation<AddProjectDTO>> violations = validator.validate(addProjectDTO);
        assertEquals(1, violations.size());
    }

    @Test
    public void testBlankStartDate() {
        AddProjectDTO addProjectDTO = new AddProjectDTO();
        addProjectDTO.setName("Valid Project");
        addProjectDTO.setDescription("This is a valid description.");
        addProjectDTO.setStartDate("");
        addProjectDTO.setEndDate("2023-12-31");
        addProjectDTO.setResponsibleDepartment("IT_DEPARTMENT");

        Set<ConstraintViolation<AddProjectDTO>> violations = validator.validate(addProjectDTO);
        assertEquals(1, violations.size());
    }


    @Test
    public void testGetName() {
        AddProjectDTO addProjectDTO = new AddProjectDTO();
        addProjectDTO.setName("Project Name");
        assertEquals("Project Name", addProjectDTO.getName());
    }

    @Test
    public void testSetName() {
        AddProjectDTO addProjectDTO = new AddProjectDTO();
        addProjectDTO.setName("Project Name");
        assertEquals("Project Name", addProjectDTO.getName());
    }

    @Test
    public void testGetDescription() {
        AddProjectDTO addProjectDTO = new AddProjectDTO();
        addProjectDTO.setDescription("This is a description.");
        assertEquals("This is a description.", addProjectDTO.getDescription());
    }

    @Test
    public void testSetDescription() {
        AddProjectDTO addProjectDTO = new AddProjectDTO();
        addProjectDTO.setDescription("This is a description.");
        assertEquals("This is a description.", addProjectDTO.getDescription());
    }

    @Test
    public void testGetStartDate() {
        AddProjectDTO addProjectDTO = new AddProjectDTO();
        addProjectDTO.setStartDate("2023-08-01");
        assertEquals("2023-08-01", addProjectDTO.getStartDate());
    }

    @Test
    public void testSetStartDate() {
        AddProjectDTO addProjectDTO = new AddProjectDTO();
        addProjectDTO.setStartDate("2023-08-01");
        assertEquals("2023-08-01", addProjectDTO.getStartDate());
    }

    @Test
    public void testGetEndDate() {
        AddProjectDTO addProjectDTO = new AddProjectDTO();
        addProjectDTO.setEndDate("2023-12-31");
        assertEquals("2023-12-31", addProjectDTO.getEndDate());
    }

    @Test
    public void testSetEndDate() {
        AddProjectDTO addProjectDTO = new AddProjectDTO();
        addProjectDTO.setEndDate("2023-12-31");
        assertEquals("2023-12-31", addProjectDTO.getEndDate());
    }

    @Test
    public void testGetResponsibleDepartment() {
        AddProjectDTO addProjectDTO = new AddProjectDTO();
        addProjectDTO.setResponsibleDepartment("Development");
        assertEquals("Development", addProjectDTO.getResponsibleDepartment());
    }

    @Test
    public void testSetResponsibleDepartment() {
        AddProjectDTO addProjectDTO = new AddProjectDTO();
        addProjectDTO.setResponsibleDepartment("Development");
        assertEquals("Development", addProjectDTO.getResponsibleDepartment());
    }
}
