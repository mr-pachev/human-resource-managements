package bg.softuni.human_resource_managements.web;

import bg.softuni.human_resource_managements.model.dto.AddUserDTO;
import bg.softuni.human_resource_managements.model.dto.LoginUserDTO;
import bg.softuni.human_resource_managements.model.enums.RoleName;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    @ModelAttribute("addUserDTO")
    public AddUserDTO addUserDTO() {
        return new AddUserDTO();
    }

    @GetMapping("/registration")
    public String viewAddUser(Model model){
        model.addAttribute("roles", RoleName.values());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @Valid AddUserDTO addUserDTO,
            BindingResult bindingResult,
            RedirectAttributes rAtt) {

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("addUserDTO", addUserDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.addUserDTO", bindingResult);
            return "redirect:/registration";
        }



        return "redirect:/login";
    }

    @GetMapping("/login")
    public String viewLogin(){
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(
            @Valid LoginUserDTO loginUserDTO,
            BindingResult bindingResult,
            RedirectAttributes rAtt) {

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("loginUserDTO", loginUserDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.loginUserDTO", bindingResult);
            return "redirect:/login";
        }

        return "redirect:/index";
    }

}
