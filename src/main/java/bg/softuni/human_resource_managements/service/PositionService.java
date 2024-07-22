package bg.softuni.human_resource_managements.service;

import bg.softuni.human_resource_managements.model.dto.DepartmentDTO;
import bg.softuni.human_resource_managements.model.dto.PositionDTO;

import java.util.List;

public interface PositionService {
   List<String> getAllPositionNames();

   List<PositionDTO> getAllPositionsDTOS();
}
