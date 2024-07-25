package bg.softuni.human_resource_managements.web;

import bg.softuni.human_resource_managements.model.dto.AddEmployeeDTO;
import bg.softuni.human_resource_managements.model.dto.EmployeeDTO;
import bg.softuni.human_resource_managements.model.dto.ProjectDTO;
import bg.softuni.human_resource_managements.service.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    public AddEmployeeDTO createEmptyAddEmployeeDTO() {
        return new AddEmployeeDTO();
    }

    @ModelAttribute("employeeDTO")
    public EmployeeDTO createEmptyEmployeeDTO() {
        return new EmployeeDTO();
    }

    //view all employees
    @GetMapping("/employees")
    public String allEmployees(Model model){
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employees";
    }

    //add new employee
    @GetMapping("/add-employee")
    public String viewRegistrationForm(Model model) {
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

    //edit current employee
    @PostMapping("/employee-details/{id}")
    public String referenceToEdithEmployeeForm(@PathVariable("id") Long id, Model model){

        return "redirect:/employee-details/" + id;
    }

    @GetMapping("/employee-details/{id}")
    public String fillEditEmployeeForm(@PathVariable("id") Long id, Model model) {
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

            return "redirect:/employee-details/" + employeeDTO.getId();
        }

        employeeService.editEmployee(employeeDTO);
        return "redirect:/employees";
    }

    //delete employee by id
    @PostMapping("/delete-employee/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {

        employeeService.removeEmployee(id);

        return "redirect:/employees";
    }
}
