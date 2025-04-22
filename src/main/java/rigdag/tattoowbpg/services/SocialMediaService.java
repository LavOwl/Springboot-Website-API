package rigdag.tattoowbpg.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rigdag.tattoowbpg.entities.SocialMedia;
import rigdag.tattoowbpg.repositories.SocialMediaRepository;

@Service
public class SocialMediaService {
    
    @Autowired
    SocialMediaRepository socialMediaRepository;

    public List<SocialMedia> getSocialMedias(){
        return socialMediaRepository.findAll();
    }

    public Optional<SocialMedia> getSocialMedia(Long id){
        return socialMediaRepository.findById(id);
    }

    public void saveOrUpdate(SocialMedia SocialMedia){
        socialMediaRepository.save(SocialMedia);
    }

    public void delete(Long id){
        socialMediaRepository.deleteById(id);
    }
}
