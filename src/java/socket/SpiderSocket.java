/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package socket;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import libs.SessionManager;
import models.entities.Projeto;
import models.entities.UserOnSession;

/**
 *
 * @author smartphonne
 */

@ServerEndpoint("/spiderSocketGame")
public class SpiderSocket implements Serializable{
    
    private UserOnSession userOnSession;
    
    private SessionManager  sessionManager;
    private Projeto projeto;
    
    private ArrayList<UserOnSession> usersOnGame = new ArrayList<UserOnSession>();
    
    @OnOpen
    public void onOpen(Session session) {
        
        
        
        sessionManager = new SessionManager();
        System.err.println("1 - criando ");
        projeto =  (Projeto)sessionManager.get("projeto");
        
        
        userOnSession = this.buildObjetoUserOnGame( projeto , session);
        
        usersOnGame.add(userOnSession);
        
    }
    
    @OnMessage
    public void onMessage(String message) {
        sessionManager = new SessionManager();
        projeto = (Projeto) sessionManager.get("projeto");
        
        System.err.println("Mesage incoming");
        
        try {

            for (UserOnSession user : usersOnGame) {
                if (user.getProjeto().getId() == projeto.getId()) {

                    Session session = user.getSocketSession();
                    session.getBasicRemote().sendText(message);
                }
            }

        } catch (IOException error) {
            System.err.println("Falha ao realizar operação");
        }

    }
    
    @OnClose
    public void onClose(Session session) {
        
           userOnSession = this.buildObjetoUserOnGame(
                            (Projeto)sessionManager.get("projeto"),
                            session);
           usersOnGame.remove(userOnSession);
        
    }
    
    private UserOnSession buildObjetoUserOnGame(Projeto projeto, Session session)
    {
        userOnSession = new UserOnSession(projeto,session);
        return userOnSession;
    }


   
}