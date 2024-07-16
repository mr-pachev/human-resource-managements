package bg.softuni.human_resource_managements.web;

import bg.softuni.human_resource_managements.model.dto.AddEmployeeDTO;
import bg.softuni.human_resource_managements.model.dto.EmployeeDTO;
import bg.softuni.human_resource_managements.service.*;
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
public class EmployeeController {
    private final EmployeeService employeeService;
    private final PositionService positionService;
    private final DepartmentService departmentService;
    private final EducationService educationService;

    public EmployeeController(EmployeeService employeeService, PositionService positionService, DepartmentService departmentService, EducationService educationService) {
        this.employeeService = employeeService;
        this.positionService = positionService;
        this.departmentService = departmentService;
        this.educationService = educationService;
    }

    @ModelAttribute("addEmployeeDTO")
    public AddEmployeeDTO createEmptyDTO() {
        return new AddEmployeeDTO();
    }

    @ModelAttribute("employeeDTO")
    public EmployeeDTO createEmptyEmployeeDTO() {
        return new EmployeeDTO();
    }

    @GetMapping("/add-employee")
    public String registrationView(Model model) {
        model.addAttribute("positions", positionService.getAllPositionNames());
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("educations", educationService.getAllEducations());
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

        employeeService.addEmployee(addEmployeeDTO);

        return "redirect:/employees";
    }

    @GetMapping("/employees")
    public String allEmployees(Model model){
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employees";
    }

    @PostMapping("/delete-employee/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {

        employeeService.removeEmployee(id);

        return "redirect:/employees";
    }

    @PostMapping("/employee-details/{id}")
    public String pullEdithEmployee(@PathVariable("id") Long id, Model model){

        EmployeeDTO  employeeDTO = employeeService.getEmployeeByID(id);
        model.addAttribute(employeeDTO);

        model.addAttribute("positions", positionService.getAllPositionNames());
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("educations", educationService.getAllEducations());

        return "employee-details";
    }

    @PostMapping("/employee-details")
    public String edithEmployee(@Valid EmployeeDTO employeeDTO,
                           BindingResult bindingResult,
                           RedirectAttributes rAtt){

        if(bindingResult.hasErrors()){
                rAtt.addFlashAttribute("employeeDTO", employeeDTO);
                rAtt.addFlashAttribute("org.springframework.validation.BindingResult.employeeDTO", bindingResult);

            return "redirect:/employee-details";
        }

        employeeService.editEmployee(employeeDTO);
        return "redirect:/employees";
    }

    @GetMapping("/employee-details")
    public String showEmployeeDetails(Model model) {

        model.addAttribute("positions", positionService.getAllPositionNames());
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("educations", educationService.getAllEducations());

        return "employee-details";
    }
}
