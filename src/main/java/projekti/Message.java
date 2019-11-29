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
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message extends AbstractPersistable<Long> {
    
    @ManyToOne
    private Account user; 
    private String text;
    private LocalDateTime sendTime;
    
    @ManyToMany
    private List<Account> likes;
    
    @OneToMany
    private List<Comment> comments;
    
   
    
    public Message(Account user, String text, LocalDateTime sendTime){
        this.user=user;
        this.text=text;
        this.sendTime=sendTime;
    }

    
}
