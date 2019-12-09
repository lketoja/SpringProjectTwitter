/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.awt.print.Pageable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Lotta
 */
public interface PhotoRepository extends JpaRepository<Photo, Long>{
    
    
    
    public List<Photo> findByUserId(Long userId);
    
}
