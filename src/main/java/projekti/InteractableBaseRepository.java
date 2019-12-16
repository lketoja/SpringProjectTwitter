/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Tämä repositorio osoittautui turhaksi, mutta en jaksanut (uskaltanut) enää
 * lähteä muuttamaan toimivaa rakennetta.
 * 
 * @author Lotta
 */
@NoRepositoryBean
public interface InteractableBaseRepository<T extends Interactable> 
extends JpaRepository<T, Long> {
    


}
