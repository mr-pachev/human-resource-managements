package bg.softuni.human_resource_managements.service;

import bg.softuni.human_resource_managements.model.dto.AddEmployeeDTO;
import bg.softuni.human_resource_managements.model.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> getAllEmployees();
    void addEmployee(AddEmployeeDTO addEmployeeDTO);
    void removeEmployee(long id);
    EmployeeDTO getEmployeeByID(long id);
    boolean isExistEmployee(String managerFullName);
    boolean isExistEmployeeByIN(String identificationNumber);
    void editEmployee(EmployeeDTO employeeDTO);
}
