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
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WhoFollowsWho implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    
    @ManyToOne
    private Account theOneFollowed;
    
    @ManyToOne
    private Account follower;
    
    private LocalDateTime startTime;

    WhoFollowsWho(Account theOneFollowed, Account follower, LocalDateTime now) {
       this.theOneFollowed = theOneFollowed;
       this.follower = follower;
       startTime = now;
    }
    
    
    

    
}
