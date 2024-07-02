package bg.softuni.human_resource_managements.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/add-user")
    public String viewAddUser(){
        return "add-user";
    }
}
