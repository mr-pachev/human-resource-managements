package bg.softuni.human_resource_managements.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "departments.api")
public class DepartmentsApiConfig  {
    private String baseDepartmentsUrl;

    public String getBaseDepartmentsUrl() {
        return baseDepartmentsUrl;
    }

    public void setBaseDepartmentsUrl(String baseDepartmentsUrl) {
        this.baseDepartmentsUrl = baseDepartmentsUrl;
    }
}
