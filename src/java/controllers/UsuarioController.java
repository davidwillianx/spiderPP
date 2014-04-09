package controllers;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import libs.BuildMail;
import libs.BuildMessage;
import libs.Redirect;
import libs.SessionManager;
import libs.exception.BusinessException;
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
    private List<Usuario> usuariosOutOfProjeto;
    private List<Usuario> usuariosOfProjeto;
    
    
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
    
    //@TODO falta tratar a exception lancada
    public List<Usuario> getUsuariosOutOfProjeto()
    {
        return this.iUsuario.selectUsuarioOutOfProjectById();
    }
    
    public List<Usuario>getUsuarioOfProjeto()
    {
       try{
           
           return this.iUsuario.selectUsuarioOfProjeto();
           
       }catch(BusinessException error){
           
           this.buildMessage = new BuildMessage();
           this.buildMessage.addError(error.getMessage());
           return null;
       }
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
                this.sessionManager.set("usuario", this.usuario);
                
                this.redirect.redirectTo("/user/index.xhtml");
                
            }else{
                buildMessage.addError("Email ou senha inválidos");
            }
            
        }catch(Exception error){
             this.buildMessage.addError("Email ou senha inválidos");
             
        }
    }
    
    public void edit(Usuario usuario)
    {
        this.usuario = usuario;
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
