/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models.ejb;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import models.ejbs.interfaces.IEstoria;
import models.entities.Estoria;

/**
 *
 * @author Bruno
 */
@Stateless
public class EstoriaBean implements IEstoria{

    @PersistenceContext
    private EntityManager entityManager;
    
    @Resource
    private SessionContext sessionContext;

    @Override
    public void saveStory(Estoria estoria) {
        try
        {
            this.entityManager.persist(estoria);
        }catch (Exception Error)
            {
                this.sessionContext.setRollbackOnly();
            }
    }
    
}
