package bg.softuni.human_resource_managements.web;

import bg.softuni.human_resource_managements.model.dto.AddUserDTO;
import bg.softuni.human_resource_managements.model.dto.LoginUserDTO;
import bg.softuni.human_resource_managements.model.dto.UserDTO;
import bg.softuni.human_resource_managements.model.enums.RoleName;
import bg.softuni.human_resource_managements.service.UserHelperService;
import bg.softuni.human_resource_managements.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final UserHelperService userHelperService;

    public UserController(UserService userService, UserHelperService userHelperService) {
        this.userService = userService;
        this.userHelperService = userHelperService;
    }

    @ModelAttribute("addUserDTO")
    public AddUserDTO addUserDTO() {
        return new AddUserDTO();
    }

    @ModelAttribute("loginUserDTO")
    public LoginUserDTO loginUserDTO() {
        return new LoginUserDTO();
    }

    @ModelAttribute("userDTO")
    public UserDTO userDTO() {
        return new UserDTO();
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
    public String viewLogin(){

        return "login";
    }

    @GetMapping("/login-error")
    public String viewLoginError(Model model) {
        model.addAttribute("showErrorMessage", true);

        return "login";
    }

    @GetMapping("/users")
    public String getUsers(Model model){
        List<UserDTO> userDTOS = userService.getAllUsers();
        model.addAttribute("userDTOS", userDTOS);

        return "users";
    }

    @PostMapping("/delete-user/{id}")
    public String deleteWord(@PathVariable("id") Long id) {

        userService.removeUser(id);

        return "redirect:/users";
    }

    @PostMapping("/user-details/{id}")
    public String pullEdithEmployee(@PathVariable("id") Long id, Model model){

        UserDTO userDTO = userService.getUserDetails(id);
        model.addAttribute(userDTO);

        model.addAttribute("roles", RoleName.values());

        return "user-details";
    }

    @GetMapping("/user-details/{id}")
    public String showUserDetails(@PathVariable("id") Long id, Model model) {

        UserDTO userDTO = userService.getUserDetails(id);
        model.addAttribute(userDTO);

        model.addAttribute("roles", RoleName.values());

        return "user-details";
    }

    @PostMapping("/edit-user-details")
    public String edithEmployee(@Valid UserDTO userDTO,
                                BindingResult bindingResult,
                                RedirectAttributes rAtt){

        if(bindingResult.hasErrors()){
            rAtt.addFlashAttribute("userDTO", userDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.userDTO", bindingResult);

            return "redirect:/user-details";
        }

        userService.editUser(userDTO);
        return "redirect:/users";
    }
}
