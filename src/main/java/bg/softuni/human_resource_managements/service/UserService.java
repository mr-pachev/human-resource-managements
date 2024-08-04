package bg.softuni.human_resource_managements.service;

import bg.softuni.human_resource_managements.model.dto.AddUserDTO;
import bg.softuni.human_resource_managements.model.dto.EmployeeDTO;
import bg.softuni.human_resource_managements.model.dto.UserDTO;
import bg.softuni.human_resource_managements.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    //get all users
    List<UserDTO> getAllUsers();

    //checking is exist user by username
    boolean isExistUser(String username);

    //add new user
    void addUser(AddUserDTO addUserDTO);

    //get user
    UserDTO getUserDetails(long id);


    void editUser(UserDTO userDTO);
    void removeUser(long id);
}
