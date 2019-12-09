/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lotta
 */
@Service
public class WhoFollowsWhoService {
    
    @Autowired 
    private WhoFollowsWhoRepository followRepo;
    
    
    
}
