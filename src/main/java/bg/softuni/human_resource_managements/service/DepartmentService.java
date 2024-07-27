package bg.softuni.human_resource_managements.service;

import bg.softuni.human_resource_managements.model.dto.*;

import java.util.List;

public interface DepartmentService {
    //get all departments names
    List<String> getAllDepartments();
    //get all departments
    List<DepartmentDTO> getAllDepartmentsDTOS();
    //checking is exist department by name
    boolean isExistDepartment(String name);
    //add new department
    void addDepartment(AddDepartmentDTO addDepartmentDTO);
    //get department
    DepartmentDTO getDepartmentDTOByID(long id);
    //edit department
    void editDepartment(DepartmentDTO departmentDTO);
    //delete department
    void removeDepartment(long id);


    //get all employees names from current department
    List<DepartmentEmployeeDTO> getAllEmployeesNames();
    //get all employees from current department
    List<EmployeeDTO> allDepartmentEmployees(long id);
    //checking is exist current employee in current department
    boolean isExistEmployeeInDepartment(String employeeName, long idDep);
    //add employee in current department
    void addDepartmentEmployee(DepartmentEmployeeDTO departmentEmployeeDTO, long idDep);

}
