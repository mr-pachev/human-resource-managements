package bg.softuni.human_resource_managements.web;

import bg.softuni.human_resource_managements.model.dto.AddPositionDTO;
import bg.softuni.human_resource_managements.model.dto.AddProjectDTO;
import bg.softuni.human_resource_managements.service.PositionService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PositionController {

    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @ModelAttribute("addPositionDTO")
    public AddPositionDTO emptyAddPositionDTO() {
        return new AddPositionDTO();
    }

    @GetMapping("/positions")
    public String viewAllPositions(Model model){
        model.addAttribute("allPositionsDTOS", positionService.getAllPositionsDTOS());

        return "positions";
    }

    //create new position
    @GetMapping("/add-position")
    public String viewAddPositionForm(Model model) {

        return "add-position";
    }

    @PostMapping("/add-position")
    public String creatPosition(
            @Valid AddPositionDTO addPositionDTO,
            BindingResult bindingResult,
            RedirectAttributes rAtt) {

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("addPositionDTO", addPositionDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.addPositionDTO", bindingResult);
            rAtt.addFlashAttribute("isExist", true);
            return "redirect:/add-position";
        }

//        projectService.addProject(addProjectDTO);

        return "redirect:/projects";
    }
}
