package bg.softuni.human_resource_managements.web;

import bg.softuni.human_resource_managements.model.dto.AddUserDTO;
import bg.softuni.human_resource_managements.model.dto.LoginUserDTO;
import bg.softuni.human_resource_managements.model.dto.UserDTO;
import bg.softuni.human_resource_managements.model.enums.RoleName;
import bg.softuni.human_resource_managements.repository.UserRepository;
import bg.softuni.human_resource_managements.service.EmployeeService;
import bg.softuni.human_resource_managements.service.UserHelperService;
import bg.softuni.human_resource_managements.service.UserService;
import bg.softuni.human_resource_managements.service.exception.ObjectNotFoundException;
import bg.softuni.human_resource_managements.service.session.AppUserDetailsService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final UserHelperService userHelperService;
    private final EmployeeService employeeService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserHelperService userHelperService, EmployeeService employeeService, UserRepository userRepository) {
        this.userService = userService;
        this.userHelperService = userHelperService;
        this.employeeService = employeeService;
        this.userRepository = userRepository;
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

    //view all users
    @GetMapping("/users")
    public String getAllUsers(Model model){
        List<UserDTO> userDTOS = userService.getAllUsers();
        String username = userHelperService.getUser().getUsername();

        model.addAttribute("userDTOS", userDTOS);
        model.addAttribute("loginUsername", username);

        return "users";
    }

    //create new user
    @GetMapping("/registration")
    public String viewAddUserForm(Model model){

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

        if (userService.isExistUser(addUserDTO.getUsername()) ||
                !employeeService.isExistEmployeeByIN(addUserDTO.getIdentificationNumber())) {
            rAtt.addFlashAttribute("addUserDTO", addUserDTO);
            rAtt.addFlashAttribute("noAddedUser", true);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.addUserDTO", bindingResult);

            return "redirect:/registration";
        }

        boolean confirmPassword = addUserDTO.getPassword().equals(addUserDTO.getConfirmPassword());

        if (!confirmPassword) {
            rAtt.addFlashAttribute("addUserDTO", addUserDTO);
            rAtt.addFlashAttribute("unconfirmed", true);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.addUserDTO", bindingResult);

            return "redirect:/registration";
        }

        userService.addUser(addUserDTO);

        return "redirect:/login";
    }

    //edit current user
    @PostMapping("/user-details/{id}")
    public String referenceToEditUserForm(@PathVariable("id") Long id){

        return "redirect:/user-details/" + id;
    }

    @GetMapping("/user-details/{id}")
    public String fillEditUserForm(@PathVariable("id") Long id, Model model) {
        UserDTO userDTO = userService.getUserDetails(id);

        model.addAttribute(userDTO);
        model.addAttribute("roles", RoleName.values());

        return "user-details";
    }

    @PostMapping("/user-details")
    public String editUser(@RequestParam("userId") Long userId,
                            @Valid UserDTO userDTO,
                            BindingResult bindingResult,
                            RedirectAttributes rAtt,
                            Model model){

        userDTO.setUserId(userId);

        if(userDTO.getRole() == null){
            userDTO.setRole(userHelperService.getUser().getRole().getRoleName().name());
        }

        if(bindingResult.hasErrors()){
            rAtt.addFlashAttribute("userDTO", userDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.userDTO", bindingResult);

            model.addAttribute("roles", RoleName.values());

            return "user-details";
        }

        String currentUsernameForEdit = userRepository.findById(userId)
                .orElseThrow(ObjectNotFoundException::new)
                .getUsername();

        boolean isChangedUsername = !currentUsernameForEdit.equals(userDTO.getUsername());

        if(userService.isExistUser(userDTO.getUsername()) && isChangedUsername){
            rAtt.addFlashAttribute("userDTO", userDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.userDTO", bindingResult);

            model.addAttribute("isExistUsername", true);
            model.addAttribute("roles", RoleName.values());

            return "user-details";
        }

        userService.editUser(userDTO);

        String loginUser = userHelperService.getUserDetails().getUsername();

        if(currentUsernameForEdit.equals(loginUser)){
            userHelperService.updateCurrentUserUsername(userDTO.getUsername());
        }

        return "redirect:/users";
    }

    //delete user by id
    @PostMapping("/delete-user/{id}")
    public String removeUser(@PathVariable("id") Long id) {

        userService.removeUser(id);

        return "redirect:/users";
    }

    //login
    @GetMapping("/login")
    public String viewLogin(){

        return "login";
    }

    @GetMapping("/login-error")
    public String viewLoginError(Model model) {
        model.addAttribute("showErrorMessage", true);

        return "login";
    }
}
