package bg.softuni.human_resource_managements.scheduler;

import bg.softuni.human_resource_managements.model.dto.EmployeeDTO;
import bg.softuni.human_resource_managements.service.BirthdayService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BirthdayScheduler {

    private final BirthdayService birthdayService;

    public BirthdayScheduler(BirthdayService birthdayService) {
        this.birthdayService = birthdayService;
    }

    @Scheduled(cron = "0 0 0 * * *") //daily check
//    @Scheduled(fixedRate = 10000) //for testing
    public void checkBirthdays() {
        List<EmployeeDTO> birthdayEmployees = birthdayService.getBirthdayEmployees();

        if (!birthdayEmployees.isEmpty()) {
            System.out.println("Today's birthdays:");

            for (EmployeeDTO employee : birthdayEmployees) {
                System.out.println("Happy Birthday to: " + employee.toString());
            }
        } else {
            System.out.println("No birthdays today.");
        }
    }
}
