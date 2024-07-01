package bg.softuni.human_resource_managements.init;

import bg.softuni.human_resource_managements.model.entity.Position;
import bg.softuni.human_resource_managements.model.enums.PositionName;
import bg.softuni.human_resource_managements.repository.PositionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InitService implements CommandLineRunner {
    private final PositionRepository positionRepository;
   Map<PositionName, String> description = new HashMap<>();

    public InitService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initializeMap();

        long count = this.positionRepository.count();

        if (count > 0) {
            return;
        }

        List<Position> toInsert = Arrays.stream(PositionName.values())
                .map(pos -> new Position(pos, description.get(pos)))
                .toList();

        this.positionRepository.saveAll(toInsert);
    }

    private void initializeMap() {
        description = new HashMap<>();
        description.put(PositionName.CHIEF_EXECUTIVE_OFFICER, "The CEO is responsible for the overall management and strategic direction of the company.");
        description.put(PositionName.CHIEF_OPERATING_OFFICER, "The COO oversees the day-to-day operations of the company. They ensure that the business is running efficiently and effectively, often managing departments like production, marketing, and human resources.");
        description.put(PositionName.CHIEF_FINANCIAL_OFFICER, "The CFO manages the financial actions of the company. They oversee financial planning, financial risk management, record-keeping, and financial reporting.");
        description.put(PositionName.FINANCIAL_OFFICER, "These duties help the financial officer to effectively manage the financial aspects and maintain the financial stability and sustainability of the business.");
        description.put(PositionName.HUMAN_RESOURCES_MANAGER, "The HR Manager handles all aspects of human resources within the company. They recruit and onboard new employees.");
        description.put(PositionName.HEAD_OF_DEPARTMENT, "The role of Head of Department is crucial in ensuring the effective functioning and success of their respective department within the broader organizational structure.");
        description.put(PositionName.IT_ADMINISTRATOR, "The IT Administrator manages the company's information technology infrastructure. They handle hardware and software installations, network security, system upgrades, and troubleshooting IT issues.");
        description.put(PositionName.PROJECT_MANAGER, "Project Managers play a critical role in driving project success by effectively managing resources, risks, and stakeholder expectations throughout the project lifecycle.");
        description.put(PositionName.MARKETING_MANAGER, "The Marketing Manager develops and implements marketing strategies to promote the company's products or services.");
        description.put(PositionName.DEVELOPER, "A developer is responsible for writing, testing, and maintaining code for software applications, websites, or other digital products.");
        description.put(PositionName.SENIOR_DEVELOPER, "This position typically involves significant technical expertise and responsibility within a software development team or organization.");
        description.put(PositionName.LEAD_DEVELOPER, "A Lead Developer is responsible for guiding and overseeing the technical aspects of software projects.");
        description.put(PositionName.QUALITY_ASSURANCE, "Quality Assurance (QA) refers to a systematic process or set of activities designed to ensure that products or services meet specified requirements and standards.");
        description.put(PositionName.CUSTOMER_SUPPORT, "Assist its customers in making cost-effective and correct use of a product or service.");
        description.put(PositionName.CLEANER, "Person responsible for maintaining the cleanliness and hygiene of the office environment. Lays a crucial role in creating a pleasant and healthy work environment for office employees.");
    }
}
