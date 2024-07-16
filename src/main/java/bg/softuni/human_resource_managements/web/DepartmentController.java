package bg.softuni.human_resource_managements.web;

import bg.softuni.human_resource_managements.model.dto.AddDepartmentDTO;
import bg.softuni.human_resource_managements.model.dto.AddEmployeeDTO;
import bg.softuni.human_resource_managements.model.dto.DepartmentDTO;
import bg.softuni.human_resource_managements.model.dto.EmployeeDTO;
import bg.softuni.human_resource_managements.service.DepartmentService;
import bg.softuni.human_resource_managements.service.EmployeeService;
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

    public DepartmentController(DepartmentService departmentService, EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    @GetMapping("/departments")
    public String allDepartments(Model model){
        model.addAttribute("departments", departmentService.getAllDepartmentsDTOS());

        return "departments";
    }

    @PostMapping("/department-details/{id}")
    public String pullEdithDepartment(@PathVariable("id") Long id, Model model){

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

    @GetMapping("/department-details")
    public String showEmployeeDetails() {

        return "department-details";
    }

    @PostMapping("/delete-department/{id}")
    public String deleteDepartment(@PathVariable("id") Long id) {

        departmentService.removeDepartment(id);

        return "redirect:/departments";
    }
}
