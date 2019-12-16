/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;


import java.util.List;


/**
 *
 * @author Lotta
 */
public interface PhotoRepository extends InteractableBaseRepository<Photo>{
   
    public List<Photo> findByUserId(Long userId);
    
}
