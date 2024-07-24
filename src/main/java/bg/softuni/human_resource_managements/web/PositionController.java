package bg.softuni.human_resource_managements.web;

import bg.softuni.human_resource_managements.model.dto.AddPositionDTO;
import bg.softuni.human_resource_managements.model.dto.PositionDTO;
import bg.softuni.human_resource_managements.model.dto.PositionEmployeesDTO;
import bg.softuni.human_resource_managements.model.dto.ProjectEmployeeDTO;
import bg.softuni.human_resource_managements.service.PositionService;
import bg.softuni.human_resource_managements.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.swing.text.Position;

@Controller
public class PositionController {

    private final PositionService positionService;
    private final ProjectService projectService;

    public PositionController(PositionService positionService, ProjectService projectService) {
        this.positionService = positionService;
        this.projectService = projectService;
    }

    @ModelAttribute("addPositionDTO")
    public AddPositionDTO emptyAddPositionDTO() {
        return new AddPositionDTO();
    }

    @ModelAttribute("positionEmployeeDTO")
    public PositionEmployeesDTO emptyPositionEmployeesDTO() {
        return new PositionEmployeesDTO();
    }

    //view all positions
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
            return "redirect:/add-position";
        }

        if(positionService.isExistPosition(addPositionDTO.getPositionName())){
            rAtt.addFlashAttribute("addPositionDTO", addPositionDTO);
            rAtt.addFlashAttribute("isExist", true);
            return "redirect:/add-position";
        }

        positionService.addPosition(addPositionDTO);

        return "redirect:/positions";
    }

    //edit current position
    @PostMapping("/position-details/{id}")
    public String fillEditPositionForm(@PathVariable("id") Long id, Model model) {
        PositionDTO positionDTO = positionService.getPositionDTOByID(id);
        model.addAttribute(positionDTO);
        model.addAttribute("positionEmployees", positionService.allPositionEmployees(id));

        return "position-details";
    }

    @PostMapping("/position-details")
    public String editProject(@RequestParam("id") Long id,
                              @Valid PositionDTO positionDTO,
                              BindingResult bindingResult,
                              RedirectAttributes rAtt) {

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("positionDTO", positionDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.positionDTO", bindingResult);

            return "redirect:/position-details/" + positionDTO.getId();
        }


        return "redirect:/positions";
    }

    @GetMapping("/position-details/{id}")
    public String viewEditPositionForm(@PathVariable("id") Long id, Model model) {
        if (!model.containsAttribute("positionDTO")) {
            PositionDTO positionDTO = positionService.getPositionDTOByID(id);
            model.addAttribute("positionDTO", positionDTO);
        }

        return "position-details";
    }

    //position-employees
    @PostMapping("/position-employees/{id}")
    public String fillAndViewAllPositionEmployees(@PathVariable("id") Long id, Model model) {
        model.addAttribute("positionId", id);

        return "redirect:/position-employees/" + id;
    }

    @GetMapping("/position-employees/{id}")
    public String viewProjectEmployees(@PathVariable("id") Long id, Model model) {
        model.addAttribute("positionEmployees", positionService.allPositionEmployees(id));
        model.addAttribute("positionId", id);
        model.addAttribute("allEmployees", projectService.getAllEmployees());

        return "position-employees";
    }

    //add another employee in current position
    @PostMapping("/position-employee/{idPos}")
    public String addEmployee(@PathVariable("idPos") Long idPos,
                              Model model,
                              PositionEmployeesDTO positionEmployeesDTO,
                              RedirectAttributes rAtt){

        String employeeName = positionEmployeesDTO.getFullName();

        boolean isExist = positionService.isExistEmployeeInPosition(employeeName, idPos);
        if(isExist){
            rAtt.addFlashAttribute("positionEmployees", positionService.allPositionEmployees(idPos));
            rAtt.addFlashAttribute("isExist", true);
            return "redirect:/position-employees/" + idPos;
        }

        positionService.addPositionEmployee(positionEmployeesDTO, idPos);

        return "redirect:/position-employees/" + idPos;
    }
}
