package bg.softuni.human_resource_managements.service;

import bg.softuni.human_resource_managements.model.dto.AddEmployeeDTO;

public interface EmployeeService {
    boolean addEmployee(AddEmployeeDTO addEmployeeDTO);
}
