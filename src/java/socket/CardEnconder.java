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
 * @author smartphonne
 */
public class CardEnconder implements Encoder.Text<Card>{

    @Override
    public String encode(Card card) throws EncodeException {
        return Json.createObjectBuilder()
                .add("value", card.getValue())
                .add("userNameOption", card.getUserNameOption())
                .add("type", "cardSelected")
                .build().toString();
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
    
}
