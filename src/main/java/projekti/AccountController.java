/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

/**
 *
 * @author Lotta
 */


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
    
    @Autowired
    private CommentRepository commentRepo;
    
    @Autowired
    private InteractableRepository interactableRepo;
    
//    @GetMapping("/")
//    public String afterLogin(){
//        Account loggedInUser = userServ.getLoggedInUser();
//        return "redirect:/" + loggedInUser.getUsername();
//    }
    
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
        System.out.println(userRepo.findAll());
        Account user = userRepo.findByUsername(username);
        Long userId = user.getId();
        if(user==null){
            System.out.println("wtf");
        }
        model.addAttribute("user", user);
        model.addAttribute("allUsers", userServ.getAllOtherUsers(user));
       
        
        //Pageable pageable = (Pageable) PageRequest.of(0,10, Sort.by("sendTime"));
        model.addAttribute("messages", messageRepo.findByUserIdOrFollowingIds(userId));
       
        model.addAttribute("photos", photoRepo.findByUserId(userId));
        
        model.addAttribute("whoIFollow", userRepo.findAccountByFollowerId(userId));
        model.addAttribute("whoFollowsMe", userRepo.findAccountByTheOneFollowedId(userId));

        model.addAttribute("whoIFollowAndTime", whoFollowsWhoServ.findByFollowerIdAsAccountAndFollowTimeObjects(user));
        model.addAttribute("whoFollowsMeAndTime", whoFollowsWhoServ.findByTheOneFollowedAsAccountAndFollowTimeObjects(user));
     
        model.addAttribute("loggedInUser", userServ.getLoggedInUser());
        
        return "user-home";
    }
    
    @GetMapping("/photos/{photoId}")
    public ResponseEntity<byte[]> viewPhoto(@PathVariable Long photoId){
        Photo photo = photoRepo.getOne(photoId);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(photo.getMediaType()));
        headers.setContentLength(photo.getPhotoSize());
        return new ResponseEntity<>(photo.getContent(), headers, HttpStatus.CREATED);
    }
    
    
    
    @PostMapping("/{userId}/add-photo")
    public String savePhoto(@RequestParam("file") MultipartFile file, 
            @PathVariable Long userId, @RequestParam String description) throws IOException{
        Account user = userRepo.getOne(userId);
        Photo photo = new Photo(user, file.getBytes(), description,
            file.getContentType(), file.getSize());
        photoRepo.save(photo);
        //user.getPhotos().add(photo);
        //userRepo.save(user);    
        return "redirect:/" + user.getUsername();
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
