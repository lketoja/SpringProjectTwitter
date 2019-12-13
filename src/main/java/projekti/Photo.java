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
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("Photo")
public class Photo extends Interactable implements Serializable {
   
    @Lob
    private byte[] content;
    
    private String description;
    private String mediaType;
    private Long photoSize;
    
    public Photo(Account user, byte[] content, String description,
            String mediaType, Long size){
        super(user);
        this.content = content;
        this.description = description;
        this.mediaType = mediaType;
        photoSize = size;
        
        
    }
   
    
}
