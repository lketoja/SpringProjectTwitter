/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;


/**
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
    private WhoFollowsWhoRepository whoFollowsWhoRepo;
    
    
    @GetMapping("/{username}/photos")
    public String viewGallery(Model model, @PathVariable String username){
        Account user = userRepo.findByUsername(username);
        Long userId = user.getId();
        model.addAttribute("user", user);
        model.addAttribute("loggedInUser", userServ.getLoggedInUser());
        model.addAttribute("whoIFollow", whoFollowsWhoRepo.findFollowingUsernamesByAccountId(userId));
        model.addAttribute("photos", photoRepo.findByUserId(userId));
        
        
        return "photos";
    }


    
    @PostMapping("/{loggedInUser}/{photoId}/change-profile-photo")
    public String changeProfilePhoto(@PathVariable String loggedInUser, 
            @PathVariable Long photoId){
        Account user = userRepo.findByUsername(loggedInUser);
        Photo photo = photoRepo.getOne(photoId);
        user.setProfilePhoto(photo);
        userRepo.save(user);
        
        return "redirect:/{loggedInUser}";
    
    }
    
}
