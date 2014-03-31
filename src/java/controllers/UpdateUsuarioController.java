/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import libs.BuildMessage;
import libs.SessionManager;
import models.ejbs.interfaces.IUsuario;
import models.entities.Usuario;

/**
 *
 * @author smartphonne
 */
@Named
@RequestScoped
public class UpdateUsuarioController {
   
    private Usuario usuario;
    
    @EJB
    private IUsuario iUsuario;
    
    private SessionManager sessionManager;
    private BuildMessage buildMessage;
    
    public UpdateUsuarioController()
    {
        this.sessionManager = new SessionManager();
        this.usuario = (Usuario) this.sessionManager.get("usuario");
    }
    
    public Usuario getUsuario()
    {
        return this.usuario;
    }
    
    public void edit(Usuario usuario)
    {   
        this.buildMessage = new BuildMessage();
        this.sessionManager = new SessionManager();
        
        try
        {
            this.iUsuario.updateUsuario(usuario);
            this.sessionManager.set("usuario", usuario);
            this.buildMessage.addInfo("Edi&ccdeil;&atilde;o realizada com sucesso!");
        }
        catch(Exception error)
        {
            this.buildMessage.addError("Falha ao realizar a edi&ccedil;&atilde;o");
        }
    }
}
