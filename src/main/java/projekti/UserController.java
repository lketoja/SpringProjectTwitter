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
    private UserRepository userRepo; 
    
    @Autowired
    private MessageRepository messageRepo;
    
    @Autowired
    private PhotoRepository photoRepo;
    
    @GetMapping("/create-account")
    public String createAccount(){
        return "create-account";
    }
    
    @PostMapping("/create-account")
    public String saveNewUser(@RequestParam String name,
            @RequestParam String username, @RequestParam String password){
        userRepo.save(new User(name, username, password));
        return "redirect:/create-account";
    }
    
    @GetMapping("/users/{userId}")
    public String userHome(Model model, @PathVariable Long userId){
        model.addAttribute("user", userRepo.getOne(userId));
        model.addAttribute("messages", messageRepo.findByUserId(userId));
        model.addAttribute("photos", photoRepo.findByUserId(userId));
        return "user-home";
    }
    
    @GetMapping("/users/photos/{photoId}")
    public ResponseEntity<byte[]> viewPhoto(@PathVariable Long photoId){
        Photo photo = photoRepo.getOne(photoId);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(photo.getMediaType()));
        headers.setContentLength(photo.getPhotoSize());
        return new ResponseEntity<>(photo.getContent(), headers, HttpStatus.CREATED);
    }
    
    @PostMapping("/users/{userId}/add-message")
    public String saveMessage(@PathVariable Long userId, @RequestParam String text){
        User user = userRepo.getOne(userId);
        Message message = new Message(user, text, LocalDateTime.now());
        messageRepo.save(message);
        user.getMessages().add(message);
        userRepo.save(user);
        return "redirect:/users/{userId}";
    }
    
    @PostMapping("/users/{userId}/add-photo")
    public String savePhoto(@RequestParam("file") MultipartFile file, 
            @PathVariable Long userId, @RequestParam String description) throws IOException{
        User user = userRepo.getOne(userId);
        Photo photo = new Photo(user, file.getBytes(), description,
            file.getContentType(), file.getSize());
        photoRepo.save(photo);
        user.getPhotos().add(photo);
        userRepo.save(user);    
        return "redirect:/users/{userId}";
    }
    
    
    
    
    
    
}
