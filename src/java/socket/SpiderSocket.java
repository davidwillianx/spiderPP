/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package socket;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author smartphonne
 */

@ServerEndpoint("/spiderSocketGame/{room}")
public class SpiderSocket implements Serializable{
    
      private String room;
      private static final Set<Session> sessions = 
                           Collections.synchronizedSet(new HashSet<Session>()); 
     /*
     *@TODO 
     *verificar sua sala e carregar as mensagens antigas
     *
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("room") String room) {
        session.getUserProperties().put("room", room);
        sessions.add(session);
    }
    
    @OnMessage
    public void onMessage(String message, Session senderSession) {
        room = (String) senderSession.getUserProperties().get("room");
        
        System.err.println("Mandando...");
       try{
            for (Session session : sessions) {
                if(room.equals(session.getUserProperties().get("room")))
                    if(!senderSession.equals(session))
                         session.getBasicRemote().sendText(message);
           }
       }catch(IOException error){
           System.err.println("Algo ocorreu");
       }
    }
    
    @OnClose
    public void onClose(Session session) {
           sessions.remove(session);
    }
}