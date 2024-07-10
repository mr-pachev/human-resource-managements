package bg.softuni.human_resource_managements.service.impl;

import bg.softuni.human_resource_managements.model.dto.AddEmployeeDTO;
import bg.softuni.human_resource_managements.model.dto.EmployeeDTO;
import bg.softuni.human_resource_managements.model.entity.Employee;
import bg.softuni.human_resource_managements.model.enums.DepartmentName;
import bg.softuni.human_resource_managements.model.enums.EducationName;
import bg.softuni.human_resource_managements.model.enums.PositionName;
import bg.softuni.human_resource_managements.repository.DepartmentRepository;
import bg.softuni.human_resource_managements.repository.EducationRepository;
import bg.softuni.human_resource_managements.repository.EmployeeRepository;
import bg.softuni.human_resource_managements.repository.PositionRepository;
import bg.softuni.human_resource_managements.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final ModelMapper mapper;
    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final DepartmentRepository departmentRepository;
    private final EducationRepository educationRepository;
    private final RestClient employeesRestClient;

    public EmployeeServiceImpl(ModelMapper mapper, EmployeeRepository employeeRepository, PositionRepository positionRepository, DepartmentRepository departmentRepository, EducationRepository educationRepository, RestClient employeesRestClient) {
        this.mapper = mapper;
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
        this.departmentRepository = departmentRepository;
        this.educationRepository = educationRepository;
        this.employeesRestClient = employeesRestClient;
    }

    @Override
    public boolean addEmployee(AddEmployeeDTO addEmployeeDTO) {

       if(isExistEmployee(addEmployeeDTO.getIdentificationNumber())){
           return false;
       }

        employeesRestClient
                .post()
                .uri("http://localhost:8081/employees")
                .body(addEmployeeDTO)
                .retrieve();

        return true;
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {

        return employeesRestClient
                .get()
                .uri("http://localhost:8081/employees")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }

    @Override
    public void removeEmployee(long id) {
        employeesRestClient.delete()
                .uri("http://localhost:8081/employees/" + id)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public EmployeeDTO getEmployeeByID(long id) {
        return employeesRestClient
                .get()
                .uri("http://localhost:8081/employees/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(EmployeeDTO.class);
    }

    @Override
    public boolean isExistEmployee(String identificationNumber) {
        List<EmployeeDTO> employeeDTOS = employeesRestClient
                .get()
                .uri("http://localhost:8081/employees")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});

        return employeeDTOS.stream()
                .anyMatch(employee -> employee.getIdentificationNumber().equals(identificationNumber));
    }

    @Override
    public void edithEmployee(EmployeeDTO employeeDTO) {
        employeesRestClient
                .post()
                .uri("http://localhost:8081/employees/edith")
                .body(employeeDTO)
                .retrieve();
    }
}
