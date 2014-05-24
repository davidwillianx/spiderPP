/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import javax.inject.Named;
import models.entities.Estimativa;

/**
 *
 * @author smp
 */
@Named
public class EstimativaController {
    
    private Estimativa estimativa;
    private int estimativaSelected;
    private  int[] estimativas = {0,1,2,3,5,8,13,20,40,100};
    
    
    
    public void setEstimativaSelected(int estimativaSelected)
    {
        this.estimativaSelected = estimativaSelected;
    }

    public int getEstimativaSelected()
    {
        return this.estimativaSelected;
    }
    
    public void registrarEstimativa(int estimativaSelected)
    {
    }
    
    public int[] getEstimativas()
    {
        return estimativas;
    }
}
