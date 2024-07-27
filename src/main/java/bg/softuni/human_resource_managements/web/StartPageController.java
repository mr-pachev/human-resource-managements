package bg.softuni.human_resource_managements.web;

import bg.softuni.human_resource_managements.model.dto.EmployeeDTO;
import bg.softuni.human_resource_managements.service.BirthdayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class StartPageController {
    private final BirthdayService birthdayService;

    public StartPageController(BirthdayService birthdayService) {
        this.birthdayService = birthdayService;
    }

    @GetMapping("/")
    public String viewIndex(Model model) {
        List<EmployeeDTO> employeeDTOS = birthdayService.getBirthdayEmployees();

        model.addAttribute("birthdayEmployees", employeeDTOS);
        return "index";
    }
}
