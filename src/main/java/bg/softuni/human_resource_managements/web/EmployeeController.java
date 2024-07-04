package bg.softuni.human_resource_managements.web;

import bg.softuni.human_resource_managements.model.dto.AddEmployeeDTO;
import bg.softuni.human_resource_managements.model.enums.DepartmentName;
import bg.softuni.human_resource_managements.model.enums.EducationName;
import bg.softuni.human_resource_managements.model.enums.PositionName;
import bg.softuni.human_resource_managements.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @ModelAttribute("addEmployeeDTO")
    public AddEmployeeDTO createEmptyDTO() {
        return new AddEmployeeDTO();
    }

    @GetMapping("/add-employee")
    public String registrationView(Model model) {
        model.addAttribute("positions", PositionName.values());
        model.addAttribute("departments", DepartmentName.values());
        model.addAttribute("educations", EducationName.values());
        return "add-employee";
    }

    @PostMapping("/add-employee")
    public String addEmployee(
            @Valid AddEmployeeDTO addEmployeeDTO,
            BindingResult bindingResult,
            RedirectAttributes rAtt) {

        if (bindingResult.hasErrors() || !employeeService.addEmployee(addEmployeeDTO)) {
            rAtt.addFlashAttribute("addEmployeeDTO", addEmployeeDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.addEmployeeDTO", bindingResult);
            rAtt.addFlashAttribute("isExist", true);
            return "redirect:/add-employee";
        }

        return "redirect:/login";
    }
}
