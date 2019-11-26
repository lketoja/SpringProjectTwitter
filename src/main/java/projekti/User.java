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
import java.util.ArrayList;
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
public class User extends AbstractPersistable<Long> {
    
    private String name;
    private String username;
    private String password;
    
    @OneToMany
    private List<WhoFollowsWho> whoFollowsWho;
    
    @OneToMany
    private List<Photo> photos;
    
    @OneToMany
    private List<Message> messages;
    
    public User(String name, String username, String password){
        this.name = name;
        this.username = username;
        this.password = password;
        whoFollowsWho = new ArrayList<>();
        photos = new ArrayList<>();
        messages = new ArrayList<>();
    }
    
    
    
    
}
