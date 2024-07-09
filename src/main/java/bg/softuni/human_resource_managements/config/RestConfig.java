package bg.softuni.human_resource_managements.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class RestConfig {
    @Bean
    public RestClient usersRestClient(UsersApiConfig usersApiConfig){
        return RestClient
                .builder()
                .baseUrl(usersApiConfig.getBaseUrl())
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public RestClient employeesRestClient(EmployeesApiConfig employeesApiConfig){
        return RestClient
                .builder()
                .baseUrl(employeesApiConfig.getBaseEmployeesUrl())
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

}
