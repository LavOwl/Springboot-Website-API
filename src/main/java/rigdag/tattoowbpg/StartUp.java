package rigdag.tattoowbpg;

import jakarta.annotation.PostConstruct;
import rigdag.tattoowbpg.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartUp {
    
    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        userService.createUser("admin", "password123");
    }
}