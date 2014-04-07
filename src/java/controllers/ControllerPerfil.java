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
import libs.exception.NotFoundException;
import models.ejbs.interfaces.IPerfil;
import models.entities.Perfil;

/**
 *
 * @author smartphonne
 */
@Named
@RequestScoped
public class ControllerPerfil {
    
    @EJB
    private IPerfil iPerfil;
    
    private List<Perfil> perfis;
    private BuildMessage buildMessage;
    
    public ControllerPerfil()
    {
        this.buildMessage = new BuildMessage();
    }
    
    public List<Perfil> getPerfis()
    {
      try{
           this.perfis  = this.iPerfil.selectAll();
           return this.perfis;
          
      }catch(NotFoundException error){
          this.buildMessage.addError(error.getMessage());
          return null;
      }   
    }
    
    
    
    
}
