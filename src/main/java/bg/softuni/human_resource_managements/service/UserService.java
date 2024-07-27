package bg.softuni.human_resource_managements.service;

import bg.softuni.human_resource_managements.model.dto.AddUserDTO;
import bg.softuni.human_resource_managements.model.dto.EmployeeDTO;
import bg.softuni.human_resource_managements.model.dto.UserDTO;
import bg.softuni.human_resource_managements.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserDetails(long id);
    boolean addUser(AddUserDTO addUserDTO);
    void editUser(UserDTO userDTO);
    void removeUser(long id);
}
