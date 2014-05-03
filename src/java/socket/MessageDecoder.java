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
public class MessageDecoder implements  Decoder.Text<ChatMessage>{

    @Override
    public ChatMessage decode(String dataReceived) throws DecodeException{

            ChatMessage chatMessage = new ChatMessage();

            JsonObject jsonReceived = Json.createReader(new StringReader(dataReceived)).readObject();
            
            chatMessage.setId(jsonReceived.getInt("id"));
            chatMessage.setAuthor(jsonReceived.getString("author"));
            chatMessage.setMessage(jsonReceived.getString("message"));
            chatMessage.setDateReceived(new Date());
            return chatMessage;
        
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
