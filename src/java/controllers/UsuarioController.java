package controllers;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import libs.BuildMail;
import libs.BuildMessage;
import models.ejbs.interfaces.IUsuario;
import models.entities.Usuario;

/**
 *
 * @author smp
 */

@ManagedBean
public class UsuarioController {
    
    private Usuario usuario;
    
    
    @EJB
    private IUsuario iUsuario;
    
    private BuildMessage buildMessage;
    private BuildMail buildMail;
    
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
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        try{
            
            this.usuario = this.iUsuario.findUsuarioByEmailAndSenha(usuario);
            
            if(this.usuario != null){
                ExternalContext externalContext = facesContext.getExternalContext();
                HttpSession session = (HttpSession) externalContext.getSession(false);
                session.setAttribute("usuario", this.usuario);
                
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
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);
        session.removeAttribute("usuario");
        return "./index.xhtml";
    }
}
