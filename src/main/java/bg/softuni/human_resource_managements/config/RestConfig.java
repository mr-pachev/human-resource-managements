package bg.softuni.human_resource_managements.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class RestConfig {
    @Bean
    public RestClient employeesRestClient(EmployeesApiConfig employeesApiConfig){
        return RestClient
                .builder()
                .baseUrl(employeesApiConfig.getBaseUrl())
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public RestClient departmentsRestClient(DepartmentsApiConfig departmentsApiConfig){
        return RestClient
                .builder()
                .baseUrl(departmentsApiConfig.getBaseDepartmentsUrl())
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
    @Bean
    public RestClient projectRestClient(ProjectsApiConfig projectsApiConfig){
        return RestClient
                .builder()
                .baseUrl(projectsApiConfig.getBaseProjectUrl())
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public RestClient positionRestClient(PositionsApiConfig positionsApiConfig){
        return RestClient
                .builder()
                .baseUrl(positionsApiConfig.getBasePositionUrl())
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public RestClient taskRestClient(TasksApiConfig tasksApiConfig){
        return RestClient
                .builder()
                .baseUrl(tasksApiConfig.getBaseTaskUrl())
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
