package bg.softuni.human_resource_managements.service;

import bg.softuni.human_resource_managements.model.dto.AddUserDTO;
import bg.softuni.human_resource_managements.model.dto.UserDTO;

import java.util.List;

public interface UserService {
    boolean addUser(AddUserDTO addUserDTO);
    boolean findUserByUsername(String username);
    List<UserDTO> getAllUsers();
}
