package bg.softuni.human_resource_managements.service;

import bg.softuni.human_resource_managements.model.dto.DepartmentDTO;
import bg.softuni.human_resource_managements.model.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {
    List<ProjectDTO> getAllProjectsDTOS();
    ProjectDTO getProjectDTOByID(long id);
}
