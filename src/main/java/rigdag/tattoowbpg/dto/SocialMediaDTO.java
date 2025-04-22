package rigdag.tattoowbpg.dto;

import java.util.Base64;
import lombok.Data;
import rigdag.tattoowbpg.entities.SocialMedia;

@Data
public class SocialMediaDTO {

    private Long socialMediaId;

    private String name;

    private boolean active = true;

    private String link;

    private String image;

    public SocialMediaDTO(SocialMedia socialMedia) {
        this.socialMediaId = socialMedia.getSocialMediaId();
        this.name = socialMedia.getName();
        this.active = socialMedia.isActive();
        this.link = socialMedia.getLink();
        this.image =  "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(socialMedia.getImage());
    }
}
