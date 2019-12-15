/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserControllerissa Modeliin lisätään attribuutiksi lista tämän
 * luokan olioita, jotta Thymeleaf tietää jokaisen seuraajan ja seurattavan
 * kohdalla käyttäjän nimen, profiilikuvan ja seurauksen aloitusajan eikä
 * tee erillisiä tietokantakyselyjä näiden hakemiseen.
 *
 * @author Lotta
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountAndFollowTime {
    
    private Account user;
    private LocalDateTime startTime;

    
}
