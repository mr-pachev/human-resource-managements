package bg.softuni.human_resource_managements.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeController {

    @GetMapping("/login")
    public String viewLogin(){
        return "login";
    }

    @GetMapping("/registration")
    public String registrationView(){

        return "registration";
    }
}
