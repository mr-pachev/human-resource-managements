package bg.softuni.human_resource_managements.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeController {

    @GetMapping("/")
    public String viewLogin(){
        return "index";
    }

    @GetMapping("/registrationEmployee")
    public String registrationEmployeeView(){

        return "registrationEmployee";
    }
}
