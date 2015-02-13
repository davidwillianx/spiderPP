/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package socket;

import java.util.Map;

/**
 *
 * @author smartphonnee
 */
public class User {
    
    
    private Map<String,Object> user;
    private String roomHash;
    private String roomId;
    private String perfil;
    
    
    public User()
    {
        
    }
    
    public User(Map<String,Object> sessionUser)
    {
        this.user = sessionUser;
    }
    
    public boolean isScrumMaster()
    {
       return   (int) this.user.get("perfil") == 1;
    }
    
    public boolean isGameOpened()
    {
        return (boolean) this.user.get("gameOpened");
    }
    
    public boolean isMyRoom(String room)
    {
        String userRoom = (String) this.user.get("room");
        return userRoom.equals(room);
    }
}
