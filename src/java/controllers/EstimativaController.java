/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
 */ 
        
package controllers;     
   
import java.util.List;
import javax.ejb.EJB;    
import javax.enterprise.context.RequestScoped; 
import javax.inject.Named;  
import libs.BuildMessage;    
import libs.exception.NoPersistException;  
import models.ejbs.interfaces.IEstimativa;      
import models.entities.Estimativa;          
     
/**
 *      
 * @author smp  
 */
@Named
@RequestScoped
public class EstimativaController {
       
    private Estimativa estimativa;
    private int estimativaSelected;
    private final  int[] estimativas;
    private List<Estimativa> storyEstimativas;
    
     
    @EJB
    private IEstimativa iEstimativa;
    
    
    public EstimativaController(){
        this.estimativas = new int[]{0,1, 2, 3, 5, 8, 13, 20, 40, 100};
    }
                
    
    public void setEstimativaSelected(int estimativaSelected)
    {
        this.estimativaSelected = estimativaSelected;
    }
 
    public int getEstimativaSelected()
    {
        return this.estimativaSelected; 
    }
      
    public void registerEstimativa(int idEstoria) 
    {
        BuildMessage buildMessage = new BuildMessage();  
        try{
            iEstimativa.persistEstimativa(idEstoria, this.estimativaSelected);
            buildMessage.addInfo("Estimativa realizada com sucesso!");
        }catch(NoPersistException error){
            buildMessage.addError("Falha ao realizar processo de estimativa");
        }
        
    }
     
    public int[] getEstimativas() 
    { 
        return estimativas;
    }
    
    //Under Analysis
    public List<Estimativa> giveEstimativa (int idEstoria){
        this.storyEstimativas = this.iEstimativa.selectEstimativaByIdEstoria(idEstoria);
        return this.storyEstimativas; 
    }
    
    public Estimativa getEstimativa()
    {
        return this.estimativa;
    }
}
