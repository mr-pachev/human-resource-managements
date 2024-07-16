package bg.softuni.human_resource_managements.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProjectController {
    @GetMapping("/projects")
    public String allProjects(Model model){
        model.addAttribute("departments", departmentService.getAllDepartmentsDTOS());

        return "projects";
    }

}
