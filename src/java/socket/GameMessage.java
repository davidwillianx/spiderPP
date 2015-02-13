/*
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
public class GameMessage extends Message{

    private String startTime;
    private final String type = "gameStart";
    
    public GameMessage(JsonObject json) {
        this.setStartTime(json.getString("start"));
    }
    
    public void setStartTime(String startTime)
    {
        this.startTime  = startTime;
    }
    
    public String getStartTime()
    {
        return this.startTime;
    }
    
    public String getType()
    {
        return this.type;
    }
    
}
