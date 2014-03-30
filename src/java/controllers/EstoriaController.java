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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import libs.BuildMessage;
import libs.SessionManager;
import models.ejbs.interfaces.IEstoria;
import models.entities.Estoria;
import models.entities.Projeto;

/**
 *
 * @author Bruno
 */
@Named
@RequestScoped
public class EstoriaController
{
    private Estoria estoria;
    
    @EJB
    private IEstoria iEstoria;
    private BuildMessage buildMessage;
    private SessionManager sessionManager;
    private Projeto projeto;
    
    
    public EstoriaController()
    {
        this.buildMessage = new BuildMessage();
        this.estoria = new Estoria();
        this.sessionManager = new SessionManager();
        this.sessionManager.set("projeto", this.projeto);
        Projeto projeto = new Projeto(1);
        this.estoria = new Estoria();
        this.estoria.setIdProjeto(projeto);
    }
    
    public Estoria getEstoria()
    {
        return this.estoria;
    }
    
    public Projeto getProjeto()
    {
        return this.projeto;
    }
    
    
    
    public void saveStory(Estoria estoria)
    {
        this.buildMessage = new BuildMessage(); 
        try
        {   
            this.iEstoria.saveStory(estoria);
            this.buildMessage.addInfo("Estória criada");
            this.estoria = new Estoria();
        }catch(Exception e)
                {
                    System.out.println("error: "+ e.getMessage());
                    this.buildMessage.addError("Ocorreu um erro ao tentar criar a estória");
                    e.printStackTrace();  
                }
    }
    
    
}
