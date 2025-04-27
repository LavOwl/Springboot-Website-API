package rigdag.tattoowbpg.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import rigdag.tattoowbpg.dto.ProfileDTO;
import rigdag.tattoowbpg.dto.SocialMediaDTO;
import rigdag.tattoowbpg.entities.Profile;
import rigdag.tattoowbpg.services.GoogleConnectService;
import rigdag.tattoowbpg.services.ProfileService;
import rigdag.tattoowbpg.services.SocialMediaService;
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

    @Autowired
    private ProfileService profileService;

    @Autowired
    private SocialMediaService socialMediaService;

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

        Profile profile = profileService.getProfile(1L).get();
        model.addAttribute("profile", new ProfileDTO(profile));
        model.addAttribute("socialMedia", socialMediaService.getSocialMedias().stream().map(f -> new SocialMediaDTO(f)).collect(Collectors.toList()));

        return "about";
    }

    @PostMapping("/actualizarPerfil")
    public String updateProfile(
        @RequestParam("fullname") String fullname, 
        @RequestParam("pronouns") String pronouns, 
        @RequestParam("description") String description, 
        @RequestParam(name = "image", required = false) MultipartFile file, 
        Model model) throws IOException{

        Profile original = profileService.getProfile(1L).get();
        Profile profile = new Profile(fullname, LocalDate.now(), pronouns, description, file != null ? file.getBytes() : original.getImage());

        profile.setProfileId(1L);
        profileService.saveOrUpdate(profile);

        return "redirect:/sobremi";
    }

    @GetMapping("/publicaciones")
    public String publications(Model model) {
        model.addAttribute("images", tattooImageService.getTattooImages());
        return "publications";
    }

    @PostMapping("/subirpublicacion")
    public String saveOrUpdate(
        @RequestParam("title") String title,
        @RequestParam("description") String description, 
        @RequestParam("type") String type, 
        @RequestParam("image") MultipartFile file, 
        Model model) throws IOException, InterruptedException{

        try {
            String originalFilename = file.getOriginalFilename();
            String extension = getFileExtension(originalFilename).toLowerCase();
    
            byte[] imageData;
    
            switch (extension) {
                case "heic":
                case "avif":
                    
                    File tempInput = File.createTempFile("upload_", "." + extension);
                    File tempOutput = File.createTempFile("converted_", ".jpg");
                    file.transferTo(tempInput);
    
                    ProcessBuilder pb = new ProcessBuilder("magick", tempInput.getAbsolutePath(), tempOutput.getAbsolutePath());
                    pb.redirectErrorStream(true);
                    Process process = pb.start();
                    process.waitFor();
    
                    imageData = Files.readAllBytes(tempOutput.toPath());
                    tempInput.delete();
                    tempOutput.delete();
                    break;
    
                default:
                    imageData = file.getBytes();
            }
    
            TattooImage tattooImage = new TattooImage(title, description, type, imageData);
            tattooImageService.saveOrUpdate(tattooImage);
    
            model.addAttribute("message", "Image uploaded successfully!");
        } catch (Exception e) {
            model.addAttribute("message", "Failed to upload image: " + e.getMessage());
            throw e;
        }
        return "redirect:/publicaciones";
    }

    private String getFileExtension(String filename) {
        return filename != null && filename.contains(".")
            ? filename.substring(filename.lastIndexOf('.') + 1)
            : "";
    }


    @GetMapping("/login/oauth2/code/google")
    public String grantCode(
        @RequestParam("code") String code,
        @RequestParam("scope") String scope,
        @RequestParam("authuser") String authUser,
        @RequestParam("prompt") String prompt) {

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
