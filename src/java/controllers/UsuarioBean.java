package controllers;

import java.io.UnsupportedEncodingException;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import libs.BuildHash;
import libs.BuildMail;
import libs.BuildMessage;
import models.entities.Usuario;
import models.persistence.UsuarioDao;

/**
 *
 * @author smp
 */

@ManagedBean
@RequestScoped
public class UsuarioBean {
    
    private Usuario usuario;
    private UsuarioDao usuarioDao;
    
    @ManagedProperty(value="#{param.pkm}")
    private String hashMail;
    
    public void setHashMail(String hashMail)
    {
        this.hashMail = hashMail;
    }
    
    public String getHashMail()
    {
        return this.hashMail;
    }
    
    public UsuarioBean()
    {
        this.usuario = new Usuario();
        this.usuarioDao =  new UsuarioDao();
    }
    
    public Usuario  getUsuario()
    {
        return this.usuario;
    }
    
    public void save(Usuario usuario)
   {
       BuildMessage buildMessage = new BuildMessage();
       try {
           
           BuildHash buildHash = new BuildHash();  
           String hashMail = buildHash.createHash(usuario.getEmail());
           usuario.setHashmail(hashMail);
           this.usuarioDao.save(usuario);
           
           BuildMail buildMail =  new BuildMail();
           buildMail.sendRegisterNotification(
                                        usuario.getEmail()
                                       ,usuario.getNome()
                                       ,hashMail
                                       );
           
           buildMessage.addInfo();
           
           this.usuario =  new Usuario();
          
       } catch (Exception e) {
           buildMessage.addError("Email já cadastrado");
           System.out.println("error: "+ e.getMessage());
           e.printStackTrace();  
       }
   }
    
    public String authenticator(Usuario usuario)
    {
        BuildMessage  buildMessage = new BuildMessage();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        try{
            BuildHash buildHash = new BuildHash();
            usuario.setSenha(buildHash.createHash(usuario.getSenha()));
            this.usuario = this.usuarioDao.selectUsuarioByEmailAndSenha(usuario);
            
            if(this.usuario != null){
                ExternalContext externalContext = facesContext.getExternalContext();
                HttpSession session = (HttpSession) externalContext.getSession(false);
                session.setAttribute("usuario", this.usuario);
                
                return "/user/inicio.xhtml";
                
            }else{
                buildMessage.addError("Email ou senha inválidos");
                return "/user/login.xhtml";
            }
            
                
        }catch(UnsupportedEncodingException error){
             buildMessage.addError("Email ou senha inválidos");
             return "/user/login.xhtml";
        }
    }
    
    public String exit()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);
        session.removeAttribute("usuario");
        return "login.xhtml";
    }
    
    public void searchKey(Usuario usuario)
    {
        try {
            Usuario use = this.usuarioDao.searchMail(usuario.getEmail());
       
            BuildMail buildMail = new BuildMail();
            buildMail.rescuesKey(use.getEmail(), use.getNome(), use.getHashmail());
            System.out.println("passou!!");
        } catch (Exception e){
            System.out.println("error:" + e);
        }

    }
    
    @PostConstruct
    public void init()
    {
        System.out.println("hashMail.....:" + this.hashMail);
        this.usuario.setHashmail(this.hashMail);
        System.out.println("hashMail2.....:" + this.usuario.getHashmail());
    }
    
    public void changeKey(Usuario usuario)
    {
       System.out.println("hashMail3.....:" + usuario.getHashmail());
       //usuario.setHashmail("2897ff22d81c406e67d6ae8d962edc54a175f82076b4fe6814e6dad156ed1e61");
       this.usuarioDao =  new UsuarioDao();
       this.usuarioDao.mergeUsuario(usuario);
    }
}
