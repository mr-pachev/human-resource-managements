package bg.softuni.human_resource_managements.service.impl;

import bg.softuni.human_resource_managements.model.dto.*;
import bg.softuni.human_resource_managements.service.ProjectService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final RestClient projectRestClient;

    public ProjectServiceImpl(RestClient projectRestClient) {
        this.projectRestClient = projectRestClient;
    }

    //get all projects
    @Override
    public List<ProjectDTO> getAllProjectsDTOS() {
        return projectRestClient
                .get()
                .uri("http://localhost:8081/projects")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }

    //checking is exist project by name
    @Override
    public boolean isExistProject(String newProjectName) {
        List<ProjectDTO> projectDTOS = projectRestClient
                .get()
                .uri("http://localhost:8081/projects")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});

        return projectDTOS.stream()
                .anyMatch(project -> project.getName().equals(newProjectName));
    }

    //add new project
    @Override
    public boolean addProject(AddProjectDTO addProjectDTO) {
        if(isExistProject(addProjectDTO.getName())){
            return false;
        }

        projectRestClient
                .post()
                .uri("http://localhost:8081/projects")
                .body(addProjectDTO)
                .retrieve();

        return true;
    }

    //get project by id
    @Override
    public ProjectDTO getProjectDTOByID(long id) {
        return projectRestClient
                .get()
                .uri("http://localhost:8081/projects/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(ProjectDTO.class);
    }

    //edit project
    @Override
    public void editProject(ProjectDTO projectDTO) {
        projectRestClient
                .post()
                .uri("http://localhost:8081/projects/edit")
                .body(projectDTO)
                .retrieve();
    }

    //delete project
    @Override
    public void removeProject(long id) {
        projectRestClient.delete()
                .uri("http://localhost:8081/projects/{id}",id)
                .retrieve()
                .toBodilessEntity();
    }

    //get all employees from current project
    @Override
    public List<EmployeeDTO> allProjectEmployees(long id) {
        return projectRestClient
                .get()
                .uri("http://localhost:8081/projects/employees/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }

    //get all employees names
    @Override
    public List<ProjectEmployeeDTO> getAllEmployeesNames() {
        return projectRestClient
                .get()
                .uri("http://localhost:8081/projects/all-employees")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }

    //checking is exist current employee in current project
    @Override
    public boolean isExistEmployeeInProject(String employeeName, long idPr) {
        return allProjectEmployees(idPr).stream()
                .map(employee -> employee.getFirstName() + " " +
                        employee.getMiddleName() + " " +
                        employee.getLastName())
                .anyMatch(fullName -> fullName.equals(employeeName));
    }

    //add current employee in current project
    @Override
    public void addProjectEmployee(ProjectEmployeeDTO projectEmployeeDTO, long idPr) {
        projectRestClient
                .post()
                .uri("http://localhost:8081/projects/add-employee/{idPr}", idPr)
                .body(projectEmployeeDTO)
                .retrieve();

    }

    //delete current employee from current project
    @Override
    public void removeEmployeeFromProject(long idEm, long idPr) {
        projectRestClient.delete()
                .uri("http://localhost:8081/projects/employee/{idEm}/{idPr}",idEm ,idPr)
                .retrieve()
                .toBodilessEntity();
    }
}

