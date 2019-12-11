/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.util.ArrayList;
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
    
    @Autowired
    private WhoFollowsWhoRepository whoFollowsWhoRepo;
    
    public Account getLoggedInUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account loggedInUser = userRepo.findByUsername(auth.getName());
        return loggedInUser;
    
    }
    
    public List<Account> getFollowingAsUserObjects(Account user) {

        List<Account> following = new ArrayList<>();
        List<WhoFollowsWho> followingAsWFW = whoFollowsWhoRepo.findByFollowerId(user.getId());
    
        for (WhoFollowsWho wfw : followingAsWFW) {
            following.add(wfw.getTheOneFollowed());
        }
        return following;
    }
    
    public List<Account> getFollowersAsUserObjects(Account user) {

        List<Account> followers = new ArrayList<>();
        List<WhoFollowsWho> followersAsWFW = whoFollowsWhoRepo.findByTheOneFollowedId(user.getId());
    
        for (WhoFollowsWho whoFollowsWho : followersAsWFW) {
            followers.add(whoFollowsWho.getFollower());
        }
        return followers;
    }
    
    public List<Account> getAllOtherUsers(Account user){
        List<Account> users = userRepo.findAll();
        users.remove(user);
        return users;
    }
    
}
