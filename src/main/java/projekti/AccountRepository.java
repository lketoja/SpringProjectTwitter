/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

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
    
//    @Query("SELECT profile_photo_id FROM Account WHERE id = :id") 
//    public Long findProfilePhotoById(@Param("id") Long id);
    
}
