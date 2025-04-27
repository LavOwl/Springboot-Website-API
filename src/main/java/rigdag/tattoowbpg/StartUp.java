package rigdag.tattoowbpg;

import jakarta.annotation.PostConstruct;
import rigdag.tattoowbpg.entities.Profile;
import rigdag.tattoowbpg.entities.SocialMedia;
import rigdag.tattoowbpg.services.ProfileService;
import rigdag.tattoowbpg.services.SocialMediaService;
import rigdag.tattoowbpg.services.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.core.io.ClassPathResource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class StartUp {
    
    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private SocialMediaService socialMediaService;

    @PostConstruct
    public void init() {
        userService.createUser("admin", "password123");
        profileService.saveOrUpdate(new Profile("Ivy Daguerre Elgarte", LocalDate.of(2003, 8, 23), "She/Her", "Hi! Sup, it's just me, your friendly local dev <3. This is a placeholder text, please do remove it and put instead whatever you want your clients to know about you! Hopefully this page meets your needs, and helps your startup succeed, lots of love!!", loadImageAsByteArray("images/UserBlank.png")));
        socialMediaService.saveOrUpdate(new SocialMedia("Whatsapp", "https://wa.me/5492213036980", loadImageAsByteArray("icons/Social_Media/Whatsapp.png")));
        socialMediaService.saveOrUpdate(new SocialMedia("Telegram", "https://t.me/myusername", loadImageAsByteArray("icons/Social_Media/Telegram.png")));
        socialMediaService.saveOrUpdate(new SocialMedia("Instagram", "https://www.instagram.com/ide088/", loadImageAsByteArray("icons/Social_Media/Instagram.png")));
        socialMediaService.saveOrUpdate(new SocialMedia("Twitter", "https://x.com/ContraPoints", loadImageAsByteArray("icons/Social_Media/Twitter.png")));
    }

    private byte[] loadImageAsByteArray(String filename) {
        try {
            ClassPathResource resource = new ClassPathResource("static/" + filename);
            Path path = resource.getFile().toPath();
            return Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}