/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Lotta
 */
public interface WhoFollowsWhoRepository extends JpaRepository<WhoFollowsWho, Long>{
    
    List<WhoFollowsWho> findByFollowerId(Long followerId);
    List<WhoFollowsWho> findByTheOneFollowedId(Long theOneFollowedId);
    WhoFollowsWho findByFollowerIdAndTheOneFollowedId(Long followerId, Long theOneFollowedId);
    
}
