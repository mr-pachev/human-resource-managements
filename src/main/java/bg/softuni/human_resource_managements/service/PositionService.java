package bg.softuni.human_resource_managements.service;

import bg.softuni.human_resource_managements.model.dto.*;

import java.util.List;

public interface PositionService {
   //get all positions names
   List<String> getAllPositionNames();

   //get all positions
   List<PositionDTO> getAllPositionsDTOS();

   //checking is exist position by name
   boolean isExistPosition(String name);

   //add new position
   void addPosition(AddPositionDTO addPositionDTO);

   //get position by id
   PositionDTO getPositionDTOByID(long id);

   //edit position
   void editPosition(PositionDTO positionDTO);

   //delete position
   void removePosition(long id);

   //get all employees from current position
   List<EmployeeDTO> allPositionEmployees(long id);

   //checking is exist employee in current position
   boolean isExistEmployeeInPosition(String employeeName, long idPos);

   //add current employee in current position
   void addPositionEmployee(PositionEmployeesDTO positionEmployeesDTO, long idPos);

   //delete current employee from current position
   void removeEmployeeFromPosition(long idEm, long idPos);
}
