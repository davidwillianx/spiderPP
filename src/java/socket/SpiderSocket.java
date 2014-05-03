/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package socket;

import static antlr.Utils.error;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import models.ejb.MensagemBean;
import models.ejbs.interfaces.IMensagem;
import models.entities.Mensagem;

/**
 *
 * @author smartphonne
 */

@ServerEndpoint(value = "/spiderSocketGame/{room}", encoders ={MessageEncoder.class}, decoders = {MessageDecoder.class})
public class SpiderSocket implements Serializable{
    
      private String room;
      Mensagem mensagem = new Mensagem();
      private static final Set<Session> sessions = 
                           Collections.synchronizedSet(new HashSet<Session>()); 
      @Inject
      private MensagemBean mensagemBean;
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
    public void onMessage(ChatMessage message, Session senderSession){
        room = (String) senderSession.getUserProperties().get("room");
        
        try{
          
            mensagem.setTexto(message.getMessage());
            
            mensagemBean.save(mensagem);
            System.err.println("sss");
            
            for (Session session : sessions) {
                if(room.equals(session.getUserProperties().get("room")))
                    if(!senderSession.equals(session))
                         session.getBasicRemote().sendObject(message);
           }
       }catch(IOException | EncodeException error){
           System.err.println("Algo ocorreu");
       }
    }
    
    @OnClose
    public void onClose(Session session) {
           sessions.remove(session);
    }
}