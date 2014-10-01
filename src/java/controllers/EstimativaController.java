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
import libs.exception.NoPersistException;
import models.ejbs.interfaces.IEstimativa;
import models.entities.Estimativa;
import models.entities.Mensagem;

/**
 *
 * @author smp
 */
@Named
@RequestScoped
public class EstimativaController {
     
    private Estimativa estimativa;
    private int estimativaSelected;
    private  int[] estimativas = {0,1,2,3,5,8,13,20,40,100};
    
    @EJB
    private IEstimativa iEstimativa;
    
    
    public EstimativaController()
    {}
            
    
    public void setEstimativaSelected(int estimativaSelected)
    {
        this.estimativaSelected = estimativaSelected;
    }
 
    public int getEstimativaSelected()
    {
        return this.estimativaSelected;
    }
    
    public void registrarEstimativa(int idEstoria)
    {
        BuildMessage buildMessage = new BuildMessage();
        try{
            iEstimativa.persistEstimativa(idEstoria, estimativaSelected);
            buildMessage.addInfo("Estimativa realizada com sucesso!");
        }catch(NoPersistException error){
            buildMessage.addError("Falha ao realizar processo de estimativa");
        }
        
    }
    
    public int[] getEstimativas() 
    { 
        return estimativas;
    }
    
    public Estimativa giveEstimativa (int idEstoria){
        this.estimativa = this.iEstimativa.SelectEstimativaByIdEstoria(idEstoria);
        return this.estimativa;
    }
}
