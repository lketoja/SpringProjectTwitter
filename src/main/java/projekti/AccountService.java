/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lotta
 */
@Service
public class AccountService {
    
    @Autowired
    private AccountRepository userRepo;
    
    public Account getLoggedInUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account loggedInUser = userRepo.findByUsername(auth.getName());
        return loggedInUser;
    
    }
    
}
