/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package socket;

import java.util.Date;

/**
 *
 * @author smp
 */
public class ChatMessage {

    
    private  int  id;
    private String message;
    private String author;
    private Date dateReceived;
    
    
    public void setId(int id)
    {
        this.id = id;
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
    
    public int getId()
    {
        return this.id;
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
}
