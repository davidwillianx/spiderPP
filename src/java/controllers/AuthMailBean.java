package controllers;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import models.ejbs.interfaces.IUsuario;
import models.entities.Usuario;



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
    private Usuario usuario;
    
    @EJB
    private IUsuario iUsuario;
    
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
        this.usuario = new Usuario();
        this.usuario.setHashmail(this.hashMail);
        
        this.isActiveted = this.iUsuario.enableStatus(this.usuario);
    }
}