package bg.softuni.human_resource_managements.web;

import bg.softuni.human_resource_managements.model.dto.AddProjectDTO;
import bg.softuni.human_resource_managements.model.dto.ProjectDTO;
import bg.softuni.human_resource_managements.model.dto.ProjectEmployeeDTO;
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
    public AddProjectDTO emptyAddProjectDTO() {
        return new AddProjectDTO();
    }

    @ModelAttribute("projectEmployeeDTO")
    public ProjectEmployeeDTO emptyProjectEmployeeDTO() {
        return new ProjectEmployeeDTO();
    }

    //view all projects
    @GetMapping("/projects")
    public String viewAllProjects(Model model) {
        model.addAttribute("projects", projectService.getAllProjectsDTOS());

        return "projects";
    }

    //add new project
    @GetMapping("/add-project")
    public String viewAddProjectForm(Model model) {
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "add-project";
    }

    @PostMapping("/add-project")
    public String creatProject(
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

    //edit current project
    @PostMapping("/project-details/{id}")
    public String referenceToEditProjectForm(@PathVariable("id") Long id) {

        return "redirect:/project-details/" + id;
    }

    @GetMapping("/project-details/{id}")
    public String fillEditProjectForm(@PathVariable("id") Long id, Model model) {
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

    //delete project by id
    @PostMapping("/delete-project/{id}")
    public String deleteProject(@PathVariable("id") Long id) {
        projectService.removeProject(id);

        return "redirect:/projects";
    }

    //project-employees
    @PostMapping("/project-employees/{id}")
    public String fillAndViewAllProjectEmployees(@PathVariable("id") Long id, Model model) {
        model.addAttribute("projectId", id);

        return "redirect:/project-employees/" + id;
    }

    @GetMapping("/project-employees/{id}")
    public String viewProjectEmployees(@PathVariable("id") Long id, Model model) {
        model.addAttribute("projectEmployees", projectService.allProjectEmployees(id));
        model.addAttribute("projectId", id);
        model.addAttribute("allEmployees", projectService.getAllEmployees());

        return "project-employees";
    }

    //add another employee in current project
    @PostMapping("/project-employee/{idPr}")
    public String addEmployee(@PathVariable("idPr") Long idPr,
                              ProjectEmployeeDTO projectEmployeeDTO,
                              RedirectAttributes rAtt){

        String employeeName = projectEmployeeDTO.getFullName();

        boolean isExist = projectService.isExistEmployeeInProject(employeeName, idPr);
        if(isExist){
            rAtt.addFlashAttribute("projectEmployees", projectService.allProjectEmployees(idPr));
            rAtt.addFlashAttribute("isExist", true);
            return "redirect:/project-employees/" + idPr;
        }

        projectService.addProjectEmployee(projectEmployeeDTO, idPr);

        return "redirect:/project-employees/" + idPr;
    }

    //delete current employee from current project
    @PostMapping("/delete-project-employee/{idEm}/{idPr}")
    public String deleteProjectEmployee(@PathVariable("idEm") Long idEm, @PathVariable("idPr") Long idPr) {
        projectService.removeEmployeeFromProject(idEm, idPr);
        return "redirect:/project-employees/" + idPr;
    }
}
