package rigdag.tattoowbpg.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rigdag.tattoowbpg.entities.TattooImage;
import rigdag.tattoowbpg.repositories.TattooImageRepository;

@Service
public class TattooImageService {
    @Autowired
    TattooImageRepository tattooImageRepository;

    public List<TattooImage> getTattooImages(){
        return tattooImageRepository.findAll();
    }

    public Optional<TattooImage> getTattooImage(Long id){
        return tattooImageRepository.findById(id);
    }

    public void saveOrUpdate(TattooImage image){
        tattooImageRepository.save(image);
    }

    public void delete(Long id){
        tattooImageRepository.deleteById(id);
    }
}
