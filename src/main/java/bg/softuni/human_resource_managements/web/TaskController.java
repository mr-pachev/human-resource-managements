package bg.softuni.human_resource_managements.web;

import bg.softuni.human_resource_managements.model.dto.AddTaskDTO;
import bg.softuni.human_resource_managements.model.dto.DepartmentDTO;
import bg.softuni.human_resource_managements.model.dto.EmployeeNameDTO;
import bg.softuni.human_resource_managements.model.dto.TaskDTO;
import bg.softuni.human_resource_managements.service.EmployeeService;
import bg.softuni.human_resource_managements.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TaskController {

    private final TaskService taskService;
    protected final EmployeeService employeeService;

    public TaskController(TaskService taskService, EmployeeService employeeService) {
        this.taskService = taskService;
        this.employeeService = employeeService;
    }

    @ModelAttribute("addTaskDTO")
    public AddTaskDTO emptyAddTaskDTO() {
        return new AddTaskDTO();
    }

    @ModelAttribute("employeeNameDTO")
    public EmployeeNameDTO emptyEmployeeNameDTO() {
        return new EmployeeNameDTO();
    }

    //view all tasks
    @GetMapping("/tasks")
    public String viewAllTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllTasksDTOS());

        return "tasks";
    }

    //create new task
    @GetMapping("/add-task")
    public String viewAddTaskForm(Model model) {
        model.addAttribute("allEmployees", employeeService.getAllEmployeesNames());

        return "add-task";
    }

    @PostMapping("/add-task")
    public String creatTask(
            @Valid AddTaskDTO addTaskDTO,
            BindingResult bindingResult,
            RedirectAttributes rAtt) {

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("addTaskDTO", addTaskDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.addTaskDTO", bindingResult);
            return "redirect:/add-task";
        }

        if (taskService.isExistTask(addTaskDTO.getName())) {
            rAtt.addFlashAttribute("addTaskDTO", addTaskDTO);
            rAtt.addFlashAttribute("isExist", true);
            return "redirect:/add-task";
        }

        taskService.addTask(addTaskDTO);

        return "redirect:/tasks";
    }

    //edit current task
    @PostMapping("/task-details/{id}")
    public String referenceToEditTaskForm(@PathVariable("id") Long id) {

        return "redirect:/task-details/" + id;
    }

    @GetMapping("/task-details/{id}")
    public String fillEditTaskForm(@PathVariable("id") Long id, Model model) {
        TaskDTO taskDTO = taskService.getTaskDTOByID(id);
        model.addAttribute(taskDTO);
        model.addAttribute("allEmployees", employeeService.getAllEmployeesNames());

        return "task-details";
    }

    @PostMapping("/task-details")
    public String editTask(@RequestParam("id") Long id,
                           @Valid TaskDTO taskDTO,
                           BindingResult bindingResult,
                           RedirectAttributes rAtt) {

        taskDTO.setId(id);

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("taskDTO", taskDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.taskDTO", bindingResult);

            return "redirect:/task-details/" + taskDTO.getId();
        }

        taskService.editTask(taskDTO);
        return "redirect:/tasks";
    }

    //delete task by id
    @PostMapping("/delete-task/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        TaskDTO taskDTO = taskService.getTaskDTOByID(id);

        taskService.removeTask(id);

        return "redirect:/tasks";
    }
}
