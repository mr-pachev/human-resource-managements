package bg.softuni.human_resource_managements.service;

import bg.softuni.human_resource_managements.model.dto.*;

import java.util.List;

public interface ProjectService {
    boolean addProject(AddProjectDTO addProjectDTO);

    boolean isExistProject(String projectName);
    List<ProjectDTO> getAllProjectsDTOS();

    List<ProjectEmployeeDTO> getAllEmployees();

    ProjectDTO getProjectDTOByID(long id);

    void removeEmployeeFromProject(long idEm, long idPr);
    void removeProject(long id);

    List<EmployeeDTO> allProjectEmployees(long id);
    void editProject(ProjectDTO projectDTO);

    void addProjectEmployee(String name);

    boolean isExistEmployee(String name);
}
