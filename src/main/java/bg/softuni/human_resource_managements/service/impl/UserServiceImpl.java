package bg.softuni.human_resource_managements.service.impl;

import bg.softuni.human_resource_managements.model.dto.AddUserDTO;
import bg.softuni.human_resource_managements.model.entity.Employee;
import bg.softuni.human_resource_managements.model.entity.Role;
import bg.softuni.human_resource_managements.model.entity.User;
import bg.softuni.human_resource_managements.model.enums.RoleName;
import bg.softuni.human_resource_managements.repository.EmployeeRepository;
import bg.softuni.human_resource_managements.repository.RoleRepository;
import bg.softuni.human_resource_managements.repository.UserRepository;
import bg.softuni.human_resource_managements.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(ModelMapper mapper, PasswordEncoder passwordEncoder, EmployeeRepository employeeRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public boolean addUser(AddUserDTO addUserDTO) {
        User user = mapper.map(addUserDTO, User.class);
        Optional<User> isExistUser = userRepository.findByUsername(addUserDTO.getUsername());

        Optional<Employee> currentEmployee = employeeRepository.findAllByIdentificationNumber(addUserDTO.getIdentificationNumber());

        if (isExistUser.isPresent() || currentEmployee.isEmpty()) {
            return false;
        }

        user.setEmployee(currentEmployee.get());
        user.setRole(roleRepository.findByRoleName(RoleName.valueOf(addUserDTO.getRole())));
        user.setPassword(passwordEncoder.encode(addUserDTO.getPassword()));

        userRepository.save(user);

        return true;
    }
}
