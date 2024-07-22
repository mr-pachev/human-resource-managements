package bg.softuni.human_resource_managements.service.impl;

import bg.softuni.human_resource_managements.model.dto.PositionDTO;
import bg.softuni.human_resource_managements.service.PositionService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {
    private final RestClient positionRestClient;

    public PositionServiceImpl(RestClient positionRestClient) {
        this.positionRestClient = positionRestClient;
    }

    @Override
    public List<String> getAllPositionNames() {
        return positionRestClient
                .get()
                .uri("http://localhost:8081/positions/all-positions")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }
    @Override
    public List<PositionDTO> getAllPositionsDTOS() {
        return positionRestClient
                .get()
                .uri("http://localhost:8081/positions")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }
}
