/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import libs.BuildMessage;
import libs.Redirect;
import libs.SessionManager;
import models.ejb.EstoriaBean;
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
    private EstoriaBean estoriaBean;
    private List<Estoria> estorias;
    private Redirect redirect;
    
    public EstoriaController()
    {
        this.buildMessage = new BuildMessage();
        this.estoria = new Estoria();
    }
    
    public Estoria getEstoria()
    {
        return this.estoria;
    }
    
    public Projeto getProjeto()
    {
        return this.projeto;
    }
    
    public List<Estoria> getEstorias()
    {
        try
        {
            return this.iEstoria.getEstorias();
        }catch (Exception error){
            System.out.println("Ocorreu um erro: " + error);
            error.printStackTrace();
            return null;
        }
    }
    
    
    public void newStory() throws IOException
    {
        this.redirect = new Redirect();
        this.redirect.redirectTo("/projeto/createstory.xhtml");
    }
    
    public void saveStory(Estoria estoria)
    {
        this.buildMessage = new BuildMessage(); 
        try
        {   
            
            this.iEstoria.saveStoryBean(estoria);
            this.buildMessage.addInfo("Estória criada");
        }catch(Exception e)
                {
                    
                    System.out.println("error-->: "  + e.getMessage());
                    this.buildMessage.addError("Ocorreu um erro ao tentar criar a estória");
                    e.printStackTrace();  
                }
    }
    
    public void removeStory()
    {
        FacesContext.getCurrentInstance();
        boolean result = estoriaBean.removeStory(estoria);
        
        if (result)
        {
            estoria = new Estoria();
            buildMessage.addInfo("Estória Removida com Sucesso");
        }else{
            buildMessage.addError("Falha ao deletar Estória");
        }
    }
    
    
   public void modifyStory()
   {
       FacesContext.getCurrentInstance();
       boolean result = estoriaBean.modifyStory(estoria);
       
       if(result){
           estoria = new Estoria();
           buildMessage.addInfo("Estória alterada com sucesso.");
       }else{
           buildMessage.addError("Falha ao alterar estória.");
       }
   }
}
