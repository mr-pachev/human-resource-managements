package bg.softuni.human_resource_managements.service;

import bg.softuni.human_resource_managements.model.dto.AddEmployeeDTO;
import bg.softuni.human_resource_managements.model.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    boolean addEmployee(AddEmployeeDTO addEmployeeDTO);
    List<EmployeeDTO> getAllEmployees();

    void removeEmployee(long id);
}
