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
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Lob;
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
public class Photo extends AbstractPersistable<Long> {
   
    @ManyToOne
    private User user;
    
    @Lob
    private byte[] content;
    
    private String description;
    private String mediaType;
    private Long photoSize;
    
    private HashMap<User,String> comments;
    
    @OneToMany
    private List<User> likes;
    
    private boolean profilePhoto;
    
    public Photo(User user, byte[] content, String description,
            String mediaType, Long size){
        this.user = user;
        this.content = content;
        this.description = description;
        this.mediaType = mediaType;
        photoSize = size;
        comments = new HashMap<>();
        likes = new ArrayList<>();
        profilePhoto = false;
        
    }
    
}
