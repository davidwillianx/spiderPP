/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models.ejbs.interfaces;

import java.util.List;
import models.entities.Estimativa;

/**
 *
 * @author BlenoVale
 */
public interface IEstimativa {
    
     public Estimativa SelectEstimativaByIdEstoria (int idEstoria);
     public void persistEstimativa(int idEstoria, int score);
     public List<Estimativa> selectEstimativaByIdEstoria(int idEstoria);
}
