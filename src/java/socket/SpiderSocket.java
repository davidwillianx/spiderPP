/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package socket;

import java.io.Serializable;
import javax.jms.Session;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author smp
 */

@ServerEndpoint("/spiderSocketGame")
public class SpiderSocket implements Serializable{

    @OnOpen
    public void onOpen(Session session) {
        System.err.println("Connectado");
    }

    @OnMessage
    public void onMessage(String message) {
    }

    @OnClose
    public void onClose(Session session) {
    }
    
    
    
    
}
