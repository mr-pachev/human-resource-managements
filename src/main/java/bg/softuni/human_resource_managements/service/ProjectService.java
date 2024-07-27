package bg.softuni.human_resource_managements.service;

import bg.softuni.human_resource_managements.model.dto.*;

import java.util.List;

public interface ProjectService {
    //get all projects
    List<ProjectDTO> getAllProjectsDTOS();

    //checking is exist project by name
    boolean isExistProject(String projectName);

    //add new project
    boolean addProject(AddProjectDTO addProjectDTO);

    //get project by id
    ProjectDTO getProjectDTOByID(long id);

    //edit project
    void editProject(ProjectDTO projectDTO);

    //delete project
    void removeProject(long id);

    //get all employees from current project
    List<EmployeeDTO> allProjectEmployees(long id);

    //get all employees names
    List<ProjectEmployeeDTO> getAllEmployeesNames();

    //checking is exist current employee in current project
    boolean isExistEmployeeInProject(String employeeName, long idPr);

    //add current employee in current project
    void addProjectEmployee(ProjectEmployeeDTO projectEmployeeDTO, long idPr);

    //delete current employee from current project
    void removeEmployeeFromProject(long idEm, long idPr);
}
