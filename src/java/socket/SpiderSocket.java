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
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import libs.SessionManager;
import libs.exception.NoPersistException;
import models.ejb.MensagemBean;
import models.ejbs.interfaces.IMensagem;
import models.entities.Mensagem;
import models.entities.Usuario;

/**
 *
 * @author smartphonne
 */

@Singleton
@ServerEndpoint(value = "/spiderSocketGame/{room}", encoders ={MessageEncoder.class}, decoders = {MessageDecoder.class})
public class SpiderSocket implements Serializable{
    
      @Inject
      private MensagemBean mensagemBean;
      private String room;
      private Mensagem mensagem;
      private static final Set<Session> sessions = 
                           Collections.synchronizedSet(new HashSet<Session>()); 
//      @EJB
//      private IMensagem iMensagem;
      
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
            mensagemBean.save(message.getIdProjeto(),message.getIdUsuario(),message.getMessage());
            
            for (Session session : sessions) {
                if(room.equals(session.getUserProperties().get("room")))
                    if(!senderSession.equals(session))
                         session.getBasicRemote().sendObject(message);
           }
       }catch(IOException | EncodeException | NoPersistException  error){
           System.err.println("Algo ocorreu "+error.getMessage());
       }
    }
    
    @OnClose
    public void onClose(Session session) {
           sessions.remove(session);
    }
}