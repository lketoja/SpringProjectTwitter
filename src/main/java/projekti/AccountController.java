
package projekti;

/**
 *
 * @author Lotta
 */
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {
    
    @Autowired
    private AccountRepository userRepo;
    
    @Autowired
    private AccountService userServ;
    
    @Autowired
    private MessageRepository messageRepo;
    
    @Autowired
    private PhotoRepository photoRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private WhoFollowsWhoRepository whoFollowsWhoRepo;
    
    @Autowired
    private WhoFollowsWhoService whoFollowsWhoServ;
    
    
    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/after-login")
    public String afterLogin(){
        return "redirect:/" + userServ.getLoggedInUser().getUsername();
    }
    
    @GetMapping("/create-account")
    public String createAccount(){
        return "create-account";
    }
    
    @PostMapping("/create-account")
    public String saveNewUser(@RequestParam String username, @RequestParam String password){
         if (userRepo.findByUsername(username) != null) {
            return "redirect:/create-account";
        }
        userRepo.save(new Account(username, 
                passwordEncoder.encode(password)));
        return "redirect:/" + username;
    }
    
    @GetMapping("/{username}")
    public String userHome(Model model, @PathVariable String username){
        
        Account user = userRepo.findByUsername(username);
        Long userId = user.getId();
        
        model.addAttribute("user", user);
        model.addAttribute("allUsers", userServ.getAllOtherUsers(user));
       
        //Haetaan käyttäjän omat viestit ja niiden viestit, joita käyttäjä seuraa.
        //Haetaan 25 uusinta viestiä aikajärjestyksessä
        model.addAttribute("messages", messageRepo.findByUserIdOrFollowingIds(userId));
       
        model.addAttribute("photos", photoRepo.findByUserId(userId));
        
        model.addAttribute("whoIFollow", userRepo.findAccountByFollowerId(userId));
        model.addAttribute("whoFollowsMe", userRepo.findAccountByTheOneFollowedId(userId));
        
        //Seuraajien ja seurattavien yhteydessä piti näyttää myös seurauksen alkamisaika
        //joten tehdään uudet kyselyt yllä olevien lisäksi (näillä kyselyillä taas
        //olisi hankalaa tarkistaa näytetäänkä "Follow" vai "Don't follow" nappula.
        model.addAttribute("whoIFollowAndTime", whoFollowsWhoServ.findByFollowerIdAsAccountAndFollowTimeObjects(user));
        model.addAttribute("whoFollowsMeAndTime", whoFollowsWhoServ.findByTheOneFollowedAsAccountAndFollowTimeObjects(user));
     
        model.addAttribute("loggedInUser", userServ.getLoggedInUser());
        
        return "user-home";
    }
    
    @PostMapping("/{username}/{loggedInUser}/follow-me")
    public String followMe(@PathVariable String username, @PathVariable String loggedInUser){
        Account theOneFollowed = userRepo.findByUsername(username);
        Account follower = userRepo.findByUsername(loggedInUser);
        WhoFollowsWho whoFollowsWho = new WhoFollowsWho(theOneFollowed, follower, LocalDateTime.now());
        whoFollowsWhoRepo.save(whoFollowsWho);        
        return "redirect:/{username}";
    }
    
    @PostMapping("/{username}/{loggedInUser}/dont-follow")
    public String dontFollow(@PathVariable String username, @PathVariable String loggedInUser){
        Long theOneFollowedId = userRepo.findByUsername(username).getId();
        Long followerId = userRepo.findByUsername(loggedInUser).getId();
        WhoFollowsWho whoFollowsWho = whoFollowsWhoRepo.findByFollowerIdAndTheOneFollowedId(followerId, theOneFollowedId);
        whoFollowsWhoRepo.delete(whoFollowsWho);        
        return "redirect:/{username}";
    }
    
    @PostMapping("/{username}/{loggedInUser}/block-follower")
    public String removeFollower(@PathVariable String username, @PathVariable String loggedInUser){
        Long theOneFollowedId = userRepo.findByUsername(loggedInUser).getId();
        Long followerId = userRepo.findByUsername(username).getId();
        WhoFollowsWho whoFollowsWho = whoFollowsWhoRepo.findByFollowerIdAndTheOneFollowedId(followerId, theOneFollowedId);
        whoFollowsWhoRepo.delete(whoFollowsWho);        
        return "redirect:/{username}";
    }
    
}
