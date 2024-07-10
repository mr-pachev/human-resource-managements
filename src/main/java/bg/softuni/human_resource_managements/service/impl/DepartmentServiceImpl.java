package bg.softuni.human_resource_managements.service.impl;

import bg.softuni.human_resource_managements.service.DepartmentService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final RestClient employeesRestClient;

    public DepartmentServiceImpl(RestClient employeesRestClient) {
        this.employeesRestClient = employeesRestClient;
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
}
