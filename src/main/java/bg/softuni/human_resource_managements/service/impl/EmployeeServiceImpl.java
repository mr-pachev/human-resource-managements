package bg.softuni.human_resource_managements.service.impl;

import bg.softuni.human_resource_managements.model.dto.AddEmployeeDTO;
import bg.softuni.human_resource_managements.repository.EmployeeRepository;
import bg.softuni.human_resource_managements.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(ModelMapper mapper, PasswordEncoder passwordEncoder, EmployeeRepository employeeRepository) {
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void addEmployee(AddEmployeeDTO addEmployeeDTO) {

    }
}
