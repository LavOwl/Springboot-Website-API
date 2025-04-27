package rigdag.tattoowbpg.dto;

import java.time.LocalDate;
import java.time.Period;
import java.util.Base64;

import lombok.Data;
import rigdag.tattoowbpg.entities.Profile;

@Data
public class ProfileDTO {

    private String fullname;

    private int age;

    private String pronouns;

    private String description;

    private String image;

    public ProfileDTO(Profile profile){
        this.fullname = profile.getFullname();
        this.age = Period.between(profile.getBirthDate(), LocalDate.now()).getYears();
        this.pronouns = profile.getPronouns();
        this.description = profile.getDescription();
        this.image = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(profile.getImage());
    }
}
