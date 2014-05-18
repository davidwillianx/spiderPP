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
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/** 
 *
 * @author smartphonne
 */
public class GameStartEncoder implements Encoder.Text<GameMessage>
{

    @Override
    public String encode(GameMessage gameMessage) throws EncodeException {
        return Json.createObjectBuilder()
                    .add("start", gameMessage.getStartTime())
                    .add("type", "gameStart")
                    .build().toString();
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
    
}
