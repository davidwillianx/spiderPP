/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import libs.BuildMessage;
import models.ejbs.interfaces.IEstoria;
import models.entities.Estoria;

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
    
    
    public EstoriaController()
    {
        this.estoria = new Estoria();
    }
    
    public Estoria getEstoria()
    {
        return this.estoria;
    }
    
    public void saveStory(Estoria estoria)
    {
        this.buildMessage = new BuildMessage();
        try
        {
            this.iEstoria.saveStory(estoria);
            this.estoria = new Estoria();
        }catch(Exception e)
                {
                    System.out.println("error: "+ e.getMessage());
                    e.printStackTrace();  
                }
    }
    
    
}