package bg.softuni.human_resource_managements.service.impl;

import bg.softuni.human_resource_managements.service.EducationService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class EducationServiceImpl implements EducationService {
    private final RestClient employeesRestClient;

    public EducationServiceImpl(RestClient employeesRestClient) {
        this.employeesRestClient = employeesRestClient;
    }

    //get all educations names
    @Override
    public List<String> getAllEducations() {
        return employeesRestClient
                .get()
                .uri("http://localhost:8081/employees/all-educations")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }
}
