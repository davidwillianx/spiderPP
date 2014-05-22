/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package socket;

import javax.json.JsonObject;

/**
 *
 * @author smartphonne
 */
public class Card extends Message{
    
    private final String type = "cardSelected";
    
    private int value;
    private String userNameOption;
    private int idUsuario;
    
    
    public Card(JsonObject json)
    {
         this.setValue(Integer.parseInt(json.getString("value")));
         this.setUserNameOtion(json.getString("userNameOption"));
         this.setIdUsuario(json.getInt("idUsuario"));
    }
    
    public void setValue(int value)
    {
        this.value = value;
    }
    
    public int getValue()
    {
        return this.value;
    }
    
    public void setUserNameOtion(String userName)
    {
        this.userNameOption = userName;
    }
    
    public String getUserNameOption()
    {
        return this.userNameOption;
    }
           
    public void setIdUsuario(int idUsuario)
    {
        this.idUsuario = idUsuario;
    }
    
    public int getIdUsuario()
    {
        return this.idUsuario;
    }
}
