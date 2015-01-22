/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package socket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.websocket.EncodeException;
import javax.websocket.Session;

/**
 *
 * @author smartphonnee
 */
public class Game {
    
    private String hashRoom;
    private int idProjeto;
    private List<Participant> participants;
    private List<Card> cards;
    private boolean start;
    
    public Game(Participant participant)
    {
        this.participants = new ArrayList<>();
        this.cards = new ArrayList<>();
        this.idProjeto = participant.getIdProjeto();
        this.participants.add(participant);
        this.start =  false;
    }
    
    public int getIdProjeto()
    {
        return this.idProjeto;
    }
    
    public void addUser(Participant participant)
    {
         this.participants.add(participant);
    }
    
    public List<Participant> getParticipantes()
    {
        return this.participants;
    }
    
    public void removeUser(Participant participant){
        this.participants.remove(participant);
    }
    
    public void start()
    {
        this.start = true;
    }
    
    public void closeRound()
    {
        this.start = false;
    }
    
    public boolean isOpen(){
        return this.start == true;
    }

    public boolean isGameParticipant(Session session)
    {
        for(Participant participant : this.participants)
        {
            if(session.equals(participant.getSession()))
                return true;
        }
        return false;
    }
    
    
    public Participant getParticipant(Session session) {
        for (Participant participant : this.participants) {
            if (session.equals(participant.getSession())) {
                return participant;
            }
        }
        return null;
    }
    
    
    public void addCard(Message messageCard, Session session)
    {
        Card newCard = new Card(messageCard.getJson());
        boolean isOld = false;
        
        for (Card card : cards) {
            if(newCard.getParticipantSession().equals(session)){
                card.setValue(newCard.getValue());
                isOld = true;
            }
        } 
        
        if (!isOld) {
            cards.add(newCard);
        }
    }
    
    public void sendBroadcastMessageWithoutSender(Session senderSession, Message message) {
        try {
            for (Participant participant : this.getParticipantes()) {
                Session sessionParticipant = participant.getSession();
                if (!senderSession.equals(sessionParticipant)) {
                    sessionParticipant.getBasicRemote().sendObject(message);
                }
            }  

        } catch (IOException | EncodeException e) {
            System.err.println("Error : " + e.getMessage());
        }
    }
    
    public void sendBroadcastMessage(Session senderSession, Message message) {
        try {
            for (Participant participant : this.getParticipantes()) {
                Session sessionParticipant = participant.getSession();
                    sessionParticipant.getBasicRemote().sendObject(message);
            }

        } catch (IOException | EncodeException e) {
            
            System.err.println("Error : " + e.getMessage());
        }
    }
 
}