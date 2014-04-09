/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import libs.BuildMessage;
import libs.SessionManager;
import models.ejbs.interfaces.IEstoria;
import models.entities.Estoria;
import models.entities.EstoriaPK;

/**
 *
 * @author Bruno
 */
@Named
@RequestScoped
public class UpdateEstoriaController {
    
    private Estoria estoria;
    private EstoriaPK estoriaPK;
    private SessionManager sessionManager;
    private BuildMessage buildMessage;
    
    @EJB
    private IEstoria iEstoria;
    
    public UpdateEstoriaController()
    {
        this.sessionManager = new SessionManager();
        this.estoria = (Estoria) this.sessionManager.get("estoria");
    }
    
    public Estoria getEstoria()
    {
        return this.estoria;
    }
    
    public void updateEstoria()
    {
        this.buildMessage = new BuildMessage();
        try
        {
            this.iEstoria.updateEstoriaBean(estoria);
            this.buildMessage.addInfo("Estória Atualizada com Sucesso");
        }catch(Exception error){
            this.buildMessage.addError("Falha na atualização das informações");
        }
    }
}
