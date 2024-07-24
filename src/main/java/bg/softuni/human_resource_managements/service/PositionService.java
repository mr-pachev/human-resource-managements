package bg.softuni.human_resource_managements.service;

import bg.softuni.human_resource_managements.model.dto.*;

import java.util.List;

public interface PositionService {
   List<String> getAllPositionNames();
   List<PositionDTO> getAllPositionsDTOS();
   void addPosition(AddPositionDTO addPositionDTO);
   boolean isExistPosition(String name);
   boolean isExistEmployeeInPosition(String employeeName, long idPos);
   PositionDTO getPositionDTOByID(long id);
   List<EmployeeDTO> allPositionEmployees(long id);
   void addPositionEmployee(PositionEmployeesDTO positionEmployeesDTO, long idPos);
   void removeEmployeeFromPosition(long idEm, long idPos);
   void removePosition(long id);
}
