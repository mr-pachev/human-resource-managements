package bg.softuni.human_resource_managements.service;

import bg.softuni.human_resource_managements.model.dto.ProjectDTO;
import bg.softuni.human_resource_managements.model.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    //get all tasks
    List<TaskDTO> getAllTasksDTOS();

    //checking is exist task by name
    boolean isExistTask(String taskName);

    //add new task
    void addTask(TaskDTO taskDTO);
}
