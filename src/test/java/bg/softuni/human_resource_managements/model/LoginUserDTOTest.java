package bg.softuni.human_resource_managements.model;

import bg.softuni.human_resource_managements.model.dto.LoginUserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LoginUserDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidLoginUserDTO() {
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO.setUserId(1L);
        loginUserDTO.setUsername("validUser");
        loginUserDTO.setPassword("validPass");

        Set<ConstraintViolation<LoginUserDTO>> violations = validator.validate(loginUserDTO);

        assertTrue(violations.isEmpty(), "Expected no constraint violations");
    }

    @Test
    void testInvalidUsernameTooShort() {
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO.setUserId(1L);
        loginUserDTO.setUsername("ab"); //too short
        loginUserDTO.setPassword("validPass");

        Set<ConstraintViolation<LoginUserDTO>> violations = validator.validate(loginUserDTO);

        assertFalse(violations.isEmpty(), "Expected constraint violations for username");
    }

    @Test
    void testInvalidUsernameTooLong() {
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO.setUserId(1L);
        loginUserDTO.setUsername("thisusernameiswaytoolong"); //too long
        loginUserDTO.setPassword("validPass");

        Set<ConstraintViolation<LoginUserDTO>> violations = validator.validate(loginUserDTO);

        assertFalse(violations.isEmpty(), "Expected constraint violations for username");
    }

    @Test
    void testInvalidPasswordTooShort() {
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO.setUserId(1L);
        loginUserDTO.setUsername("validUser");
        loginUserDTO.setPassword("123"); //too short

        Set<ConstraintViolation<LoginUserDTO>> violations = validator.validate(loginUserDTO);

        assertFalse(violations.isEmpty(), "Expected constraint violations for password");
    }

    @Test
    void testInvalidPasswordTooLong() {
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO.setUserId(1L);
        loginUserDTO.setUsername("validUser");
        loginUserDTO.setPassword("thispasswordiswaytoolong"); //too long

        Set<ConstraintViolation<LoginUserDTO>> violations = validator.validate(loginUserDTO);

        assertFalse(violations.isEmpty(), "Expected constraint violations for password");
    }
}
