package rigdag.tattoowbpg.entities;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Petitions") //Redundant, written for clarity
public class Petition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petitionId;

    @Column(name = "solicited_date", nullable=false)
    private LocalDateTime solicitedDate;

    @Column(name = "phone_number", nullable=false, length = 15) //Revisit if it's important to extend length to 20-25 (depends on how we decide to format the data)
    private String phoneNumber; //Standard storage type for phone numbers is String, as leading zeros can be lost if stored as an integer, and formatting tools like "+" or "-" cannot be used if limited to an int type.

    @Column(name = "email_address", nullable=false, length = 255) //SMTP allows for email addresses of up to 254 chars, including @ and domain.
    private String emailAddress;

    @Column(name = "fullname", nullable=false, length = 255)
    private String fullname;

    @Column(name = "tattoo_size", nullable=false)
    private int tattooSize;

}
