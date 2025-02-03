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
@Table(name="Tattoo_Images")
public class TattooImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tattooImageId;

    @Lob
    @Column(name="image", columnDefinition = "LONGBLOB")
    private byte[] image;

    @Column(name = "description", nullable=false, length = 1023)
    private String description;
}
