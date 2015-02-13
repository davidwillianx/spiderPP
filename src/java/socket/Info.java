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
public class Info extends Message{
     private final String type = "info";
     private String message;
     
     
     public Info(JsonObject json){
         this.message = json.getString("message");
     }
 
     public void setMessage(String message){
         this.message = message;
     }
     
     public String setMessage(){
         return this.message;
     }
     
     public String getType()
     {
         return this.type;
     }
}
