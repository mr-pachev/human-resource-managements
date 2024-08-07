package bg.softuni.human_resource_managements.web;

import bg.softuni.human_resource_managements.model.dto.AddEmployeeDTO;
import bg.softuni.human_resource_managements.model.dto.EmployeeDTO;
import bg.softuni.human_resource_managements.service.DepartmentService;
import bg.softuni.human_resource_managements.service.EducationService;
import bg.softuni.human_resource_managements.service.EmployeeService;
import bg.softuni.human_resource_managements.service.PositionService;
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

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("addEmployeeDTO", addEmployeeDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.addEmployeeDTO", bindingResult);

            return "redirect:/add-employee";
        }

        String fullName = addEmployeeDTO.getFirstName() + " " +
                            addEmployeeDTO.getMiddleName() + " " +
                            addEmployeeDTO.getLastName();

        if(employeeService.isExistEmployee(fullName)){
            rAtt.addFlashAttribute("addEmployeeDTO", addEmployeeDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.addEmployeeDTO", bindingResult);
            rAtt.addFlashAttribute("isExist", true);

            return "redirect:/add-employee";
        }

        if(employeeService.isExistEmployeeByIN(addEmployeeDTO.getIdentificationNumber())){
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
    public String referenceToEdithEmployeeForm(@PathVariable("id") Long id){

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
                                RedirectAttributes rAtt,
                                Model model){

        if(bindingResult.hasErrors()){
            rAtt.addFlashAttribute("employeeDTO", employeeDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.employeeDTO", bindingResult);

            model.addAttribute("positions", positionService.getAllPositionNames());
            model.addAttribute("departments", departmentService.getAllDepartments());
            model.addAttribute("educations", educationService.getAllEducations());

            return "employee-details";
        }

        employeeService.editEmployee(employeeDTO);
        return "redirect:/employees";
    }

    //delete employee by id
    @PostMapping("/delete-employee/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {
        EmployeeDTO employeeDTO = employeeService.getEmployeeByID(id);

        //cannot delete this employee because it is associated with tasks with already deleted employees
        if(employeeDTO.getFirstName().equals("DEFAULT_EMP")){
            return "redirect:/employees";
        }

        employeeService.removeEmployee(id);

        return "redirect:/employees";
    }
}
