/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package libs;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author smp
 */
public class BuildMail {
 
    private String message;
    private String subject;

    
    public void sendRegisterNotification(String address, String name, String hash)
    {
        
        this.subject = "Cadastro efetuado com sucesso!";
        this.message = "<html>"
            + "Olá "+ name + ", <br><br>Bem vindo a plataforma de estimativa de projetos SpiderPP.<br>"
            +" <br> Para efetivar o seu cadastro clique  no link abaixo: <br> <a style=  \"background-color:blue;\" href = \"http://localhost:8080/spider/faces/user/auth.xhtml?pkm="+hash+"\"> <p class=\"classname\">Clique aqui</p> </a>  </html>";
        
        this.sendMail(address, this.subject, this.message);
    }
    
    private void sendMail(String address, String subject, String msg)
    {
        try {
            HtmlEmail email = new HtmlEmail();
            email.setHostName("smtp.ufpa.br");
            email.setSmtpPort(25);
            email.setAuthenticator(new DefaultAuthenticator("david.lopes@icen.ufpa.br","spiderteste"));
            //email.setSSLOnConnect(); TODO verficar a possiblidade de uso SMTP
            email.setFrom("david.lopes@icen.ufpa.br");
            email.setSubject(subject);
            email.addTo(address);
            email.setHtmlMsg(msg);
            email.send();
            
        } catch (Exception error) {
            
            System.out.println("Email error: check your log file"+ error.getMessage());
        }
    }
   
}






