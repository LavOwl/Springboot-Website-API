package rigdag.tattoowbpg.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rigdag.tattoowbpg.entities.Profile;
import rigdag.tattoowbpg.repositories.ProfileRepository;

@Service
public class ProfileService {
    
    @Autowired
    ProfileRepository profileRepository;

    public Optional<Profile> getProfile(Long id){
        return profileRepository.findById(id);
    }

    public void saveOrUpdate(Profile profile){
        profileRepository.save(profile);
    }
}
