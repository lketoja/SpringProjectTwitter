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
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Account extends AbstractPersistable<Long> {
    
    private String name;
    private String username;
    private String password;
    @OneToOne
    private Photo profilePhoto; 
        
    public Account(String name, String username, String password){
        this.name = name;
        this.username = username;
        this.password = password;
        
    }
    
    public void setProfilePhoto(Photo photo){
        profilePhoto = photo;
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getPassword(){
        return password;
    }
    
    public Photo getProfilePhoto(){
        return profilePhoto;
    }
    
   
    
    
}
    
    
    
