/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.time.LocalDateTime;
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
public class MessageController {
    
    @Autowired
    private AccountRepository userRepo;
    
    @Autowired
    private MessageRepository messageRepo;
    
    @Autowired
    private WhoFollowsWhoRepository whoFollowsWhoRepo;
    
    
    @PostMapping("/{username}/add-message")
    public String saveMessage(@PathVariable String username, @RequestParam String text){
        Account user = userRepo.findByUsername(username);
        Message message = new Message(user, text, LocalDateTime.now());
        messageRepo.save(message);
        return "redirect:/{username}";
    }
    
}
