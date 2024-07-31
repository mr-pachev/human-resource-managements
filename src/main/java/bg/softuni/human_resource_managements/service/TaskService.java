package bg.softuni.human_resource_managements.service;

import bg.softuni.human_resource_managements.model.dto.AddTaskDTO;
import bg.softuni.human_resource_managements.model.dto.PositionDTO;
import bg.softuni.human_resource_managements.model.dto.ProjectDTO;
import bg.softuni.human_resource_managements.model.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    //get all tasks
    List<TaskDTO> getAllTasksDTOS();

    //checking is exist task by name
    boolean isExistTask(String taskName);

    //add new task
    void addTask(AddTaskDTO addTaskDTO);

    //get task by id
    TaskDTO getTaskDTOByID(long id);

    //edit task
    void editTask(TaskDTO taskDTO);
}
