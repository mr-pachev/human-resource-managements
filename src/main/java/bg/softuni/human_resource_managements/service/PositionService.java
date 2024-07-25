package bg.softuni.human_resource_managements.service;

import bg.softuni.human_resource_managements.model.dto.*;

import java.util.List;

public interface PositionService {
   //get all
   List<String> getAllPositionNames();
   List<PositionDTO> getAllPositionsDTOS();
   List<EmployeeDTO> allPositionEmployees(long id);

   //position
   void addPosition(AddPositionDTO addPositionDTO);
   PositionDTO getPositionDTOByID(long id);
   boolean isExistPosition(String name);
   void editPosition(PositionDTO positionDTO);
   void removePosition(long id);

   //position employees
   void addPositionEmployee(PositionEmployeesDTO positionEmployeesDTO, long idPos);
   boolean isExistEmployeeInPosition(String employeeName, long idPos);
   void removeEmployeeFromPosition(long idEm, long idPos);
}
