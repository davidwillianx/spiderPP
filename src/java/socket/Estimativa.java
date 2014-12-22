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
public class Estimativa extends Message{
    private  final String type = "rate";
    private int score;
    private int storyId;
    
    
    public Estimativa(JsonObject json){
        this.score  = Integer.parseInt(json.getString("score"));
        this.storyId = Integer.parseInt(json.getString("storyId"));
    } 
    
    public void setScore(int score){
        this.score = score;
    }    
    
    public int getScore(){
        return this.score;
    }
    
    public void setStoryId(int storyId){
        this.storyId = storyId;
    }
    
    public int getStoryId()
    {
        return this.storyId;
    }
    
    public String getType(){
        return type;
    }
}
