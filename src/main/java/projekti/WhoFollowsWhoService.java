/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lotta
 */
@Service
public class WhoFollowsWhoService {
    
    @Autowired 
    private WhoFollowsWhoRepository whoFollowsWhoRepo;
    
    public List<AccountAndFollowTime> findByFollowerIdAsAccountAndFollowTimeObjects(Account user) {

        List<AccountAndFollowTime> followingAndTime = new ArrayList<>();
        List<WhoFollowsWho> followingAsWFW = whoFollowsWhoRepo.findByFollowerId(user.getId());
    
        for (WhoFollowsWho whoFollowsWho : followingAsWFW) {
            AccountAndFollowTime accountAndTime = new AccountAndFollowTime(
                    whoFollowsWho.getTheOneFollowed(), whoFollowsWho.getStartTime());
            followingAndTime.add(accountAndTime);
        }
        return followingAndTime;
    }
    
    public List<AccountAndFollowTime> findByTheOneFollowedAsAccountAndFollowTimeObjects(Account user) {

        List<AccountAndFollowTime> followersAndTime = new ArrayList<>();
        List<WhoFollowsWho> followersAsWFW = whoFollowsWhoRepo.findByTheOneFollowedId(user.getId());
    
        
        for (WhoFollowsWho whoFollowsWho : followersAsWFW) {
            AccountAndFollowTime accountAndTime = new AccountAndFollowTime(
                    whoFollowsWho.getFollower(), whoFollowsWho.getStartTime());
            followersAndTime.add(accountAndTime);
        }
        return followersAndTime;
    }
    
    
    
}
