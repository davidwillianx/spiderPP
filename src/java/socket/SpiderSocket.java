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
import java.util.List;
import java.util.Set;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import libs.exception.NoPersistException;
import libs.exception.NotFoundException;
import models.ejb.MensagemBean;
import models.entities.Mensagem;

/**
 *
 * @author smartphonne
 */
@Singleton
@ServerEndpoint(value = "/spiderSocketGame/{room}",
        encoders = {ChatMessageEncoder.class, GameStartEncoder.class, CardEnconder.class }, decoders = {MessageDecoder.class}
)
public class SpiderSocket implements Serializable {

    @Inject
    private MensagemBean mensagemBean;
    private List<Mensagem> mensagens;
    private static final Set<Session> sessions
            = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onOpen(Session session, @PathParam("room") String room) {

        try {
            session.getUserProperties().put("room", room);
            sessions.add(session);

            this.mensagens = mensagemBean.getMensagensByProjeto(Integer.parseInt(room));

            for (Mensagem mensagem : this.mensagens) {

                ChatMessage chatMessage = new ChatMessage(
                        Json.createObjectBuilder().add("author", mensagem.getUsuario().getNome())
                        .add("message", mensagem.getTexto())
                        .add("idProjeto", mensagem.getProjeto().getId())
                        .add("idUsuario", mensagem.getUsuario().getId())
                        .build()
                );
                session.getBasicRemote().sendObject(chatMessage);
                
            }

        } catch (NumberFormatException | IOException | EncodeException | NotFoundException error) {
            System.err.println("Error" + error.getMessage());
        }
    }

    @OnMessage
    public void onMessage(Session senderSession, Message message) {
        try {
            if (message instanceof ChatMessage) {
                this.sendChatMessage(senderSession, message);
            }

            if (message instanceof GameMessage) {

                GameMessage gameMessage = (GameMessage) message;
                String room = (String) senderSession.getUserProperties().get("room");

                for (Session session : sessions) {
                    if (room.equals(session.getUserProperties().get("room"))) {
                        session.getBasicRemote().sendObject(gameMessage);
                    }
                }
            }

            if (message instanceof Card) {
                Card  cardSelected = (Card) message;
                
                for(Session session : sessions)
                {
                   String room = (String ) senderSession.getUserProperties().get("room");
                   
                   if(room.equals(session.getUserProperties().get("room")))
                       if(!senderSession.equals(session)){
                            session.getBasicRemote().sendObject(cardSelected);
                       }
                }
            }

        } catch (IOException | EncodeException error) {
            System.err.println("Falha" + error.getMessage());
        }

    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }

    private void sendChatMessage(Session senderSession, Message message) throws IOException, EncodeException {
        ChatMessage chatMessage = (ChatMessage) message;
        mensagemBean.save(chatMessage);
        String room = (String) senderSession.getUserProperties().get("room");

        for (Session session : sessions) {
            if (room.equals(session.getUserProperties().get("room"))) {
                if (!senderSession.equals(session)) {
                    session.getBasicRemote().sendObject(chatMessage);
                }
            }
        }
    }

}
