package bg.softuni.human_resource_managements.model;
import bg.softuni.human_resource_managements.model.dto.PositionDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PositionDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testPositionNameValidation() {
        PositionDTO positionDTO = new PositionDTO();

        positionDTO.setPositionName("HR");
        positionDTO.setDescription("This is a description with more than 10 characters.");

        Set<ConstraintViolation<PositionDTO>> violations = validator.validate(positionDTO);
        assertEquals(1, violations.size());

        ConstraintViolation<PositionDTO> violation = violations.iterator().next();

        assertEquals("size must be between 3 and 50", violation.getMessage());
        assertEquals("positionName", violation.getPropertyPath().toString());
    }

    @Test
    public void testDescriptionValidation() {
        PositionDTO positionDTO = new PositionDTO();

        positionDTO.setPositionName("Human Resources");
        positionDTO.setDescription("Short");

        Set<ConstraintViolation<PositionDTO>> violations = validator.validate(positionDTO);

        assertEquals(1, violations.size());

        ConstraintViolation<PositionDTO> violation = violations.iterator().next();

        assertEquals("size must be between 10 and 255", violation.getMessage());
        assertEquals("description", violation.getPropertyPath().toString());
    }

    @Test
    public void testValidPositionDTO() {
        PositionDTO positionDTO = new PositionDTO();

        positionDTO.setPositionName("Human Resources");
        positionDTO.setDescription("This is a valid description with more than 10 characters.");
        positionDTO.setEmployees(List.of("Employee1", "Employee2"));

        Set<ConstraintViolation<PositionDTO>> violations = validator.validate(positionDTO);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testSettersAndGetters() {
        PositionDTO positionDTO = new PositionDTO();

        positionDTO.setId(1L);
        positionDTO.setPositionName("Human Resources");
        positionDTO.setDescription("This is a valid description with more than 10 characters.");
        positionDTO.setEmployees(List.of("Employee1", "Employee2"));

        assertEquals(1L, positionDTO.getId());
        assertEquals("Human Resources", positionDTO.getPositionName());
        assertEquals("This is a valid description with more than 10 characters.", positionDTO.getDescription());
        assertEquals(List.of("Employee1", "Employee2"), positionDTO.getEmployees());
    }
}
