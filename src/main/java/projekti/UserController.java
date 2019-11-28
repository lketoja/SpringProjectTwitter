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
        Account user = userRepo.findByUsername(username);
        model.addAttribute("user", userRepo.getOne(user.getId()));
        model.addAttribute("messages", messageRepo.findByUserId(user.getId()));
        model.addAttribute("photos", photoRepo.findByUserId(user.getId()));
        
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
        user.getMessages().add(message);
        userRepo.save(user);
        return "redirect:/{username}";
    }
    
    @PostMapping("/{userId}/add-photo")
    public String savePhoto(@RequestParam("file") MultipartFile file, 
            @PathVariable Long userId, @RequestParam String description) throws IOException{
        Account user = userRepo.getOne(userId);
        Photo photo = new Photo(user, file.getBytes(), description,
            file.getContentType(), file.getSize());
        photoRepo.save(photo);
        user.getPhotos().add(photo);
        userRepo.save(user);    
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
    
    @PostMapping("/{userId}/{loggedInUser}/follow-me")
    public String followMe(@PathVariable Long userId, @PathVariable String loggedInUser){
        Account userToBeFollowed = userRepo.getOne(userId);
        Account userWhoFollows = userRepo.findByUsername(loggedInUser);
        
        return "redirect:/" + userToBeFollowed.getUsername();
    }
    
    
    
    
    
    
    
}
