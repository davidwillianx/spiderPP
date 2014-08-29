/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.OnClose; 
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import models.ejb.MensagemBean;
import models.entities.Mensagem;

/** 
 *
 * @author smartphonnee
 */
@ServerEndpoint(value = "/spiderSocketGame/{room}/{perfil}",
        encoders = {MessageEncoder.class}, decoders = {MessageDecoder.class}
)
public class GameSocket implements Serializable {

    @Inject
    private MensagemBean mensagemBean;
    private Game game;
    private static final List<Game> games = Collections.synchronizedList(new ArrayList<Game>());
    private Participant participant;

    @OnOpen
    public void onOpen(Session session, @PathParam("room") String room, @PathParam("perfil") String perfil) {
        try {
            participant = new Participant(room, perfil, session);
            if (participant.isScrumMaster()) {
                Game game = new Game(participant); 
                games.add(game);  
                this.loadPreviousMessage(session);
            } else {
                Game game = this.getGameByProject(participant.getIdProjeto());
                if (game instanceof Game) {
                    if (game.isOpen()) {
                        Message message = new Message(Json.createObjectBuilder().add("type", "gameLocked").build());
                        session.getBasicRemote().sendObject(message);
                    } 
                    game.addUser(participant);
                    this.loadPreviousMessage(session);
                } else {
                    this.OnClose(session);
                }
            }
        } catch (IOException | EncodeException e) {
            //Call OnError
            System.err.println("Do something" + e.getMessage());
        } 
    }   
    
    //BAM BAM BAM REFACTORY DETECTED PAY ATTENTION SR 
    @OnMessage
    public void OnMessage(Session session, Message message) {
        try {

              Game game  = this.getGame(session);
              
            if ("message".equals(message.getJson().getString("type"))) {
                mensagemBean.save(new ChatMessage(message.getJson()));
                game.sendBroadcastMessageWithoutSender(session, message);
            }   
  
            if ("gameStart".equals(message.getJson().getString("type"))) { 
                game.start();
                game.sendBroadcastMessage(session,message); 
            }
            
            if("cardSelected".equals(message.getJson().getString("type")))
            {
                //TODO pensando sobre sim ou não de armazenar esta informação no servidor
                //Game game = this.getGame(session);
                //game.addCard(message, session);
                game.sendBroadcastMessageWithoutSender(session, message);
            }
               
            if("gameUnlock".equals(message.getJson().getString("type")))
            {
                if(game.getParticipant(session).isScrumMaster()){
                        game.closeRound();
                        game.sendBroadcastMessageWithoutSender(session, message);
                }
            }

        } catch (Exception e) {
            //Call on error and close connection
        } 
    }

    @OnClose
    public void OnClose(Session session) {
        try {
            //Fechar a conexão/Remover do game/ redirecionar/
            //Verifica o que pode ser o método closeReason.
            session.close();
            System.err.println("Vai tomar DC");
        } catch (IOException ex) {
            Logger.getLogger(GameSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadPreviousMessage(Session session) throws IOException, EncodeException {

        List<Mensagem> messages = mensagemBean.getMensagensByProjeto(participant.getIdProjeto());
        for (Mensagem mensagem : messages) {
            
                 Message message = new Message(Json.createObjectBuilder().add("author", mensagem.getUsuario().getNome())
                    .add("message", mensagem.getTexto())
                    .add("idProjeto", mensagem.getProjeto().getId())
                    .add("idUsuario", mensagem.getUsuario().getId())
                    .add("type", "message")
                    .build()); 
            
            session.getBasicRemote().sendObject(message);
        }
    }

    private Game getGame(Session userSession) {
        for (Game game : games) {
            if (game.isGameParticipant(userSession)) {
                return game;
            }
        }
        //Possivelmente trocado por uma exception (NotFoundException)
        return null;
    }
    
    private Game getGameByProject(int idProjeto)
    {
         for (Game game : games) {
            if (game.getIdProjeto() == idProjeto) {
                return game;
            }
        }
        //Possivelmente trocado por uma exception (NotFoundException)
        return null;
    }
}
