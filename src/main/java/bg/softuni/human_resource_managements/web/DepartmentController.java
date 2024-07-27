package bg.softuni.human_resource_managements.web;

import bg.softuni.human_resource_managements.model.dto.*;
import bg.softuni.human_resource_managements.service.DepartmentService;
import bg.softuni.human_resource_managements.service.EmployeeService;
import bg.softuni.human_resource_managements.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DepartmentController {

    private final DepartmentService departmentService;
    private final ProjectService projectService;

    public DepartmentController(DepartmentService departmentService, ProjectService projectService) {
        this.departmentService = departmentService;
        this.projectService = projectService;
    }

    @ModelAttribute("addDepartmentDTO")
    public AddDepartmentDTO emptyAddDepartmentDTO() {
        return new AddDepartmentDTO();
    }

    @ModelAttribute("departmentEmployeeDTO")
    public DepartmentEmployeeDTO departmentEmployeeDTO() {
        return new DepartmentEmployeeDTO();
    }

    //view all departments
    @GetMapping("/departments")
    public String viewAllDepartments(Model model){
        model.addAttribute("departments", departmentService.getAllDepartmentsDTOS());

        return "departments";
    }

    //add new department
    @GetMapping("/add-department")
    public String viewAddDepartmentForm(Model model) {
        model.addAttribute("allEmployees", projectService.getAllEmployeesNames());

        return "add-department";
    }

    @PostMapping("/add-department")
    public String creatDepartment(
            @Valid AddDepartmentDTO addDepartmentDTO,
            BindingResult bindingResult,
            RedirectAttributes rAtt) {

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("addDepartmentDTO", addDepartmentDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.addDepartmentDTO", bindingResult);

            return "redirect:/add-department";
        }

        if (departmentService.isExistDepartment(addDepartmentDTO.getDepartmentName())){
            rAtt.addFlashAttribute("addDepartmentDTO", addDepartmentDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.addDepartmentDTO", bindingResult);
            rAtt.addFlashAttribute("isExist", true);

            return "redirect:/add-department";
        }

        departmentService.addDepartment(addDepartmentDTO);

        return "redirect:/departments";
    }

    //edit current department
    @PostMapping("/department-details/{id}")
    public String referenceToEdithDepartmentForm(@PathVariable("id") Long id){

        return "redirect:/department-details/" + id;
    }

    @GetMapping("/department-details/{id}")
    public String fillDepartmentDetailsForm(@PathVariable("id") Long id, Model model) {
        DepartmentDTO departmentDTO = departmentService.getDepartmentDTOByID(id);

        model.addAttribute("allEmployees", projectService.getAllEmployeesNames());
        model.addAttribute(departmentDTO);

        return "department-details";
    }

    @PostMapping("/department-details")
    public String edithDepartment(@RequestParam("id") Long id,
                                  @Valid DepartmentDTO departmentDTO,
                                  BindingResult bindingResult,
                                  RedirectAttributes rAtt,
                                  Model model){

        departmentDTO.setId(id);

        if(bindingResult.hasErrors()){
            rAtt.addFlashAttribute("departmentDTO", departmentDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.departmentDTO", bindingResult);

            model.addAttribute("allEmployees", projectService.getAllEmployeesNames());
            return "department-details";
        }

        departmentService.editDepartment(departmentDTO);
        return "redirect:/departments";
    }

    //delete department by id
    @PostMapping("/delete-department/{id}")
    public String deleteDepartment(@PathVariable("id") Long id) {
        DepartmentDTO departmentDTO = departmentService.getDepartmentDTOByID(id);

        //cannot delete this department because it is associated with employees with already deleted departments
        if(departmentDTO.getDepartmentName().equals("DEFAULT_DEPARTMENT")){
            return "redirect:/departments";
        }

        departmentService.removeDepartment(id);

        return "redirect:/departments";
    }

    //department employees
    @PostMapping("/department-employees/{id}")
    public String fillAndViewAllDepartmentEmployees(@PathVariable("id") Long id, Model model) {
        model.addAttribute("departmentId", id);

        return "redirect:/department-employees/" + id;
    }

    @GetMapping("/department-employees/{id}")
    public String viewDepartmentEmployees(@PathVariable("id") Long id, Model model) {
        model.addAttribute("departmentEmployees", departmentService.allDepartmentEmployees(id));
        model.addAttribute("departmentId", id);
        model.addAttribute("allEmployees", departmentService.getAllEmployeesNames());

        return "department-employees";
    }

    //add another employee in current department
    @PostMapping("/department-employee/{idDep}")
    public String addEmployee(@PathVariable("idDep") Long idDep,
                              DepartmentEmployeeDTO departmentEmployeeDTO,
                              RedirectAttributes rAtt){

        String employeeName = departmentEmployeeDTO.getFullName();

        boolean isExist = departmentService.isExistEmployeeInDepartment(employeeName, idDep);
        if(isExist){
            rAtt.addAttribute("departmentEmployees", departmentService.allDepartmentEmployees(idDep));
            rAtt.addFlashAttribute("isExist", true);
            return "redirect:/department-employees/" + idDep;
        }

        departmentService.addDepartmentEmployee(departmentEmployeeDTO, idDep);

        return "redirect:/department-employees/" + idDep;
    }

    //delete current employee from current department
    @PostMapping("/delete-department-employee/{idEm}/{idDep}")
    public String deleteDepartmentEmployee(@PathVariable("idEm") Long idEm, @PathVariable("idDep") Long idDep) {
        departmentService.removeEmployeeFromDepartment(idEm, idDep);
        return "redirect:/department-employees/" + idDep;
    }
}
