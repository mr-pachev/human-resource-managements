package bg.softuni.human_resource_managements.service.impl;

import bg.softuni.human_resource_managements.model.dto.AddDepartmentDTO;
import bg.softuni.human_resource_managements.model.dto.DepartmentDTO;
import bg.softuni.human_resource_managements.model.dto.EmployeeDTO;
import bg.softuni.human_resource_managements.model.dto.ProjectDTO;
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

    @Override
    public List<String> getAllDepartments() {
        return employeesRestClient
                .get()
                .uri("http://localhost:8081/employees/all-departments")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }

    @Override
    public List<DepartmentDTO> getAllDepartmentsDTOS() {
        return departmentsRestClient
                .get()
                .uri("http://localhost:8081/departments")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }

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

    @Override
    public void addDepartment(AddDepartmentDTO addDepartmentDTO) {
        departmentsRestClient
                .post()
                .uri("http://localhost:8081/departments")
                .body(addDepartmentDTO)
                .retrieve();
    }

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

    @Override
    public void removeDepartment(long id) {
        departmentsRestClient.delete()
                .uri("http://localhost:8081/departments/" + id)
                .retrieve()
                .toBodilessEntity();
    }
}
