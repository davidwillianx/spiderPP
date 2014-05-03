/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package socket;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author smp
 */
public class MessageEncoder implements Encoder.Text<ChatMessage>{

    @Override
    public String encode(ChatMessage chatMessage) throws EncodeException {
        return Json.createObjectBuilder()
                .add("id", chatMessage.getId())
                .add("message", chatMessage.getMessage())
                .add("author", chatMessage.getAuthor())
                .add("dateReceived", chatMessage.getDateReceived().toString())
                .build().toString();
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy(){
    }
    
}
