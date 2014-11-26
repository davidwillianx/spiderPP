/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 
package models.ejb;

import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.EJB; 
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext; 
import libs.exception.BusinessException;
import libs.exception.NoPersistException;
import models.ejbs.interfaces.IEstimativa;
import models.ejbs.interfaces.IEstoria;
import models.entities.Estimativa; 
import models.entities.EstimativaPK; 
import models.entities.Estoria;
import models.entities.EstoriaPK;


/** 
 *
 * @author smp
 */
   
@Stateless
public class EstimativaBean implements IEstimativa{
    
    private Estimativa estimativa; 
    private Estoria estoria;
    
    @EJB
    IEstoria iEstoria;
    
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

    @Override
    public void persistEstimativa(int idEstoria, int pontuacao) {
        try {
            System.err.println(">>>>>>>>>>>>>>>>>> Score show what you got "+pontuacao);
            estoria =  iEstoria.selectEstoriaByIdS(idEstoria);
            estimativa = new Estimativa();
            estimativa.setData(new Date());
            estimativa.setPontuacao(pontuacao);
            estimativa.setEstimativaPK(new EstimativaPK(estoria.getEstoriaPK().getId()));
            entityManager.persist(estimativa);
                
        } catch (Exception e) {
            throw new NoPersistException("Falha ao relizar operação"); 
        }
    }
}
 