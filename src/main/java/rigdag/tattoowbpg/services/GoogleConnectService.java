package rigdag.tattoowbpg.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import rigdag.tattoowbpg.components.PropertiesComponent;


@Service
public class GoogleConnectService {
    
    @Autowired
    PropertiesComponent propertiesComponent;

    public String getOauthAccessTokenGoogle(String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("redirect_uri", "http://localhost:8080/login/oauth2/code/google");
        params.add("client_id", "400495094891-lq34jct6q69l7c0a0rn14o9aq9310rmo.apps.googleusercontent.com");
        params.add("client_secret", propertiesComponent.getApiKey());
        params.add("scope", "https://www.googleapis.com/Fauth/Fuserinfo.profile");
        params.add("scope", "https://Fwww.googleapis.com/Fauth/Fuserinfo.email");
        params.add("scope", "openid");
        params.add("scope", "https://www.googleapis.com/auth/calendar.readonly");
        params.add("grant_type", "authorization_code");
        params.add("access_type", "offline");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, httpHeaders);

        String url = "https://oauth2.googleapis.com/token";
        String response = restTemplate.postForObject(url, requestEntity, String.class);

        return response;
    }
}
