package controllers;

import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import libs.BuildMail;
import libs.BuildMessage;
import libs.Redirect;
import libs.SessionManager;
import libs.exception.BusinessException;
import libs.exception.NoPersistException;
import libs.exception.NotFoundException;
import models.ejbs.interfaces.IUsuario;
import models.entities.Usuario;
import models.entities.resultQueries.TeamMembership;

/**
 *
 * @author smp
 */

@Named
@RequestScoped
public class UsuarioController {
    
    private Usuario usuario;
    private List<Usuario> usuariosOutOfProjeto;
    private List<TeamMembership> usuariosOfProjeto;
    private int perfilSelected;
    
    
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
    
    public void setPerfilSelected(int perfilSelected)
    {
        this.perfilSelected = perfilSelected;
    }
                  
    public int getPerfilSelected()
    {
        return this.perfilSelected;
    }
    
    
    public List<Usuario> getUsuariosOutOfProjeto()
    {
        try {
            System.err.println("SSS");
            return this.iUsuario.selectUsuarioOutOfProjectById();
            
        } catch (NotFoundException error) {
            return null;
        }
    }
    
    public List<TeamMembership>getUsuarioOfProjeto()
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
    
    
    public void saveAndInviteUsuario(Usuario usuario)
    {
        this.buildMessage = new BuildMessage();
        try{
            this.iUsuario.saveAndInviteOnProjeto(usuario, this.perfilSelected);
            this.buildMessage.addInfo("Cadastro realizado com sucesso, um email foi enviado ao usuario");
            
            
        }catch(BusinessException | NoPersistException error){
            this.buildMessage.addError("Falha ao incluir usuario no projeto");
        }
    }
    
    
}
 