package bg.softuni.human_resource_managements.service.impl;

import bg.softuni.human_resource_managements.model.dto.DepartmentDTO;
import bg.softuni.human_resource_managements.model.dto.ProjectDTO;
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
    public List<ProjectDTO> getAllProjectsDTOS() {
        return projectRestClient
                .get()
                .uri("http://localhost:8081/projects")
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
}
