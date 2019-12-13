/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;


/**
 *
 * @author Lotta
 */
public interface MessageRepository extends InteractableBaseRepository<Message>{
    
    
    @EntityGraph(attributePaths = {"comments", "likes"})
    public List<Message> findByUserId(Long userId); 
}
