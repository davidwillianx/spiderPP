package controllers;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import models.entities.Usuario;
import models.persistence.UsuarioDao;



/**
 *
 * @author smp
 */
@ManagedBean
@RequestScoped
public class AuthMailBean {
    
    @ManagedProperty(value="#{param.pkm}")
    private String hashMail;
    private boolean isActiveted;
    private UsuarioDao usuarioDao;
    private Usuario usuario;
    
    public void setHashMail(String hashMail)
    {
        this.hashMail = hashMail;
    }
    
    public String getHashMail()
    {
        return this.hashMail;
    }
 
    
    public boolean getIsActiveted()
    {
        return this.isActiveted;
    } 
    
    @PostConstruct
    public void init()
    {
        System.out.println(" hash : "+this.hashMail);
        this.usuario = new Usuario();
        this.usuario.setHashmail(this.hashMail);
        
        this.usuarioDao = new UsuarioDao();
        this.isActiveted = this.usuarioDao.enableStatus(this.usuario);
        
        
    }
}