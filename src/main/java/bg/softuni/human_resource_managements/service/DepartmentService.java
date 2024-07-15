package bg.softuni.human_resource_managements.service;

import bg.softuni.human_resource_managements.model.dto.DepartmentDTO;

import java.util.List;

public interface DepartmentService {
    List<String> getAllDepartments();

    List<DepartmentDTO> getAllDepartmentsDTOS();

    DepartmentDTO getDepartmentDTOByID(long id);
    void editDepartment(DepartmentDTO departmentDTO);
}
