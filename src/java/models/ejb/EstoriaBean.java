/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models.ejb;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import libs.exception.BusinessException;
import models.ejbs.interfaces.IEstoria;
import models.entities.Estoria;

/**
 *
 * @author Bruno
 */
@Stateless
public class EstoriaBean implements IEstoria{

    private List<Estoria> estorias;
    
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
                System.out.println("Errado em EstoriaBean"+Error);
                this.sessionContext.setRollbackOnly();
            }
    }

    @Override
    public boolean removeStory(Estoria estoria) {
       boolean success = false;
       try
       {
           estoria = entityManager.find(Estoria.class, estoria.getId());
           entityManager.remove(estoria);
           success = true;
       }catch(Exception e)
       {
           e.printStackTrace();
       }
       return success;
    }



    @Override
    public boolean modifyStory(Estoria estoria) {
        boolean success = false;
        try
        {
            entityManager.merge(estoria);
            success = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return success;
        
    }


    @Override
    public List<Estoria> getEstorias() {
        try
        {
            if (this.estorias == null)
            {
                this.estorias = this.entityManager.createNamedQuery("Estoria.findAllByIdProjeto", Estoria.class)
                        .getResultList();
            }
        return estorias;
        }catch(Exception error){
            throw new BusinessException("Falha ao listar Estórias");
        }
    }
    

     
}
