package bg.softuni.human_resource_managements.model;


import bg.softuni.human_resource_managements.model.dto.EmployeeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeeDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidEmployeeDTO() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("Penio");
        employeeDTO.setMiddleName("Penkin");
        employeeDTO.setLastName("Penchov");
        employeeDTO.setAge(25);
        employeeDTO.setStartDate("2023-08-11");

        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(employeeDTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidFirstName() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("Pe");
        employeeDTO.setMiddleName("Penkin");
        employeeDTO.setLastName("Penchov");
        employeeDTO.setAge(25);
        employeeDTO.setStartDate("2023-08-11");

        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(employeeDTO);
        assertEquals(1, violations.size());
        assertEquals("size must be between 3 and 15", violations.iterator().next().getMessage());
    }

    @Test
    public void testNullFirstName() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName(null);
        employeeDTO.setMiddleName("Penkin");
        employeeDTO.setLastName("Penchov");
        employeeDTO.setAge(25);
        employeeDTO.setStartDate("2023-08-11");

        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(employeeDTO);
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    public void testGettersAndSetters() {
        EmployeeDTO employeeDTO = new EmployeeDTO();

        long id = 1L;
        employeeDTO.setId(id);
        assertEquals(id, employeeDTO.getId());

        String firstName = "Penio";
        employeeDTO.setFirstName(firstName);
        assertEquals(firstName, employeeDTO.getFirstName());

        String middleName = "Penkin";
        employeeDTO.setMiddleName(middleName);
        assertEquals(middleName, employeeDTO.getMiddleName());

        String lastName = "Penchov";
        employeeDTO.setLastName(lastName);
        assertEquals(lastName, employeeDTO.getLastName());

        String identificationNumber = "1234567890";
        employeeDTO.setIdentificationNumber(identificationNumber);
        assertEquals(identificationNumber, employeeDTO.getIdentificationNumber());

        int age = 25;
        employeeDTO.setAge(age);
        assertEquals(age, employeeDTO.getAge());

        String startDate = "2023-08-11";
        employeeDTO.setStartDate(startDate);
        assertEquals(startDate, employeeDTO.getStartDate());

        String endDate = "2023-12-31";
        employeeDTO.setEndDate(endDate);
        assertEquals(endDate, employeeDTO.getEndDate());

        String position = "Developer";
        employeeDTO.setPosition(position);
        assertEquals(position, employeeDTO.getPosition());

        String department = "IT";
        employeeDTO.setDepartment(department);
        assertEquals(department, employeeDTO.getDepartment());

        String education = "Bachelor";
        employeeDTO.setEducation(education);
        assertEquals(education, employeeDTO.getEducation());
    }
}