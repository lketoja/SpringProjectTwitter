/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


/**
 * Ne kontrollerimetodit, jotka ovat samoja sekä viesteille
 * että kuville, eli like(), dontLike() ja comment(), löytyvät luokasta
 * InteractableController.
 * 
 * @author Lotta
 */
@Controller
public class PhotoController {
    
    @Autowired
    private PhotoRepository photoRepo;
    
    @Autowired
    private AccountRepository userRepo;
    
    @Autowired
    private AccountService userServ;
    
    @Autowired
    private WhoFollowsWhoService whoFollowsWhoServ;

    @GetMapping("/{username}/photos")
    public String viewGallery(Model model, @PathVariable String username){
        Account user = userRepo.findByUsername(username);
        Long userId = user.getId();
        model.addAttribute("user", user);
        model.addAttribute("allUsers", userServ.getAllOtherUsers(user));
        model.addAttribute("loggedInUser", userServ.getLoggedInUser());
        model.addAttribute("whoIFollow", userRepo.findAccountByFollowerId(userId));
        model.addAttribute("whoFollowsMe", userRepo.findAccountByTheOneFollowedId(userId));
        model.addAttribute("whoIFollowAndTime", whoFollowsWhoServ.findByFollowerIdAsAccountAndFollowTimeObjects(user));
        model.addAttribute("whoFollowsMeAndTime", whoFollowsWhoServ.findByTheOneFollowedAsAccountAndFollowTimeObjects(user));
        model.addAttribute("photos", photoRepo.findByUserId(userId));         
        return "photos";
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
        return "redirect:/" + user.getUsername();
    }

    @PostMapping("/{loggedInUser}/{photoId}/change-profile-photo")
    public String changeProfilePhoto(@PathVariable String loggedInUser, 
            @PathVariable Long photoId){
        Account user = userRepo.findByUsername(loggedInUser);
        Photo photo = photoRepo.getOne(photoId);
        user.setProfilePhoto(photo);
        userRepo.save(user);
        return "redirect:/{loggedInUser}/photos";
    }
    
}
