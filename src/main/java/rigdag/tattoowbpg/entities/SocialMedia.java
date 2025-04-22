package rigdag.tattoowbpg.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "SocialMedia") //Redundant, written for clarity
public class SocialMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long socialMediaId;

    @Column(name = "name")
    private String name;

    @Column(name = "active")
    private boolean active = true;

    @Column(name = "link")
    private String link;

    @Lob
    @Column(name="image", columnDefinition = "LONGBLOB")
    private byte[] image;

    public SocialMedia(String name, String link, byte[] image) {
        this.name = name;
        this.link = link;
        this.image = image;
    }

    public SocialMedia(){
        
    }

}
