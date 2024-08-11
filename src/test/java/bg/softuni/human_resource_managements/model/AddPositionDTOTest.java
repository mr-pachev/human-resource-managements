package bg.softuni.human_resource_managements.model;

import bg.softuni.human_resource_managements.model.dto.AddPositionDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AddPositionDTOTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidAddPositionDTO() {
        AddPositionDTO dto = new AddPositionDTO();

        dto.setPositionName("Developer");
        dto.setDescription("Responsible for writing code and developing applications.");

        Set<ConstraintViolation<AddPositionDTO>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty(), "DTO should be valid with correct data.");
    }

    @Test
    void testInvalidPositionNameTooShort() {
        AddPositionDTO dto = new AddPositionDTO();

        dto.setPositionName("Ab"); //too short
        dto.setDescription("Valid description");

        Set<ConstraintViolation<AddPositionDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty(), "DTO should be invalid with a position name that is too short.");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("positionName")), "The positionName property should be invalid.");
    }

    @Test
    void testInvalidPositionNameTooLong() {
        AddPositionDTO dto = new AddPositionDTO();

        dto.setPositionName("A".repeat(51)); //too long
        dto.setDescription("Valid description");

        Set<ConstraintViolation<AddPositionDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty(), "DTO should be invalid with a position name that is too long.");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("positionName")), "The positionName property should be invalid.");
    }

    @Test
    void testInvalidDescriptionTooShort() {
        AddPositionDTO dto = new AddPositionDTO();

        dto.setPositionName("Valid Position");
        dto.setDescription("Too short"); //too short

        Set<ConstraintViolation<AddPositionDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty(), "DTO should be invalid with a description that is too short.");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("description")), "The description property should be invalid.");
    }

    @Test
    void testInvalidDescriptionTooLong() {
        AddPositionDTO dto = new AddPositionDTO();

        dto.setPositionName("Valid Position");
        dto.setDescription("A".repeat(256)); //too long

        Set<ConstraintViolation<AddPositionDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty(), "DTO should be invalid with a description that is too long.");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("description")), "The description property should be invalid.");
    }

    @Test
    void testGettersAndSetters() {
        AddPositionDTO dto = new AddPositionDTO();

        dto.setPositionName("Software Engineer");
        assertEquals("Software Engineer", dto.getPositionName());

        dto.setDescription("Responsible for developing and maintaining software.");
        assertEquals("Responsible for developing and maintaining software.", dto.getDescription());
    }
}
