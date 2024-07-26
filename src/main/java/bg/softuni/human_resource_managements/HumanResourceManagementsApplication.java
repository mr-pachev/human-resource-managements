package bg.softuni.human_resource_managements;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HumanResourceManagementsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HumanResourceManagementsApplication.class, args);
    }

}
