package bg.softuni.human_resource_managements.model;

import bg.softuni.human_resource_managements.model.dto.AddEmployeeDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddEmployeeDTOTest {
    @Test
    void testGettersAndSetters() {
        AddEmployeeDTO dto = new AddEmployeeDTO();

        dto.setFirstName("Ivan");
        assertEquals("Ivan", dto.getFirstName());

        dto.setMiddleName("Georgiev");
        assertEquals("Georgiev", dto.getMiddleName());

        dto.setLastName("Ivanov");
        assertEquals("Ivanov", dto.getLastName());

        dto.setIdentificationNumber("1234567890");
        assertEquals("1234567890", dto.getIdentificationNumber());

        dto.setAge(40);
        assertEquals(40, dto.getAge());

        dto.setStartDate("2024-08-11");
        assertEquals("2024-08-11", dto.getStartDate());

        dto.setPosition("Developer");
        assertEquals("Developer", dto.getPosition());

        dto.setDepartment("IT");
        assertEquals("IT", dto.getDepartment());

        dto.setEducation("Bachelor's");
        assertEquals("Bachelor's", dto.getEducation());
    }

    @Test
    void testValidationConstraints() {
        AddEmployeeDTO dto = new AddEmployeeDTO();

        // Testing valid values
        dto.setFirstName("John");
        dto.setMiddleName("A.");
        dto.setLastName("Doe");
        dto.setIdentificationNumber("1234567890");
        dto.setAge(30);
        dto.setStartDate("2024-08-01");
        dto.setPosition("Developer");
        dto.setDepartment("IT");
        dto.setEducation("Bachelor's");

        assertTrue(isValid(dto), "DTO should be valid with correct data.");

        // Testing invalid values
        dto.setFirstName(""); //blank
        assertFalse(isValid(dto), "DTO should be invalid with empty firstName.");

        dto.setFirstName("Ivan");
        dto.setMiddleName(""); //blank
        assertFalse(isValid(dto), "DTO should be invalid with empty middleName.");

        dto.setMiddleName("A.");
        dto.setLastName(""); //blank
        assertFalse(isValid(dto), "DTO should be invalid with empty lastName.");
    }

    //checks DTO is valid
    private boolean isValid(AddEmployeeDTO dto) {
        return !dto.getFirstName().isEmpty() &&
                !dto.getMiddleName().isEmpty() &&
                !dto.getLastName().isEmpty() &&
                dto.getIdentificationNumber().length() == 10 &&
                dto.getAge() >= 18 &&
                !dto.getStartDate().isEmpty() &&
                !dto.getPosition().isEmpty() &&
                !dto.getDepartment().isEmpty() &&
                !dto.getEducation().isEmpty();
    }
}
