package rigdag.tattoowbpg.entities;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="Tattoo_Images")
public class TattooImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tattooImageId;

    @Column(name="title", nullable=false, length=511)
    private String title;

    @Column(name="description", nullable=false, length = 1023)
    private String description;

    @Column(name="type", nullable=false, length = 511)
    private String type;

    @Lob
    @Column(name="image", columnDefinition = "LONGBLOB")
    private byte[] image;

    public TattooImage(String title, String description, String type, byte[] image) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.image = image;
    }

}
