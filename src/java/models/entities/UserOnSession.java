/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models.entities;

import javax.websocket.Session;

/** 
 *
 * @author smartphonne
 */
public class UserOnSession {
 
    private Projeto projeto;
    private Session socketSession;
    
    public UserOnSession(Projeto projeto, Session session)
    {
        this.projeto = projeto;
        this.socketSession =  session;
    }
    
    
    public void setProjeto(Projeto projeto)
    {
        this.projeto = projeto;
    }
    
    //TODO trocar o type para hash
    public void setSocketSession(Session session)
    {
        this.socketSession = session;
    }
    
    public Projeto getProjeto()
    {
        return this.projeto;
    }
    
    public Session getSocketSession()
    {
        return this.socketSession;
    }
    
}
