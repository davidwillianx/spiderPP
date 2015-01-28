/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package socket;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
 
/**
 * 
 * @author smartphonnee 
 */
public class MessageEncoder implements Encoder.Text<Message>{
    
private String jsonEncoded;
    
    @Override
    public String encode(Message message) throws EncodeException {

        if (message.isJsonObject()) {
            this.jsonEncoded = message.getJson().toString();
        }

        if (message.isJsonArray()) {
            this.jsonEncoded = message.getJsonArray().toString();
        }

        return this.jsonEncoded;
    }


    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
    
}
