package rigdag.tattoowbpg.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rigdag.tattoowbpg.entities.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    
}
