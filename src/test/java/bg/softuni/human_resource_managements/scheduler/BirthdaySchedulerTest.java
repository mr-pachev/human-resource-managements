package bg.softuni.human_resource_managements.scheduler;

import bg.softuni.human_resource_managements.model.dto.EmployeeDTO;
import bg.softuni.human_resource_managements.service.BirthdayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

class BirthdaySchedulerTest {

    @Mock
    private BirthdayService birthdayService;

    @InjectMocks
    private BirthdayScheduler birthdayScheduler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCheckBirthdaysWithNoBirthdays() {
        when(birthdayService.getBirthdayEmployees()).thenReturn(Collections.emptyList());

        birthdayScheduler.checkBirthdays();

        verify(birthdayService, times(1)).getBirthdayEmployees();
    }

    @Test
    void testCheckBirthdaysWithBirthdays() {
        EmployeeDTO employee = new EmployeeDTO();

        employee.setFirstName("Penio");
        employee.setMiddleName("Penkin");
        employee.setLastName("Penchov");

        List<EmployeeDTO> employees = List.of(employee);

        when(birthdayService.getBirthdayEmployees()).thenReturn(employees);

        birthdayScheduler.checkBirthdays();

        verify(birthdayService, times(1)).getBirthdayEmployees();
    }
}
