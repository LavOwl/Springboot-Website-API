package rigdag.tattoowbpg.repositories;

import rigdag.tattoowbpg.entities.Petition;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PetitionRepository extends JpaRepository<Petition, Long> {
    
}
