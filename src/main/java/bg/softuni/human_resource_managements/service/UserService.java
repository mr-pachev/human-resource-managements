package bg.softuni.human_resource_managements.service;

import bg.softuni.human_resource_managements.model.dto.AddUserDTO;
import bg.softuni.human_resource_managements.model.dto.EmployeeDTO;
import bg.softuni.human_resource_managements.model.dto.UserDTO;
import bg.softuni.human_resource_managements.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean addUser(AddUserDTO addUserDTO);
    boolean findUserByUsername(String username);
    List<UserDTO> getAllUsers();
    void removeUser(long id);
    UserDTO getUserDetails(long id);
    void edithUser(UserDTO userDTO);
}
