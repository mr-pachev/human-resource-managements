package bg.softuni.human_resource_managements.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "tasks.api")
public class TasksApiConfig {
    private String baseTaskUrl;

    public String getBaseTaskUrl() {
        return baseTaskUrl;
    }

    public void setBaseTaskUrl(String baseTaskUrl) {
        this.baseTaskUrl = baseTaskUrl;
    }
}
