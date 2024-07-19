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

    @Override
    public List<ProjectDTO> getAllProjectsDTOS() {
        return projectRestClient
                .get()
                .uri("http://localhost:8081/projects")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }

    @Override
    public List<ProjectEmployeeDTO> getAllEmployees() {
        return projectRestClient
                .get()
                .uri("http://localhost:8081/projects/all-employees")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }

    @Override
    public ProjectDTO getProjectDTOByID(long id) {
        return projectRestClient
                .get()
                .uri("http://localhost:8081/projects/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(ProjectDTO.class);
    }

    @Override
    public void removeEmployeeFromProject(long idEm, long idPr) {
        projectRestClient.delete()
                .uri("http://localhost:8081/projects/employee{idEm}/{idPr}",idEm ,idPr)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public void removeProject(long id) {
        projectRestClient.delete()
                .uri("http://localhost:8081/projects/{id}",id)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public List<EmployeeDTO> allProjectEmployees(long id) {
        return projectRestClient
                .get()
                .uri("http://localhost:8081/projects/employees/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }

    @Override
    public void editProject(ProjectDTO projectDTO) {

        projectRestClient
                .post()
                .uri("http://localhost:8081/projects/edit")
                .body(projectDTO)
                .retrieve();
    }

    @Override
    public void addProjectEmployee(String name) {

    }

    @Override
    public boolean isExistEmployeeInProject(String employeeName, long idPr) {
        List<EmployeeDTO> allProjectEmployees = allProjectEmployees(idPr);

        for (EmployeeDTO employee : allProjectEmployees) {
            String fullName = employee.getFirstName() + " " +
                            employee.getMiddleName() + " " +
                            employee.getLastName();
            if(fullName.equals(employeeName)){
                return true;
            }
        }

        return false;
    }
}
