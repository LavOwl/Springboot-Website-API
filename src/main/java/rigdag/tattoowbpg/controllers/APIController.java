package rigdag.tattoowbpg.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rigdag.tattoowbpg.entities.SocialMedia;
import rigdag.tattoowbpg.entities.TattooImage;
import rigdag.tattoowbpg.services.SocialMediaService;
import rigdag.tattoowbpg.services.TattooImageService;

@RestController //Equivalent to @Controller for the class + @ResponseBody for each method
@RequestMapping(path = "api/petitions")
public class APIController {

    @Autowired
    private SocialMediaService socialMediaService;

    @Autowired
    private TattooImageService tattooImageService;

    @GetMapping
    public List<TattooImage> getAll(){
        return tattooImageService.getTattooImages();
    }

    @PostMapping
    public void saveOrUpdate(@RequestBody SocialMedia SocialMedia){
        socialMediaService.saveOrUpdate(SocialMedia);
    }

    @GetMapping("/{tattooImageId}")
    public byte[] getById(@PathVariable Long tattooImageId){
        TattooImage tattooImage = tattooImageService.getTattooImage(tattooImageId).orElse(null);
        if (tattooImage != null){
            return tattooImage.getImage();
        }
        else{
            return null;
        }
    }
}
