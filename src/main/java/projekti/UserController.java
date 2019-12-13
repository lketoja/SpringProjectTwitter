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
public class UserController {
    
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
    private CommentRepository commentRepo;
    
    @Autowired
    private InteractableRepository interactableRepo;
    
    @GetMapping("/")
    public String afterLogin(){
        Account loggedInUser = userServ.getLoggedInUser();
        return "redirect:/" + loggedInUser.getUsername();
    }
    
    @GetMapping("/create-account")
    public String createAccount(){
        return "create-account";
    }
    
    @PostMapping("/create-account")
    public String saveNewUser(@RequestParam String username, @RequestParam String password){
         if (userRepo.findByUsername(username) != null) {
            return "redirect:/accounts";
        }
        userRepo.save(new Account(username, 
                passwordEncoder.encode(password)));
        return "redirect:/" + username;
    }
    
    @GetMapping("/{username}")
    public String userHome(Model model, @PathVariable String username){
        System.out.println(userRepo.findAll());
        Account user = userRepo.findByUsername(username);
        if(user==null){
            System.out.println("wtf");
        }
        model.addAttribute("user", user);
        model.addAttribute("allUsers", userServ.getAllOtherUsers(user));
       
        
        Pageable pageable = (Pageable) PageRequest.of(0,10);
        model.addAttribute("messages", messageRepo.findAll(pageable));
       
        model.addAttribute("photos", photoRepo.findByUserId(user.getId()));
//        System.out.println("tässä whoIFollow");
//        System.out.println(whoFollowsWhoRepo.findFollowingUsernamesByAccountId(user.getId()));
//        
//        List<Object> objektit = whoFollowsWhoRepo.findFollowingByAccountId(user.getId());
//        List<Account> following = new ArrayList<>();
//        for(Object o : objektit){
//            Account a = (Account) o;
//            following.add(a);
//        System.out.println(whoFollowsWhoRepo.findFollowingUsernamesByAccountId(user.getId()));
//        model.addAttribute("whoIFollow", whoFollowsWhoRepo.findFollowingUsernamesByAccountId(user.getId()));
//        model.addAttribute("whoFollowsMe", whoFollowsWhoRepo.findByTheOneFollowedId(user.getId()));
        model.addAttribute("whoIFollow", userServ.findByFollowerIdAsUserObjects(user));
        model.addAttribute("whoFollowsMe", userServ.findByTheOneFollowedAsUserObjects(user));
     
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
    
    @PostMapping("/{username}/add-message")
    public String saveMessage(@PathVariable String username, @RequestParam String text){
        Account user = userRepo.findByUsername(username);
        Message message = new Message(user, text, LocalDateTime.now());
        messageRepo.save(message);
        //user.getMessages().add(message);
        //userRepo.save(user);
        return "redirect:/{username}";
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
    
    
    
//    @PostMapping("/{username}/{messageId}/{loggedInUser}/dont-like-message")
//    public String dontLikeMessage(@PathVariable Long messageId, @PathVariable String username, 
//            @PathVariable String loggedInUser){
//        Message message = messageRepo.getOne(messageId);
//        Account currentUser = userRepo.findByUsername(loggedInUser);
//        message.getLikes().remove(currentUser);
//        messageRepo.save(message);
//        
//        return "redirect:/{username}";
//    }
    
//    @PostMapping("/{username}/{messageId}/{loggedInUser}/comment-message")
//    public String commentMessage(@PathVariable Long messageId, @PathVariable String username, 
//            @PathVariable String loggedInUser, @RequestParam String text){
//        Comment comment = new Comment(userRepo.findByUsername(loggedInUser), text);
//        commentRepo.save(comment);
//        Message message = messageRepo.getOne(messageId);
//        message.getComments().add(comment);
//        messageRepo.save(message);
//        
//        return "redirect:/{username}";
//    }
    
    
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
