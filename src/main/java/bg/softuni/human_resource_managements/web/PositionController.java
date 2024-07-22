package bg.softuni.human_resource_managements.web;

import bg.softuni.human_resource_managements.service.PositionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PositionController {

    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping("/positions")
    public String viewAllPositions(Model model){
        model.addAttribute("allPositionsDTOS", positionService.getAllPositionsDTOS());

        return "positions";
    }
}
