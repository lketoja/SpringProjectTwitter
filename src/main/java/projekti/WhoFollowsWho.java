/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

/**
 *
 * @author Lotta
 */
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WhoFollowsWho extends AbstractPersistable<Long> {
    
    @ManyToOne
    private Account theOneFollowed;
    
    @ManyToOne
    private Account follower;
    
    private LocalDateTime startTime;
    
    
    

    
}
