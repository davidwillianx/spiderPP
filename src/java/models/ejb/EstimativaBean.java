/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models.ejb;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import libs.exception.BusinessException;
import models.ejbs.interfaces.IEstimativa;
import models.entities.Estimativa;


/**
 *
 * @author smp
 */


public class EstimativaBean implements IEstimativa{
    private Estimativa estimativa;
    private int idEstoria;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Resource
    private SessionContext sessionContext;
    
    @Override
    public Estimativa SelectEstimativaByIdEstoria (int idEstoria){  
        try {
            this.estimativa = (Estimativa) this.entityManager.createNamedQuery("Estimativa.findByIdEstoria")
                                                .setParameter("idEstoria", idEstoria)
                                                .getSingleResult();
            return this.estimativa;
        } catch (Exception error){
            System.err.println("Error em EstoriaBean-selecEstoriaById-->" + error.getMessage());
            throw new BusinessException("Falha ao consultar estimativa da estória");
        }
    }
    
}
