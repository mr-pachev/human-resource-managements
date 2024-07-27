package bg.softuni.human_resource_managements.service;

import bg.softuni.human_resource_managements.model.dto.AddEmployeeDTO;
import bg.softuni.human_resource_managements.model.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    //get all employees
    List<EmployeeDTO> getAllEmployees();

    //checking is exist employee by full name
    boolean isExistEmployee(String managerFullName);

    //checking is exist employee by identification number
    boolean isExistEmployeeByIN(String identificationNumber);

    //add new employees
    void addEmployee(AddEmployeeDTO addEmployeeDTO);

    //get employee by id
    EmployeeDTO getEmployeeByID(long id);

    //edit employee
    void editEmployee(EmployeeDTO employeeDTO);

    //remove employee
    void removeEmployee(long id);
}
