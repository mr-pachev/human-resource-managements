package bg.softuni.human_resource_managements.service;

import bg.softuni.human_resource_managements.model.dto.*;

import java.util.List;

public interface DepartmentService {
    List<String> getAllDepartments();

    List<DepartmentDTO> getAllDepartmentsDTOS();
    List<DepartmentEmployeeDTO> getAllEmployeesNames();

    List<EmployeeDTO> allDepartmentEmployees(long id);

    DepartmentDTO getDepartmentDTOByID(long id);

    void editDepartment(DepartmentDTO departmentDTO);

    void addDepartment(AddDepartmentDTO addDepartmentDTO);
    boolean isExistDepartment(String name);

    void removeDepartment(long id);
}
