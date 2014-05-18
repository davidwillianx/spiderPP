/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package socket;

import java.util.Date;
import javax.json.JsonObject;

/**
 *
 * @author smp
 */
public class ChatMessage extends Message{

    
    private final String type = "message";
    private String message;
    private String author;
    private Date dateReceived;
    private int idProjeto;
    private int idUsuario;

    public ChatMessage(JsonObject json) {
        this.setMessage(json.getString("message"));
        this.setAuthor(json.getString("author"));
        this.setIdProjeto(json.getInt("idProjeto"));
        this.setIdUsuario(json.getInt("idUsuario"));
        this.setDateReceived(new Date());
    }
    
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    public String getMessage()
    {
        return this.message;
    }
    
    public void setAuthor(String author)
    {
        this.author = author;
    }
    
    public void setIdProjeto(int idProjeto)
    {
      this.idProjeto = idProjeto;   
    }
    
    public void setIdUsuario(int idUsuario)
    {
      this.idUsuario = idUsuario;   
    }
    
    
    public String getAuthor()
    {
        return this.author;
    }
    
    public void setDateReceived(Date dateReceived)
    {
        this.dateReceived = dateReceived;
    }
    
    public Date getDateReceived()
    {
        return this.dateReceived;
    }
    
    public int getIdProjeto()
    {
        return this.idProjeto;
    }
    
    public int getIdUsuario()
    {
        return this.idUsuario;
    }
    
    public String getType()
    {
        return type;
    }
    
}
