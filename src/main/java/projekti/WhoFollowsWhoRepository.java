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
    
//    @Query("SELECT a FROM Account a Who_follows_who b"
//       + "JOIN Account a ON Account.id = Who_follows_who.the_one_followed_id "
//       + "WHERE Who_follows_who.follower_id = :accountId")
//    List<String> findFollowingUsernamesByAccountId(@Param("accountId")Long accountId);
    
    @Query(value="SELECT username FROM Who_follows_who "
       + "JOIN Account ON Account.id = Who_follows_who.the_one_followed_id "
       + "WHERE Who_follows_who.follower_id = :accountId", nativeQuery=true)
    List<String> findFollowingUsernamesByAccountId(@Param("accountId")Long accountId);
//    
//    @Query(value="SELECT Account.id, name, username, password, profile_photo_id FROM Who_follows_who "
//       + "JOIN Account ON Account.id = Who_follows_who.the_one_followed_id "
//       + "WHERE Who_follows_who.follower_id = :accountId", nativeQuery=true)
//    List<Object> findFollowingByAccountId(@Param("accountId")Long accountId);
    
 
//    @Query(value="SELECT NEW projekti.QueryFollow(username, profile_photo_id) FROM Who_follows_who "
//       + "JOIN Account ON Account.id = Who_follows_who.the_one_followed_id "
//       + "WHERE Who_follows_who.follower_id = :accountId", nativeQuery=true)
//    List<QueryFollow> findFollowingByAccountId(@Param("accountId")Long accountId);
    

    
}
