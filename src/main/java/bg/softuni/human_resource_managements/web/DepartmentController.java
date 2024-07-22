package bg.softuni.human_resource_managements.web;

import bg.softuni.human_resource_managements.model.dto.AddDepartmentDTO;
import bg.softuni.human_resource_managements.model.dto.AddProjectDTO;
import bg.softuni.human_resource_managements.model.dto.DepartmentDTO;
import bg.softuni.human_resource_managements.service.DepartmentService;
import bg.softuni.human_resource_managements.service.EmployeeService;
import bg.softuni.human_resource_managements.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DepartmentController {

    private final DepartmentService departmentService;
    private final EmployeeService employeeService;
    private final ProjectService projectService;

    public DepartmentController(DepartmentService departmentService, EmployeeService employeeService, ProjectService projectService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    @ModelAttribute("addDepartmentDTO")
    public AddDepartmentDTO emptyAddDepartmentDTO() {
        return new AddDepartmentDTO();
    }


    @GetMapping("/departments")
    public String viewAllDepartments(Model model){
        model.addAttribute("departments", departmentService.getAllDepartmentsDTOS());

        return "departments";
    }

    //edith current department
    @PostMapping("/department-details/{id}")
    public String fillEdithDepartmentForm(@PathVariable("id") Long id){

        return "redirect:/department-details/" + id;
    }

    @GetMapping("/department-details/{id}")
    public String viewDepartmentDetailsForm(@PathVariable("id") Long id, Model model) {
        DepartmentDTO departmentDTO = departmentService.getDepartmentDTOByID(id);
        model.addAttribute(departmentDTO);
        return "department-details";
    }

    @PostMapping("/department-details")
    public String edithDepartment(@Valid DepartmentDTO departmentDTO,
                                BindingResult bindingResult,
                                RedirectAttributes rAtt){

        if(bindingResult.hasErrors() || !employeeService.isExistEmployee(departmentDTO.getManager())){
            rAtt.addFlashAttribute("departmentDTO", departmentDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.departmentDTO", bindingResult);
            rAtt.addFlashAttribute("isNotExist", true);
            return "redirect:/department-details";
        }

        departmentService.editDepartment(departmentDTO);
        return "redirect:/departments";
    }

    //create department
    @GetMapping("/add-department")
    public String viewAddDepartmentForm(Model model) {
        model.addAttribute("allEmployees", projectService.getAllEmployees());

        return "add-department";
    }

    @PostMapping("/add-department")
    public String creatDepartment(
            @Valid AddDepartmentDTO addDepartmentDTO,
            BindingResult bindingResult,
            RedirectAttributes rAtt) {

        if (bindingResult.hasErrors() || departmentService.isExistDepartment(addDepartmentDTO.getDepartmentName())) {
            rAtt.addFlashAttribute("addDepartmentDTO", addDepartmentDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.addDepartmentDTO", bindingResult);
            rAtt.addFlashAttribute("isExist", true);
            return "redirect:/add-department";
        }

        departmentService.addDepartment(addDepartmentDTO);

        return "redirect:/departments";
    }

    @PostMapping("/delete-department/{id}")
    public String deleteDepartment(@PathVariable("id") Long id) {

        departmentService.removeDepartment(id);

        return "redirect:/departments";
    }
}
