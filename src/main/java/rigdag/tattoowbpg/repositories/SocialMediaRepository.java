package rigdag.tattoowbpg.repositories;

import rigdag.tattoowbpg.entities.SocialMedia;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SocialMediaRepository extends JpaRepository<SocialMedia, Long> {
    
}
