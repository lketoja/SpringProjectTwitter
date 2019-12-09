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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Photo extends AbstractPersistable<Long> {
   
    @ManyToOne
    private Account user;
    
    @Lob
    private byte[] content;
    
    private String description;
    private String mediaType;
    private Long photoSize;
    
    @OneToMany
    private List<Comment> comments;
    
    @ManyToMany
    private List<Account> likes;
    
    private boolean profilePhoto;
    
    public Photo(Account user, byte[] content, String description,
            String mediaType, Long size){
        this.user = user;
        this.content = content;
        this.description = description;
        this.mediaType = mediaType;
        photoSize = size;
        
        likes = new ArrayList<>();
        profilePhoto = false;
        
    }

    public String getMediaType() {
        return mediaType;
    }

    public long getPhotoSize() {
        return photoSize;
    }
    
    public byte[] getContent(){
        return content;
    }
    
    public String getDescription(){
        return description;
    }
    
    public List<Comment> getComments(){
        return comments;
    }
    
    
    
}
