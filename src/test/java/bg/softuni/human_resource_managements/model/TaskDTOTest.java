package bg.softuni.human_resource_managements.model;

import bg.softuni.human_resource_managements.model.dto.TaskDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskDTOTest {
    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testTaskDTOValid() {
        TaskDTO task = new TaskDTO();

        task.setId(1);
        task.setName("Valid Name");
        task.setDescription("This is a valid description with more than 10 characters.");
        task.setStartDate("2024-08-11");
        task.setEndDate("2024-12-31");
        task.setEmployeeName("Ivan Pachev");

        Set<jakarta.validation.ConstraintViolation<TaskDTO>> violations = validator.validate(task);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testTaskDTOInvalidName() {
        TaskDTO task = new TaskDTO();

        task.setId(1);
        task.setName("");  //blank name
        task.setDescription("This is a valid description with more than 10 characters.");
        task.setStartDate("2024-08-11");
        task.setEndDate("2024-12-31");
        task.setEmployeeName("Ivan Pachev");

        Set<jakarta.validation.ConstraintViolation<TaskDTO>> violations = validator.validate(task);

        assertEquals(2, violations.size());
        Set<String> violationMessages = violations.stream()
                .map(v -> v.getMessage())
                .collect(Collectors.toSet());

        assertTrue(violationMessages.contains("must not be blank"));
        assertTrue(violationMessages.contains("size must be between 3 and 50"));
    }

    @Test
    public void testTaskDTOInvalidDescription() {
        TaskDTO task = new TaskDTO();

        task.setId(1);
        task.setName("Valid Name");
        task.setDescription("Short");  //too short
        task.setStartDate("2024-08-11");
        task.setEndDate("2024-12-31");
        task.setEmployeeName("Ivan Pachev");

        Set<jakarta.validation.ConstraintViolation<TaskDTO>> violations = validator.validate(task);

        assertEquals(1, violations.size());
        assertEquals("size must be between 10 and 255", violations.iterator().next().getMessage());
    }

    @Test
    public void testTaskDTOInvalidStartDate() {
        TaskDTO task = new TaskDTO();

        task.setId(1);
        task.setName("Valid Name");
        task.setDescription("This is a valid description with more than 10 characters.");
        task.setStartDate("");  //blank startDate
        task.setEndDate("2024-12-31");
        task.setEmployeeName("Ivan Pachev");

        Set<ConstraintViolation<TaskDTO>> violations = validator.validate(task);

        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    public void testTaskDTOInvalidEmployeeName() {
        TaskDTO task = new TaskDTO();

        task.setId(1);
        task.setName("Valid Name");
        task.setDescription("This is a valid description with more than 10 characters.");
        task.setStartDate("2024-08-11");
        task.setEndDate("2024-12-31");
        task.setEmployeeName("");  //blank employee name

        Set<ConstraintViolation<TaskDTO>> violations = validator.validate(task);

        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    public void testGettersAndSetters() {
        TaskDTO task = new TaskDTO();

        task.setId(1);
        assertEquals(1, task.getId());

        task.setName("Task Name");
        assertEquals("Task Name", task.getName());

        task.setDescription("This is a description that is more than 10 characters long.");
        assertEquals("This is a description that is more than 10 characters long.", task.getDescription());

        task.setStartDate("2024-08-11");
        assertEquals("2024-08-11", task.getStartDate());

        task.setEndDate("2024-12-31");
        assertEquals("2024-12-31", task.getEndDate());

        task.setEmployeeName("Ivan Pachev");
        assertEquals("Ivan Pachev", task.getEmployeeName());
    }
}
