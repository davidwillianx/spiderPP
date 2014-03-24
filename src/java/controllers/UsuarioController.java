package controllers;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import libs.BuildMail;
import libs.BuildMessage;
import libs.SessionManager;
import models.ejbs.interfaces.IUsuario;
import models.entities.Usuario;

/**
 *
 * @author smp
 */

@Named
@RequestScoped
public class UsuarioController {
    
    private Usuario usuario;
    
    
    @EJB
    private IUsuario iUsuario;
    
    private BuildMessage buildMessage;
    private BuildMail buildMail;
    private SessionManager sessionManager;
    
    public UsuarioController()
    {
        this.usuario = new Usuario();
    }
    
    public Usuario  getUsuario()
    {
        return this.usuario;
    }
    
    public void save(Usuario usuario)
   {
      this.buildMessage = new BuildMessage();
      
       try {
           
           this.iUsuario.save(usuario);
           this.buildMessage = new BuildMessage();
           this.buildMail =  new BuildMail();
           
           buildMail.sendRegisterNotification(
                                        usuario.getEmail()
                                       ,usuario.getNome()
                                       ,usuario.getHashmail()
                                       );
           this.buildMessage.addInfo();
           this.usuario =  new Usuario();
          
       } catch (Exception e) {
           this.buildMessage.addError("Email já cadastrado");
           System.out.println("error: "+ e.getMessage());
           e.printStackTrace();  
       }
   }
    
    
    //TODO criar objeto Session
    public String authenticator(Usuario usuario)
    {
        this.buildMessage = new BuildMessage();
        try{
            
            this.usuario = this.iUsuario.findUsuarioByEmailAndSenha(usuario);
            
            if(this.usuario != null){
                this.sessionManager = new SessionManager();
                this.sessionManager.set("usuario", usuario);
                
                return "/user/index.xhtml";
            }else{
                buildMessage.addError("Email ou senha inválidos");
                return null;
            }
            
        }catch(Exception error){
             this.buildMessage.addError("Email ou senha inválidos");
             return null;
        }
    }

    
    //TODO modificar o redirect
    public String exit()
    {
        this.sessionManager = new SessionManager();
        this.sessionManager.remove("usuario");
        
        return "./index.xhtml";
    }
}
