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
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("Message")
public class Message extends Interactable implements Serializable {

    private String text;
    private LocalDateTime sendTime;
    
    public Message(Account user, String text, LocalDateTime sendTime){
        super(user);
        this.text=text;
        this.sendTime=sendTime;
    }
 
}
