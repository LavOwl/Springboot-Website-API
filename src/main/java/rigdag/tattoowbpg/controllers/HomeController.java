package rigdag.tattoowbpg.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;

import rigdag.tattoowbpg.entities.TattooImage;
import rigdag.tattoowbpg.entities.User;
import rigdag.tattoowbpg.services.GoogleConnectService;
import rigdag.tattoowbpg.services.TattooImageService;
import rigdag.tattoowbpg.services.UserService;

import com.google.auth.oauth2.GoogleCredentials;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;

import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Events;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.AccessToken;

import org.springframework.ui.Model;

@Controller
public class HomeController {

    
    @Autowired
    private GoogleConnectService googleConnectService;

    @Autowired
    private UserService userService;

    @Autowired
    private TattooImageService tattooImageService;

    @ModelAttribute
    public void addHttpServletRequestToModel(HttpServletRequest request, Model model) {
        model.addAttribute("httpServletRequest", request);
    }

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/turnosoffline")
    public String test(Model model){
        return "offlineappointments";
    }

    @GetMapping("/turnos")
    public String getCalendarEvents(Model model) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); //Fetch logged user
        user = userService.getUserById(user.getId()).get(); //Fetch by id to get the token from the BD (it's not stored in the principal itself)
        if(user.getGoogleAccessToken() == null){
            return "redirect:/turnosoffline";
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseMap = objectMapper.readValue(user.getGoogleAccessToken(), Map.class); //Used to extract access token from json

        GoogleCredentials credentials = GoogleCredentials.create(new AccessToken((String) responseMap.get("access_token"), null))
            .createScoped(CalendarScopes.CALENDAR_READONLY);

        Calendar calendarService = new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials))
                .setApplicationName("Web Tattoo Admin")
                .build();

        Calendar.Events.List request = calendarService.events().list("primary");
        Events events = request.execute();
        List<Map<String, Object>> calendarEvents = events.getItems().stream().map(event -> Map.of(
                "title", (Object) event.getSummary(),
                "start", (Object) event.getStart().getDateTime().toStringRfc3339(),
                "end", (Object) event.getEnd().getDateTime().toStringRfc3339()
            )).toList();
        model.addAttribute("calendarEvents", calendarEvents);

        return "appointments";
    }

    @GetMapping("/sobremi")
    public String petitions(Model model) {
        return "about";
    }

    @GetMapping("/publicaciones")
    public String publications(Model model) {
        model.addAttribute("images", tattooImageService.getTattooImages());
        return "publications";
    }

    @PostMapping("/subirpublicacion")
    public String saveOrUpdate(@RequestParam("title") String title, @RequestParam("description") String description, @RequestParam("type") String type, @RequestParam("image") MultipartFile file, Model model){
        try {
            byte[] imageData = file.getBytes();
            TattooImage tattooImage = new TattooImage(title, description, type, imageData);
            tattooImageService.saveOrUpdate(tattooImage);

            model.addAttribute("message", "Image uploaded successfully!");
        } catch (Exception e) {
            model.addAttribute("message", "Failed to upload image: " + e.getMessage());
        }
        return "redirect:/publicaciones";
    }


    @GetMapping("/login/oauth2/code/google")
    public String grantCode(@RequestParam("code") String code, @RequestParam("scope") String scope, @RequestParam("authuser") String authUser, @RequestParam("prompt") String prompt) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = googleConnectService.getOauthAccessTokenGoogle(code);
        userService.addToken(userService.getUserById(user.getId()).get(), token);
        return "redirect:/turnos";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
