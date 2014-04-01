/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models.ejb;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import libs.exception.NoPersistException;
import models.ejbs.interfaces.IAcessar;
import models.ejbs.interfaces.IUsuario;
import models.entities.Acessar;
import models.entities.Perfil;

/**
 *
 * @author smp
 */
public class AcessarBean implements IAcessar{

    @PersistenceContext
    private EntityManager entityManager;
    
    @Resource
    private SessionContext sessionContext;
    
    @Override
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void save(Acessar acessar, Perfil perfil)
    {
        try {
            
                this.entityManager.merge(perfil);
                
                acessar.setPerfil(perfil);
                this.entityManager.persist(acessar);
        } catch (Exception error) {
            throw new NoPersistException("Falha na vincular");
        }
    }
    
}
