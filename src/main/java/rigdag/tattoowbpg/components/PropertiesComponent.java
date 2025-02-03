package rigdag.tattoowbpg.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesComponent {
    
    private String apiKey;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey(){
        return apiKey;
    }
}
