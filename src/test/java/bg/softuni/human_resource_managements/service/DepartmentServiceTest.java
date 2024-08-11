package bg.softuni.human_resource_managements.service;

import bg.softuni.human_resource_managements.model.dto.*;
import bg.softuni.human_resource_managements.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DepartmentServiceTest {

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentServiceImpl departmentServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllDepartments() {
        List<String> departmentNames = Arrays.asList("HR", "IT", "Finance");
        when(departmentService.getAllDepartments()).thenReturn(departmentNames);

        List<String> result = departmentService.getAllDepartments();
        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.contains("HR"));
        assertTrue(result.contains("IT"));
        assertTrue(result.contains("Finance"));
    }

    @Test
    public void testGetAllDepartmentsDTOS() {
        DepartmentDTO dept1 = new DepartmentDTO();
        dept1.setId(1L);
        dept1.setDepartmentName("HR");

        DepartmentDTO dept2 = new DepartmentDTO();
        dept2.setId(2L);
        dept2.setDepartmentName("IT");

        List<DepartmentDTO> departmentDTOs = Arrays.asList(dept1, dept2);
        when(departmentService.getAllDepartmentsDTOS()).thenReturn(departmentDTOs);

        List<DepartmentDTO> result = departmentService.getAllDepartmentsDTOS();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("HR", result.get(0).getDepartmentName());
        assertEquals("IT", result.get(1).getDepartmentName());
    }

    @Test
    public void testIsExistDepartment() {
        when(departmentService.isExistDepartment("HR")).thenReturn(true);
        when(departmentService.isExistDepartment("Marketing")).thenReturn(false);

        assertTrue(departmentService.isExistDepartment("HR"));
        assertFalse(departmentService.isExistDepartment("Marketing"));
    }

    @Test
    public void testAddDepartment() {
        AddDepartmentDTO addDepartmentDTO = new AddDepartmentDTO();
        addDepartmentDTO.setDepartmentName("Marketing");

        doNothing().when(departmentService).addDepartment(addDepartmentDTO);

        departmentService.addDepartment(addDepartmentDTO);
        verify(departmentService, times(1)).addDepartment(addDepartmentDTO);
    }

    @Test
    public void testGetDepartmentDTOByID() {
        DepartmentDTO dept = new DepartmentDTO();
        dept.setId(1L);
        dept.setDepartmentName("HR");
        when(departmentService.getDepartmentDTOByID(1L)).thenReturn(dept);

        DepartmentDTO result = departmentService.getDepartmentDTOByID(1L);
        assertNotNull(result);
        assertEquals("HR", result.getDepartmentName());
    }

    @Test
    public void testEditDepartment() {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(1L);
        departmentDTO.setDepartmentName("HR");
        doNothing().when(departmentService).editDepartment(departmentDTO);

        departmentService.editDepartment(departmentDTO);
        verify(departmentService, times(1)).editDepartment(departmentDTO);
    }

    @Test
    public void testRemoveDepartment() {
        doNothing().when(departmentService).removeDepartment(1L);

        departmentService.removeDepartment(1L);
        verify(departmentService, times(1)).removeDepartment(1L);
    }

    @Test
    public void testAllDepartmentEmployees() {
        EmployeeDTO emp1 = new EmployeeDTO();
        emp1.setId(1L);
        emp1.setFirstName("Ivan");
        emp1.setLastName("Pachev");

        EmployeeDTO emp2 = new EmployeeDTO();
        emp2.setId(1L);
        emp2.setFirstName("Pesho");
        emp2.setLastName("Peshev");

        List<EmployeeDTO> employees = Arrays.asList(emp1, emp2);
        when(departmentService.allDepartmentEmployees(1L)).thenReturn(employees);

        List<EmployeeDTO> result = departmentService.allDepartmentEmployees(1L);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Ivan Pachev", result.get(0).getFirstName() + " " + result.get(0).getLastName());
    }

    @Test
    public void testIsExistEmployeeInDepartment() {
        when(departmentService.isExistEmployeeInDepartment("John Doe", 1L)).thenReturn(true);
        when(departmentService.isExistEmployeeInDepartment("Alice Smith", 1L)).thenReturn(false);

        assertTrue(departmentService.isExistEmployeeInDepartment("John Doe", 1L));
        assertFalse(departmentService.isExistEmployeeInDepartment("Alice Smith", 1L));
    }

    @Test
    public void testAddDepartmentEmployee() {
        EmployeeNameDTO employeeNameDTO = new EmployeeNameDTO();
        employeeNameDTO.setFullName("Ivan Pachev");

        doNothing().when(departmentService).addDepartmentEmployee(employeeNameDTO, 1L);

        departmentService.addDepartmentEmployee(employeeNameDTO, 1L);
        verify(departmentService, times(1)).addDepartmentEmployee(employeeNameDTO, 1L);
    }

    @Test
    public void testRemoveEmployeeFromDepartment() {
        doNothing().when(departmentService).removeEmployeeFromDepartment(1L, 1L);

        departmentService.removeEmployeeFromDepartment(1L, 1L);
        verify(departmentService, times(1)).removeEmployeeFromDepartment(1L, 1L);
    }
}
