package bg.softuni.human_resource_managements.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "projects.api")
public class ProjectsApiConfig {
    private String baseProjectUrl;

    public String getBaseProjectUrl() {
        return baseProjectUrl;
    }

    public void setBaseProjectUrl(String baseProjectUrl) {
        this.baseProjectUrl = baseProjectUrl;
    }
}
