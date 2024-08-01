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
}
