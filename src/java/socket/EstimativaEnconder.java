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
 * @author smartphonnee
 */
public class EstimativaEnconder implements Encoder.Text<Estimativa>{

    @Override
    public String encode(Estimativa estimativa) throws EncodeException {
        return Json.createObjectBuilder()
                   .add("type", estimativa.getType())
                   .add("storyId",estimativa.getStoryId())
                   .add("score", estimativa.getScore())
                   .build().toString();
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
    
}
