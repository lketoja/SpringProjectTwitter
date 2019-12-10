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

public class FollowQueryResult {
    
    private String username;
    private long profilePhotoId;
    
    
    
    public FollowQueryResult(String username, long photoId){
        this.username=username;
        profilePhotoId = photoId;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the profilePhotoId
     */
    public long getProfilePhotoId() {
        return profilePhotoId;
    }

    /**
     * @param profilePhotoId the profilePhotoId to set
     */
    public void setProfilePhotoId(long profilePhotoId) {
        this.profilePhotoId = profilePhotoId;
    }
    
    
    
}
