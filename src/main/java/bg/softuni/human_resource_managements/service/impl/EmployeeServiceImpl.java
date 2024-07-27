package bg.softuni.human_resource_managements.service.impl;

import bg.softuni.human_resource_managements.model.dto.AddEmployeeDTO;
import bg.softuni.human_resource_managements.model.dto.AddUserDTO;
import bg.softuni.human_resource_managements.model.dto.EmployeeDTO;
import bg.softuni.human_resource_managements.model.dto.EmployeeNameDTO;
import bg.softuni.human_resource_managements.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final ModelMapper mapper;
    private final RestClient employeesRestClient;

    public EmployeeServiceImpl(ModelMapper mapper, RestClient employeesRestClient) {
        this.mapper = mapper;
        this.employeesRestClient = employeesRestClient;
    }

    //get all employees names
    @Override
    public List<EmployeeNameDTO> getAllEmployeesNames() {
        return employeesRestClient
                .get()
                .uri("http://localhost:8081/employees/all-employees")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }

    //get all employees
    @Override
    public List<EmployeeDTO> getAllEmployees() {

        return employeesRestClient
                .get()
                .uri("http://localhost:8081/employees")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }

    //checking is exist employee by full name
    @Override
    public boolean isExistEmployee(String managerFullName) {
        List<EmployeeDTO> employeeDTOS = employeesRestClient
                .get()
                .uri("http://localhost:8081/employees")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});

        return employeeDTOS.stream()
                .anyMatch(employee -> {
                    String managerName = employee.getFirstName() + " " +
                            employee.getMiddleName() + " " +
                            employee.getLastName();
                    return managerName.equals(managerFullName);
                });
    }

    //checking is exist employee by identification number
    @Override
    public boolean isExistEmployeeByIN(String identificationNumber) {
        List<EmployeeDTO> employeeDTOS = employeesRestClient
                .get()
                .uri("http://localhost:8081/employees")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});

        return employeeDTOS.stream()
                .anyMatch(employee -> {
                    return identificationNumber.equals(employee.getIdentificationNumber());
                });
    }

    //add employee
    @Override
    public void addEmployee(AddEmployeeDTO addEmployeeDTO) {
        employeesRestClient
                .post()
                .uri("http://localhost:8081/employees")
                .body(addEmployeeDTO)
                .retrieve();
    }

    //get employee by id
    @Override
    public EmployeeDTO getEmployeeByID(long id) {
        return employeesRestClient
                .get()
                .uri("http://localhost:8081/employees/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(EmployeeDTO.class);
    }

    //edit employee
    @Override
    public void editEmployee(EmployeeDTO employeeDTO) {
        AddEmployeeDTO addEmployeeDTO = mapper.map(employeeDTO, AddEmployeeDTO.class);
        employeesRestClient
                .post()
                .uri("http://localhost:8081/employees/edit")
                .body(addEmployeeDTO)
                .retrieve();
    }

    //delete employee
    @Override
    public void removeEmployee(long id) {
        employeesRestClient.delete()
                .uri("http://localhost:8081/employees/" + id)
                .retrieve()
                .toBodilessEntity();
    }
}
