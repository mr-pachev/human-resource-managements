package bg.softuni.human_resource_managements.model;

import bg.softuni.human_resource_managements.model.dto.AddTaskDTO;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddTaskDTOTest {
    @Test
    public void testGettersAndSetters() {
        AddTaskDTO task = new AddTaskDTO();

        long id = 1L;
        String name = "Test Task";
        String description = "This is a test task description that is long enough.";
        String startDate = "2024-08-11";
        String endDate = "2024-08-02";
        String employeeName = "Munio Munev";

        task.setId(id);
        task.setName(name);
        task.setDescription(description);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        task.setEmployeeName(employeeName);

        assertEquals(id, task.getId());
        assertEquals(name, task.getName());
        assertEquals(description, task.getDescription());
        assertEquals(startDate, task.getStartDate());
        assertEquals(endDate, task.getEndDate());
        assertEquals(employeeName, task.getEmployeeName());
    }
}
