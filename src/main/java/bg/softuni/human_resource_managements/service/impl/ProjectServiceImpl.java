package bg.softuni.human_resource_managements.service.impl;

import bg.softuni.human_resource_managements.model.dto.ProjectDTO;
import bg.softuni.human_resource_managements.service.ProjectService;
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


        return null;
    }
}
