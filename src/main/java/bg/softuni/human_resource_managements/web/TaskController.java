package bg.softuni.human_resource_managements.web;

import bg.softuni.human_resource_managements.model.dto.AddPositionDTO;
import bg.softuni.human_resource_managements.model.dto.AddProjectDTO;
import bg.softuni.human_resource_managements.model.dto.EmployeeNameDTO;
import bg.softuni.human_resource_managements.model.dto.TaskDTO;
import bg.softuni.human_resource_managements.service.EmployeeService;
import bg.softuni.human_resource_managements.service.TaskService;
import bg.softuni.human_resource_managements.service.impl.EmployeeServiceImpl;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TaskController {

    private final TaskService taskService;
    protected final EmployeeService employeeService;

    public TaskController(TaskService taskService, EmployeeService employeeService) {
        this.taskService = taskService;
        this.employeeService = employeeService;
    }

    @ModelAttribute("taskDTO")
    public TaskDTO emptyTaskDTO() {
        return new TaskDTO();
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
            @Valid TaskDTO taskDTO,
            BindingResult bindingResult,
            RedirectAttributes rAtt) {

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("taskDTO", taskDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.taskDTO", bindingResult);
            return "redirect:/add-task";
        }

        if(taskService.isExistTask(taskDTO.getName())){
            rAtt.addFlashAttribute("taskDTO", taskDTO);
            rAtt.addFlashAttribute("isExist", true);
            return "redirect:/add-task";
        }

        taskService.addTask(taskDTO);

        return "redirect:/tasks";
    }
}
