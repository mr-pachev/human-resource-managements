package bg.softuni.human_resource_managements.web;

import bg.softuni.human_resource_managements.model.dto.EmployeeDTO;
import bg.softuni.human_resource_managements.model.dto.ProjectDTO;
import bg.softuni.human_resource_managements.service.DepartmentService;
import bg.softuni.human_resource_managements.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProjectController {
    private final ProjectService projectService;
    private final DepartmentService departmentService;

    public ProjectController(ProjectService projectService, DepartmentService departmentService) {
        this.projectService = projectService;
        this.departmentService = departmentService;
    }

    @GetMapping("/projects")
    public String allProjects(Model model){
        model.addAttribute("projects", projectService.getAllProjectsDTOS());
        return "projects";
    }

    @PostMapping("/project-details/{id}")
    public String pullEdithDepartment(@PathVariable("id") Long id, Model model){
        ProjectDTO projectDTO = projectService.getProjectDTOByID(id);
        model.addAttribute(projectDTO);
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "project-details";
    }

    @GetMapping("/project-employees/{id}")
    public String allProjectEmployees(@PathVariable("id") Long id, Model model){
        model.addAttribute("projectEmployees", projectService.allProjectEmployees(id));
        model.addAttribute("projectId", id);
        return "project-employees";
    }

    @PostMapping("/project-employees/{id}")
    public String getAllProjectEmployees(@PathVariable("id") Long id, Model model){
        model.addAttribute("projectEmployees", projectService.allProjectEmployees(id));
        model.addAttribute("projectId", id);
        return "project-employees";
    }

    @PostMapping("/delete-project-employee/{idEm}/{idPr}")
    public String deleteDepartment(@PathVariable("idEm") Long idEm, @PathVariable("idPr") Long idPr) {
        projectService.removeEmployeeFromProject(idEm, idPr);
        return "redirect:/project-employees/" + idPr;
    }
}
