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
import libs.exception.NoPersistException;
import models.ejbs.interfaces.IProjeto;
import models.entities.Projeto;

/**
 *
 * @author smartphonne
 */
@Named
@RequestScoped
public class UpdateProjetoController {
    
    
    private Projeto projeto;
    private SessionManager sessionManager;
    private BuildMessage buildMessage;
    
    
    @EJB
    private IProjeto iProjeto;
    
    public UpdateProjetoController()
    {
        this.sessionManager = new SessionManager();
        this.projeto = (Projeto) this.sessionManager.get("projeto");
    }
    
    public Projeto getProjeto()
    {
        return this.projeto;
    }
    
    public void updateProjeto(Projeto projeto)
    {
        this.buildMessage = new BuildMessage();
        
        try{
            this.iProjeto.updateProjeto(projeto);
            this.buildMessage.addInfo("Atualização realizada com sucesso");
            
        }catch(NoPersistException error)
        {
            this.buildMessage.addError("Falha na atualização das informações");
        }
        
    }
}
