package bg.softuni.human_resource_managements.web;

import bg.softuni.human_resource_managements.model.dto.AddEmployeeDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EmployeeController {
    @ModelAttribute("addEmployeeDTO")
    public AddEmployeeDTO createEmptyDTO() {
        return new AddEmployeeDTO();
    }

    @GetMapping("/login")
    public String viewLogin() {
        return "login";
    }

    @GetMapping("/registration")
    public String registrationView() {
        return "registration";
    }

    @PostMapping("/registration")//линк на страницата
    public String addEmployee(
            @Valid AddEmployeeDTO addEmployeeDTO,
            BindingResult bindingResult,
            RedirectAttributes rAtt) {

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("addEmployeeDTO", addEmployeeDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.addEmployeeDTO", bindingResult);
            return "redirect:/registration";//линк на страницата
        }
        return "regirect:/login";
    }
}
