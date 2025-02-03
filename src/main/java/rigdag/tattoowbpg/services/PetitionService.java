package rigdag.tattoowbpg.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rigdag.tattoowbpg.entities.Petition;
import rigdag.tattoowbpg.repositories.PetitionRepository;

@Service
public class PetitionService {
    
    @Autowired
    PetitionRepository petitionRepository;

    public List<Petition> getPetitions(){
        return petitionRepository.findAll();
    }

    public Optional<Petition> getPetition(Long id){
        return petitionRepository.findById(id);
    }

    public void saveOrUpdate(Petition petition){
        petitionRepository.save(petition);
    }

    public void delete(Long id){
        petitionRepository.deleteById(id);
    }
}
