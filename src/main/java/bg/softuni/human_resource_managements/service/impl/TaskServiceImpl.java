package bg.softuni.human_resource_managements.service.impl;

import bg.softuni.human_resource_managements.model.dto.AddPositionDTO;
import bg.softuni.human_resource_managements.model.dto.AddTaskDTO;
import bg.softuni.human_resource_managements.model.dto.PositionDTO;
import bg.softuni.human_resource_managements.model.dto.TaskDTO;
import bg.softuni.human_resource_managements.service.TaskService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final RestClient taskRestClient;

    public TaskServiceImpl(RestClient taskRestClient) {
        this.taskRestClient = taskRestClient;
    }

    //get all tasks
    @Override
    public List<TaskDTO> getAllTasksDTOS() {
        return taskRestClient
                .get()
                .uri("http://localhost:8081/tasks")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }

    //checking is exist task by name
    @Override
    public boolean isExistTask(String taskName) {
        List<TaskDTO> taskDTOS = taskRestClient
                .get()
                .uri("http://localhost:8081/tasks")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});

        return taskDTOS.stream()
                .anyMatch(task -> task.getName().equals(taskName));
    }

    //add new task
    @Override
    public void addTask(AddTaskDTO addTaskDTO) {
        taskRestClient
                .post()
                .uri("http://localhost:8081/tasks")
                .body(addTaskDTO)
                .retrieve();
    }

    //get position by id
    @Override
    public TaskDTO getTaskDTOByID(long id) {
        return taskRestClient
                .get()
                .uri("http://localhost:8081/tasks/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(TaskDTO.class);
    }

    //edit task
    @Override
    public void editTask(TaskDTO taskDTO) {
        taskRestClient
                .post()
                .uri("http://localhost:8081/tasks/edit")
                .body(taskDTO)
                .retrieve();
    }


}
