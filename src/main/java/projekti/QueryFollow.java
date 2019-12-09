/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import org.springframework.stereotype.Component;

/**
 *
 * @author Lotta
 */

public class QueryFollow {
    
    private String username;
    private long profilePhotoId;
    
    public QueryFollow(String u, long ppi){
        username = u;
        profilePhotoId = ppi;
    }
    
}
