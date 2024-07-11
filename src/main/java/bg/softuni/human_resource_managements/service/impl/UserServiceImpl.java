package bg.softuni.human_resource_managements.service.impl;

import bg.softuni.human_resource_managements.model.dto.AddUserDTO;
import bg.softuni.human_resource_managements.model.dto.UserDTO;
import bg.softuni.human_resource_managements.model.entity.User;
import bg.softuni.human_resource_managements.model.enums.RoleName;
import bg.softuni.human_resource_managements.repository.RoleRepository;
import bg.softuni.human_resource_managements.repository.UserRepository;
import bg.softuni.human_resource_managements.service.EmployeeService;
import bg.softuni.human_resource_managements.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmployeeService employeeService;
    private final RoleRepository roleRepository;
    private final RestClient usersRestClient;

    public UserServiceImpl(ModelMapper mapper, PasswordEncoder passwordEncoder, UserRepository userRepository, EmployeeService employeeService, RoleRepository roleRepository, RestClient usersRestClient) {
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.employeeService = employeeService;
        this.roleRepository = roleRepository;
        this.usersRestClient = usersRestClient;
    }

    @Override
    public boolean addUser(AddUserDTO addUserDTO) {
        User user = mapper.map(addUserDTO, User.class);
        List<UserDTO> allUsers = getAllUsers();

        boolean isExistUser = allUsers
                .stream()
                .anyMatch(userDTO -> userDTO.getUsername().equals(addUserDTO.getUsername()));

        boolean isExistEmployee = employeeService.isExistEmployee(addUserDTO.getIdentificationNumber());

        if (isExistUser || !isExistEmployee) {
            return false;
        }

        user.setRole(roleRepository.findByRoleName(RoleName.valueOf(addUserDTO.getRole())));
        user.setPassword(passwordEncoder.encode(addUserDTO.getPassword()));

        userRepository.save(user);
        return true;
    }

    @Override
    public boolean findUserByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserDTO> users = new ArrayList<>();

        for (User user : userRepository.findAll()) {
            users.add(map(user));
        }

        return users;
    }

    @Override
    public void removeUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO getUserDetails(long id) {
       Optional<User> user = userRepository.findById(id);

        return map(user.get());
    }

    @Override
    public void edithUser(UserDTO userDTO) {
        User user = reMapUser(userDTO);

        userRepository.save(user);
    }

    public UserDTO map(User user){
        UserDTO userDTO = mapper.map(user, UserDTO.class);
        userDTO.setRole(user.getRole().getRoleName().name());
        return userDTO;
    }

    public User reMapUser(UserDTO userDTO){
        User user = userRepository.findByUsername(userDTO.getUsername()).get();

        user.setUsername(userDTO.getUsername());
        user.setRole(roleRepository.findByRoleName(RoleName.valueOf(userDTO.getRole())));

        return user;
    }
}
