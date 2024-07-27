package bg.softuni.human_resource_managements.service.impl;

import bg.softuni.human_resource_managements.model.dto.*;
import bg.softuni.human_resource_managements.service.DepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final RestClient employeesRestClient;
    private final RestClient departmentsRestClient;

    private final ModelMapper mapper;

    public DepartmentServiceImpl(RestClient employeesRestClient, RestClient departmentsRestClient, ModelMapper mapper) {
        this.employeesRestClient = employeesRestClient;
        this.departmentsRestClient = departmentsRestClient;
        this.mapper = mapper;
    }

    //get all department names
    @Override
    public List<String> getAllDepartments() {
        return employeesRestClient
                .get()
                .uri("http://localhost:8081/employees/all-departments")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }

    //get all departments
    @Override
    public List<DepartmentDTO> getAllDepartmentsDTOS() {
        return departmentsRestClient
                .get()
                .uri("http://localhost:8081/departments")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }

    //check is exist department by name
    @Override
    public boolean isExistDepartment(String newDeaprtmentName) {
        List<DepartmentDTO> departmentDTOS = departmentsRestClient
                .get()
                .uri("http://localhost:8081/departments")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});

        return departmentDTOS.stream()
                .anyMatch(department -> department.getDepartmentName().equals(newDeaprtmentName));
    }

    //add new department
    @Override
    public void addDepartment(AddDepartmentDTO addDepartmentDTO) {
        departmentsRestClient
                .post()
                .uri("http://localhost:8081/departments")
                .body(addDepartmentDTO)
                .retrieve();
    }

    //get department by id
    @Override
    public DepartmentDTO getDepartmentDTOByID(long id) {
        return departmentsRestClient
                .get()
                .uri("http://localhost:8081/departments/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(DepartmentDTO.class);
    }

    //edit department
    @Override
    public void editDepartment(DepartmentDTO departmentDTO) {
        departmentsRestClient
                .post()
                .uri("http://localhost:8081/departments/edit")
                .body(departmentDTO)
                .retrieve();
    }

    //delete department
    @Override
    public void removeDepartment(long id) {
        departmentsRestClient.delete()
                .uri("http://localhost:8081/departments/" + id)
                .retrieve()
                .toBodilessEntity();
    }

    //get employees from current department
    @Override
    public List<EmployeeDTO> allDepartmentEmployees(long id) {
        return departmentsRestClient
                .get()
                .uri("http://localhost:8081/departments/employees/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }

    //check is exist current employee in current department
    @Override
    public boolean isExistEmployeeInDepartment(String employeeName, long idDep) {
        return allDepartmentEmployees(idDep).stream()
                .map(employee -> employee.getFirstName() + " " +
                        employee.getMiddleName() + " " +
                        employee.getLastName())
                .anyMatch(fullName -> fullName.equals(employeeName));
    }

    //add current employee in current department
    @Override
    public void addDepartmentEmployee(EmployeeNameDTO employeeNameDTO, long idDep) {
        departmentsRestClient
                .post()
                .uri("http://localhost:8081/departments/add-employee/{idDep}", idDep)
                .body(employeeNameDTO)
                .retrieve();
    }

    //delete current employee from current department
    @Override
    public void removeEmployeeFromDepartment(long idEm, long idDep) {
        departmentsRestClient.delete()
                .uri("http://localhost:8081/departments/employee/{idEm}/{idDep}",idEm ,idDep)
                .retrieve()
                .toBodilessEntity();
    }
}
