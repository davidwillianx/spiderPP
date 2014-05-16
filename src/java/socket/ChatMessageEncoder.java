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

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author smartphonne
 */

public class ChatMessageEncoder implements Encoder.Text<ChatMessage>{

    @Override
    public String encode(ChatMessage chatMessage) throws EncodeException {
        return Json.createObjectBuilder()
                    .add("message", chatMessage.getMessage())
                    .add("author", chatMessage.getAuthor())
                    .add("dataReceived", chatMessage.getDateReceived().toString())
                    .add("idProjeto", chatMessage.getIdProjeto())
                    .add("idUsuario", chatMessage.getIdUsuario())
                    .build().toString();
    
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
    
}
