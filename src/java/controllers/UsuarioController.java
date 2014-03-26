package controllers;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import libs.BuildMail;
import libs.BuildMessage;
import libs.Redirect;
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
    private Redirect redirect;
    
    public UsuarioController()
    {
        this.usuario = new Usuario();
        this.buildMessage = new BuildMessage();
        this.redirect = new Redirect();
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
           this.buildMessage.addInfo("Cadastro realizado com sucesso");
           this.usuario =  new Usuario();
          
       } catch (Exception e) {
           this.buildMessage.addError("Email já cadastrado");
           System.out.println("error: "+ e.getMessage());
           e.printStackTrace();  
       }
   }
    
    
    //TODO criar objeto Session
    public void authenticator(Usuario usuario)
    {
        this.buildMessage = new BuildMessage();
        this.redirect = new Redirect();
        
        try{
            
            this.usuario = this.iUsuario.findUsuarioByEmailAndSenha(usuario);
            
            if(this.usuario != null){
                this.sessionManager = new SessionManager();
                this.sessionManager.set("usuario", usuario);
                
                this.redirect.redirectTo("/user/index.xhtml");
                
            }else{
                buildMessage.addError("Email ou senha inválidos");
            }
            
        }catch(Exception error){
             this.buildMessage.addError("Email ou senha inválidos");
        }
    }

    
    //TODO modificar o redirect
    public void exit()
    {
        try{
            
            this.sessionManager = new SessionManager();
            this.sessionManager.remove("usuario");

            this.redirect.redirectTo("/index.xhtml");
            
        }catch(Exception error)
        {
            this.buildMessage.addError("Falha ao executar a operação");
        }
    }
}
