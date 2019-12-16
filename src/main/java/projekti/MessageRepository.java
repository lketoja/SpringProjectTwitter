/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 *
 * @author Lotta
 */
public interface MessageRepository extends InteractableBaseRepository<Message>{
    
    @Query(value="SELECT * FROM Interactable " 
    + "WHERE  Interactable_type='Message' AND (User_id IN "
    +        "(SELECT The_One_Followed_id FROM Who_Follows_Who "
    +        "WHERE Follower_id = :userId) OR User_id = :userId) "
    + "ORDER BY Send_time DESC LIMIT 25", nativeQuery=true)
    public List<Message> findByUserIdOrFollowingIds(@Param("userId") Long userId);
    
    
}
