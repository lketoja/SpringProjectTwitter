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
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Lotta
 */
@Controller
public class InteractableController {
    
    @Autowired
    private InteractableRepository interactableRepo;
    
    @Autowired
    private AccountRepository userRepo;
    
    @Autowired
    private CommentRepository commentRepo;
    
    
    @PostMapping("/{username}/{interactableId}/{loggedInUser}/like")
    public String like(@PathVariable Long interactableId, @PathVariable String username, 
            @PathVariable String loggedInUser){
        Interactable interactable = interactableRepo.getOne(interactableId);
        Account currentUser = userRepo.findByUsername(loggedInUser);
        interactable.getLikes().add(currentUser);
        interactableRepo.save(interactable);        
        return "redirect:/{username}";
    }
    
    @PostMapping("/{username}/{interactableId}/{loggedInUser}/dont-like")
    public String DontLike(@PathVariable Long interactableId, @PathVariable String username, 
            @PathVariable String loggedInUser){
        Interactable interactable = interactableRepo.getOne(interactableId);
        Account currentUser = userRepo.findByUsername(loggedInUser);
        interactable.getLikes().remove(currentUser);
        interactableRepo.save(interactable);        
        return "redirect:/{username}";
    }
    
    @PostMapping("/{username}/{interactableId}/{loggedInUser}/comment")
    public String comment(@PathVariable String username, @PathVariable Long interactableId,  
            @PathVariable String loggedInUser, @RequestParam String text){
        Comment comment = new Comment(userRepo.findByUsername(loggedInUser), text);
        commentRepo.save(comment);
        Interactable interactable = interactableRepo.getOne(interactableId);
        interactable.getComments().add(comment);
        interactableRepo.save(interactable);
        return "redirect:/{username}";
    }
    
}
