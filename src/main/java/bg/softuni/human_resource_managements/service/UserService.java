package bg.softuni.human_resource_managements.service;

import bg.softuni.human_resource_managements.model.dto.AddUserDTO;

public interface UserService {
    boolean addUser(AddUserDTO addUserDTO);

    boolean findUserByUsername(String username);
}
