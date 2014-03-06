/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import models.persistence.UsuarioDao;
import models.entities.Usuario;

import javax.mail.*;
import javax.mail.internet.*;



/**
 *
 * @author smp
 */

@ManagedBean
public class UsuarioBean {
    
    private Usuario usuario;
    private UsuarioDao usuarioDao;
    
    public UsuarioBean()
    {
        this.usuario = new Usuario();
        this.usuarioDao =  new UsuarioDao();
    }
    
    public Usuario  getUsuario()
    {
        return this.usuario;
    }
    
    //Events
    
    public void save(Usuario usuario)
    {
//        this.usuarioDao.save(usuario);
        
       
        
        
        try {
                Properties properties = new Properties();
                properties.setProperty("mail.transport.protocol", "smtp");
                properties.setProperty("mail.host", "smtp.ufpa.br");
                properties.setProperty("mail.user", "david.lopes@icen.ufpa.br");
                properties.setProperty("mail.password", "spiderteste");
        
            Session sessionMail =  Session.getDefaultInstance(properties,null);
            Transport transportMail = sessionMail.getTransport();
            
            MimeMessage mimeMessage = new MimeMessage(sessionMail);
            mimeMessage.setSubject("testando envio de mensagens");
            mimeMessage.setContent("estamos testando essa bagaça", "text/plain");
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("davidcbsi@gmail.com"));
            
            transportMail.connect();
            transportMail.sendMessage(mimeMessage, mimeMessage.getRecipients(Message.RecipientType.TO));
            transportMail.close();
            System.err.println("Foi enviado!");
            
        } catch (Exception e) {
            System.out.println("error: "+ e.getMessage());
            e.printStackTrace();       
        }
 
        
            
         
        
        
        
        
    }
}
