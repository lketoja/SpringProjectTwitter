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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

//@NamedEntityGraph(name = "Account.profilePhotoAlso",
//        attributeNodes = {@NamedAttributeNode("profilePhoto")})
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    
    private String username;
    private String password;
    @OneToOne
    private Photo profilePhoto;
    
//    @OneToMany
//    private List<Photo> photos = new ArrayList<>();
    
    
        
    public Account(String username, String password){
        this.username = username;
        this.password = password;
        
    }
    
    
    
    
   
    
    
}
    
    
    
