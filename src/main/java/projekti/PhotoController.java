/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Lotta
 */
@Controller
public class PhotoController {
    
    @Autowired
    private PhotoRepository photoRepo;
    
    @Autowired
    private AccountRepository accountRepo;
    
    @PostMapping("/{loggedInUser}/{photoId}/change-profile-photo")
    public String changeProfilePhoto(@PathVariable String loggedInUser, 
            @PathVariable Long photoId){
        Account user = accountRepo.findByUsername(loggedInUser);
        Photo photo = photoRepo.getOne(photoId);
        user.setProfilePhoto(photo);
        accountRepo.save(user);
        
        return "redirect:/{loggedInUser}";
    
    }
    
}
