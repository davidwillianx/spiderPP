/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package socket;

import javax.json.JsonObject;

/**
 *
 * @author smartphonnee
 */
public class Notice extends Message{
    
    private String type = "notice";
    private String  message;
    
    public Notice(JsonObject json)
    {
        this.message = json.getString("message");
    }
    
    public String getType()
    {
        return this.type;
    }
    
    public String getMessage()
    {
        return this.message;
    }
}
