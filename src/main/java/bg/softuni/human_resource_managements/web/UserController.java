package bg.softuni.human_resource_managements.web;

import bg.softuni.human_resource_managements.model.dto.AddUserDTO;
import bg.softuni.human_resource_managements.model.dto.LoginUserDTO;
import bg.softuni.human_resource_managements.model.enums.RoleName;
import bg.softuni.human_resource_managements.service.UserService;
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
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

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

        boolean confirmPassword = addUserDTO.getPassword().equals(addUserDTO.getConfirmPassword());

        if (bindingResult.hasErrors() || !confirmPassword) {
            rAtt.addFlashAttribute("addUserDTO", addUserDTO);
            rAtt.addFlashAttribute("unconfirmed", true);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.addUserDTO", bindingResult);

            return "redirect:/registration";
        }

        boolean isCreatedUser =  userService.addUser(addUserDTO);
        if (!isCreatedUser) {
            rAtt.addFlashAttribute("addUserDTO", addUserDTO);
            rAtt.addFlashAttribute("noAddedUser", true);
            return "redirect:/registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String viewLogin(Model model){
        model.addAttribute("loginUserDTO", new  LoginUserDTO());
        return "login";
    }

    @GetMapping("/login-error")
    public String viewLoginError(Model model) {
        model.addAttribute("showErrorMessage", true);
        model.addAttribute("loginUserDTO", new LoginUserDTO());
        return "login";
    }
}
