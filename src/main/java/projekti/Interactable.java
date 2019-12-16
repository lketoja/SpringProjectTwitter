/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * Tämä luokka on luokkien Message ja Photo yläluokka. Tällä perimisjärjestelyllä 
 * saadaan vähennettyä toisteista koodia. (Esimerkiksi nyt tarvitaan vain yksi
 * kontrollerimetodi like() sen sijaan, että tarvittaisiin likeMessage() ja 
 * likePhoto()
 * 
 * @author Lotta
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Interactable_Type")
public class Interactable implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    
    @ManyToOne
    private Account user;
        
    @ManyToMany
    private List<Account> likes = new ArrayList<>();
    
    @OneToMany
    private List<Comment> comments = new ArrayList<>();
    
    public Interactable(Account user){
        this.user=user;
    }
}