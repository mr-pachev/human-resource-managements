package bg.softuni.human_resource_managements.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "positions.api")
public class PositionsApiConfig {
    private String basePositionUrl;

    public String getBasePositionUrl() {
        return basePositionUrl;
    }

    public void setBasePositionUrl(String basePositionUrl) {
        this.basePositionUrl = basePositionUrl;
    }
}
