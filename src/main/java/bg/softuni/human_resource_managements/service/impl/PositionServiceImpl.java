package bg.softuni.human_resource_managements.service.impl;

import bg.softuni.human_resource_managements.model.dto.PositionNameDTO;
import bg.softuni.human_resource_managements.service.PositionService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {
    private final RestClient employeesRestClient;

    public PositionServiceImpl(RestClient employeesRestClient) {
        this.employeesRestClient = employeesRestClient;
    }


    @Override
    public List<String> getAllPositionNames() {
        return employeesRestClient
                .get()
                .uri("http://localhost:8081/employees")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }
}
