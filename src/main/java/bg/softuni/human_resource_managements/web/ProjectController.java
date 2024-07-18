package bg.softuni.human_resource_managements.web;

import bg.softuni.human_resource_managements.model.dto.AddProjectDTO;
import bg.softuni.human_resource_managements.model.dto.ProjectDTO;
import bg.softuni.human_resource_managements.service.DepartmentService;
import bg.softuni.human_resource_managements.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProjectController {
    private final ProjectService projectService;
    private final DepartmentService departmentService;

    public ProjectController(ProjectService projectService, DepartmentService departmentService) {
        this.projectService = projectService;
        this.departmentService = departmentService;
    }

    @ModelAttribute("addProjectDTO")
    public AddProjectDTO createAddProjectDTO() {
        return new AddProjectDTO();
    }

    @GetMapping("/add-project")
    public String registrationView(Model model) {
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "add-project";
    }

    @PostMapping("/add-project")
    public String addProject(
            @Valid AddProjectDTO addProjectDTO,
            BindingResult bindingResult,
            RedirectAttributes rAtt) {

        if (bindingResult.hasErrors() || !projectService.addProject(addProjectDTO)) {
            rAtt.addFlashAttribute("addProjectDTO", addProjectDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.addProjectDTO", bindingResult);
            rAtt.addFlashAttribute("isExist", true);
            return "redirect:/add-project";
        }

        projectService.addProject(addProjectDTO);

        return "redirect:/projects";
    }

    @GetMapping("/projects")
    public String allProjects(Model model){
        model.addAttribute("projects", projectService.getAllProjectsDTOS());
        return "projects";
    }

    @PostMapping("/project-details/{id}")
    public String pullEdithProject(@PathVariable("id") Long id, Model model){
        ProjectDTO projectDTO = projectService.getProjectDTOByID(id);
        model.addAttribute(projectDTO);
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "project-details";
    }

    @PostMapping("/project-details")
    public String editProject(@RequestParam("id") Long id,
                                @Valid ProjectDTO projectDTO,
                              BindingResult bindingResult,
                              RedirectAttributes rAtt) {

        projectDTO.setId(id);

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("projectDTO", projectDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.projectDTO", bindingResult);
            rAtt.addFlashAttribute("departments", departmentService.getAllDepartments());
            return "redirect:/project-details/" + projectDTO.getId();
        }

        projectService.editProject(projectDTO);
        return "redirect:/projects";
    }

    @GetMapping("/project-details/{id}")
    public String showEditProjectForm(@PathVariable("id") Long id, Model model) {
        if (!model.containsAttribute("projectDTO")) {
            ProjectDTO projectDTO = projectService.getProjectDTOByID(id);
            model.addAttribute("projectDTO", projectDTO);
        }
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "project-details";
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

    @PostMapping("/delete-project/{id}")
    public String deleteDepartment(@PathVariable("id") Long id) {
        projectService.removeProject(id);

        return "redirect:/projects";
    }
}
