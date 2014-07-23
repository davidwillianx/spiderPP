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
public class NoticeEncoder implements Encoder.Text<Notice>
{

    @Override
    public String encode(Notice notice) throws EncodeException {
       return Json.createObjectBuilder()
                    .add("type", notice.getType())
                    .add("message", notice.getMessage())
                    .build().toString();
    }

    @Override
    public void init(EndpointConfig config) {
        
    }

    @Override
    public void destroy() {
        
    }
    
}
