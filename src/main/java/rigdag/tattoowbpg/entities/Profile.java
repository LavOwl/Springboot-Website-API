package rigdag.tattoowbpg.entities;

import java.time.LocalDate;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    @Column(name = "fullname", nullable=false, length = 255)
    private String fullname;

    @Column(name = "birth_date", nullable=false)
    private LocalDate birthDate;

    @Column(name = "pronouns", nullable = false, length = 20)
    private String pronouns;

    @Column(name = "description", nullable = false, length = 10240)
    private String description;

    /*@Column(name = "email_address", nullable=false, length = 255) //SMTP allows for email addresses of up to 254 chars, including @ and domain.
    private String emailAddress;

    @Column(name = "instagram_address", nullable=false, length = 2048)
    private String instagram;

    @Column(name = "phone_number", nullable=false, length = 15) //Revisit if it's important to extend length to 20-25 (depends on how we decide to format the data)
    private String phoneNumber; //Standard storage type for phone numbers is String, as leading zeros can be lost if stored as an integer, and formatting tools like "+" or "-" cannot be used if limited to an int type.*/

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(name="image", columnDefinition = "LONGBLOB")
    private byte[] image;

    public Profile(String fullname, LocalDate birthDate, String pronouns, String description, byte[] image) {
        this.fullname = fullname;
        this.birthDate = birthDate;
        this.pronouns = pronouns;
        this.description = description;
        this.image = image;
    }

    public Profile(){
        
    }

    
}
