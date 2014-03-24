/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import models.ejbs.interfaces.IUsuario;
import models.entities.Usuario;

/**
 *
 * @author smartphonne
 */
@Named
@ViewScoped
public class UpdatePasswordBean implements Serializable{
    
    private Usuario usuario;
    
    private String hashMail;
    
    
    @EJB
    private IUsuario iUsuario;
    
    public UpdatePasswordBean()
    {
        this.usuario = new Usuario();
        this.hashMail = String.valueOf(
                    FacesContext.getCurrentInstance()
                    .getExternalContext().getRequestParameterMap()
                    .get("pkm"));
    }
    
    
    public Usuario getUsuario()
    {
        return this.usuario;
    }
    
    public void setHashMail(String hashMail)
    {
        this.hashMail = hashMail;
    }
    
    public String getHashMail()
    {
        return this.hashMail;
    }
    
    
    public void registerPassword(Usuario usuario)
    {
        try{
            this.iUsuario.updatePassword(usuario, this.hashMail);
        }catch(Exception error){
            System.out.println("Error:"+error.getMessage());
        }
    }
}
 