package bg.softuni.human_resource_managements.service.impl;

import bg.softuni.human_resource_managements.model.dto.AddUserDTO;
import bg.softuni.human_resource_managements.model.dto.UserDTO;
import bg.softuni.human_resource_managements.model.entity.Role;
import bg.softuni.human_resource_managements.model.entity.User;
import bg.softuni.human_resource_managements.model.enums.RoleName;
import bg.softuni.human_resource_managements.repository.RoleRepository;
import bg.softuni.human_resource_managements.repository.UserRepository;
import bg.softuni.human_resource_managements.service.UserService;
import bg.softuni.human_resource_managements.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(ModelMapper mapper, PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    //get all users
    @Override
    public List<UserDTO> getAllUsers() {
        List<UserDTO> users = new ArrayList<>();

        for (User user : userRepository.findAll()) {
            users.add(mapToDTO(user));
        }
        return users;
    }

    //checking is exist user by username
    @Override
    public boolean isExistUser(String username) {
        return  getAllUsers()
                .stream()
                .anyMatch(userDTO -> userDTO.getUsername().equals(username));
    }

    //add new user
    @Override
    public void addUser(AddUserDTO addUserDTO) {
        User user = mapper.map(addUserDTO, User.class);

        if(userRepository.count() == 0){
            user.setRole(roleRepository.findByRoleName(RoleName.ADMIN));
        }else {
            user.setRole(roleRepository.findByRoleName(RoleName.USER));
        }

        user.setPassword(passwordEncoder.encode(addUserDTO.getPassword()));

        userRepository.save(user);
    }

    //get user by id
    @Override
    public UserDTO getUserDetails(long id) {
        Optional<User> user = userRepository.findById(id);

        return mapToDTO(user.get());
    }

    //edit user
    @Override
    public void editUser(UserDTO userDTO) {
        User user = userRepository.findByIdentificationNumber(userDTO.getIdentificationNumber()).orElseThrow(ObjectNotFoundException::new);

        user.setUsername(userDTO.getUsername());
        user.setRole(roleRepository.findByRoleName(RoleName.valueOf(userDTO.getRole())));

        userRepository.save(user);
    }

    //delete user
    @Override
    public void removeUser(long id) {
        userRepository.deleteById(id);
    }

    UserDTO mapToDTO(User user){
        UserDTO userDTO = mapper.map(user, UserDTO.class);
        userDTO.setRole(user.getRole().getRoleName().name());
        return userDTO;
    }
}
