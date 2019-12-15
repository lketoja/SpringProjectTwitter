/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Lotta
 */
public interface AccountRepository extends JpaRepository<Account, Long>{
    
    //@EntityGraph(value = "Account.profilePhotoAlso")
    public Account findByUsername(String username);
    
    @Query(value="SELECT * FROM Account WHERE id IN "
            + "(SELECT the_one_followed_id FROM WHO_FOLLOWS_WHO "
            + "WHERE follower_id = :userId)", nativeQuery=true)
    public List<Account> findAccountByFollowerId(@Param("userId") Long userId);
    
    @Query(value="SELECT * FROM Account WHERE id IN "
            + "(SELECT follower_id FROM WHO_FOLLOWS_WHO "
            + "WHERE the_one_followed_id = :userId)", nativeQuery=true)
    public List<Account> findAccountByTheOneFollowedId(@Param("userId") Long userId);
    

    
}
