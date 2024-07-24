package bg.softuni.human_resource_managements.service.impl;

import bg.softuni.human_resource_managements.model.dto.AddPositionDTO;
import bg.softuni.human_resource_managements.model.dto.EmployeeDTO;
import bg.softuni.human_resource_managements.model.dto.PositionDTO;
import bg.softuni.human_resource_managements.model.dto.ProjectDTO;
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

    @Override
    public void addPosition(AddPositionDTO addPositionDTO) {
        positionRestClient
                .post()
                .uri("http://localhost:8081/positions")
                .body(addPositionDTO)
                .retrieve();
    }

    @Override
    public boolean isExistPosition(String namePosition) {
        List<PositionDTO> positionDTOS = positionRestClient
                .get()
                .uri("http://localhost:8081/positions")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});

        return positionDTOS.stream()
                .anyMatch(position -> position.getPositionName().equals(namePosition));
    }

    @Override
    public PositionDTO getPositionDTOByID(long id) {
        return positionRestClient
                .get()
                .uri("http://localhost:8081/positions/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(PositionDTO.class);
    }

    @Override
    public List<EmployeeDTO> allPositionEmployees(long id) {
        return positionRestClient
                .get()
                .uri("http://localhost:8081/positions/all-employees/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }
}
