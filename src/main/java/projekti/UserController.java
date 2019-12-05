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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private MessageRepository messageRepo;
    
    @Autowired
    private PhotoRepository photoRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private WhoFollowsWhoRepository whoFollowsWhoRepo;
    
    @Autowired
    private CommentRepository commentRepo;
    
    @GetMapping("/")
    public String afterLogin(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUser = auth.getName();
        return "redirect:/" + loggedInUser;
    }
    
    @GetMapping("/create-account")
    public String createAccount(){
        return "create-account";
    }
    
    @PostMapping("/create-account")
    public String saveNewUser(@RequestParam String name,
            @RequestParam String username, @RequestParam String password){
         if (userRepo.findByUsername(username) != null) {
            return "redirect:/accounts";
        }
        userRepo.save(new Account(name, username, 
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
        model.addAttribute("messages", messageRepo.findAll());
        model.addAttribute("photos", photoRepo.findByUserId(user.getId()));
        model.addAttribute("whoIFollow", whoFollowsWhoRepo.findByFollowerId(user.getId()));
        model.addAttribute("whoFollowsMe", whoFollowsWhoRepo.findByTheOneFollowedId(user.getId()));
       
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUser = auth.getName();
        model.addAttribute("loggedInUser", loggedInUser);
        
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
    
    @PostMapping("/{userId}/{messageId}/like-message")
    public String likeMessage(@PathVariable Long userId, @PathVariable Long messageId){
        Account user = userRepo.getOne(userId);
        Message message = messageRepo.getOne(messageId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account loggedInUser = userRepo.findByUsername(auth.getName());
        message.getLikes().add(loggedInUser);
        messageRepo.save(message);
        
        return "redirect:/" + user.getUsername();
    }
    
    @PostMapping("/{username}/{messageId}/{loggedInUser}/comment-message")
    public String commentMessage(@PathVariable Long messageId, @PathVariable String username, 
            @PathVariable String loggedInUser, @RequestParam String text){
        Comment comment = new Comment(userRepo.findByUsername(loggedInUser), text);
        commentRepo.save(comment);
        Message message = messageRepo.getOne(messageId);
        message.getComments().add(comment);
        messageRepo.save(message);
        
        return "redirect:/{username}";
    }
    
    
    @PostMapping("/{userId}/{loggedInUser}/follow-me")
    public String followMe(@PathVariable Long userId, @PathVariable String loggedInUser){
        Account theOneFollowed = userRepo.getOne(userId);
        Account follower = userRepo.findByUsername(loggedInUser);
        WhoFollowsWho wfw = new WhoFollowsWho(theOneFollowed, follower, LocalDateTime.now());
        whoFollowsWhoRepo.save(wfw);
        
        return "redirect:/" + theOneFollowed.getUsername();
    }
    
    @PostMapping("/{username}/{whoFollowsWhoId}/remove-follower")
    public String removeFollower(@PathVariable String username, @PathVariable Long whoFollowsWhoId){
        whoFollowsWhoRepo.deleteById(whoFollowsWhoId);
        return "redirect:/{username}";
    }
    
    
    
    
    
    
    
}
