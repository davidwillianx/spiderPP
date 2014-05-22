/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package socket;

import java.io.StringReader;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author smp
 */
public class MessageDecoder implements  Decoder.Text<Message>{

    @Override
    public Message decode(String dataReceived) throws DecodeException{

            Message message = null;
            
            JsonObject jsonReceived = Json.createReader(new StringReader(dataReceived)).readObject();
            
            switch(jsonReceived.getString("type"))
            {
                case "message" : 
                    message = new ChatMessage(jsonReceived);
                    break;
                case "gameStart" :
                    message = new GameMessage(jsonReceived);
                    break;  
                case "cardOption":
                   message = new Card(jsonReceived);
                   break;
            }
            
           return message;
    }


    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }
    
}
