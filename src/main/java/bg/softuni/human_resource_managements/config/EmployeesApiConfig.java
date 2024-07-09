package bg.softuni.human_resource_managements.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "employees.api")
public class EmployeesApiConfig {
    private String baseEmployeesUrl;

    public String getBaseEmployeesUrl() {
        return baseEmployeesUrl;
    }

    public void setBaseEmployeesUrl(String baseEmployeesUrl) {
        this.baseEmployeesUrl = baseEmployeesUrl;
    }
}
