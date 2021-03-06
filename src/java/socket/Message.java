/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.io.StringWriter;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 *
 * @author smartphonne
 */
public class Message {

    private JsonObject json;
    private JsonArray jsonArray;

    public Message(){
    }
     
    public Message(JsonObject json) {
        this.json = json;
    }
    
    public Message(JsonArray jsonArray){
        this.jsonArray = jsonArray;
    }
    

    public void setJson(JsonObject json) {
        this.json = json;
    }
 
    public JsonObject getJson() {
        return this.json;
    }
    
    public void setJsonArray(JsonArray jsonArray){
        this.jsonArray = jsonArray;
    }
    
    public JsonArray getJsonArray(){
        return this.jsonArray;
    }
    
    public boolean isJsonArray(){
        if(this.jsonArray != null){
            if(!this.jsonArray.isEmpty())
                return true;
        }
        return false;
    }
    
    public boolean isJsonObject(){
        if (this.json != null) {
            if (!this.json.isEmpty())
                return true;
        }
        return false;
    }

    @Override 
    public String toString() {
        StringWriter stringWriter = new StringWriter();
        
//        if(isJsonObject()){
           Json.createWriter(stringWriter).write(this.json);
//        }
        
//        if(isJsonArray()){
//            Json.createWriter(stringWriter).writeArray(this.jsonArray);
//        }

        return stringWriter.toString();
    }
}
