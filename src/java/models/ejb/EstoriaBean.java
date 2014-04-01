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
    public List<Estoria> ListStory() {
        List<Estoria> estorias = null;
        try
        {
            Query query = entityManager.createQuery("Estoria.findById");
            estorias = query.getResultList();
        }catch(Exception e){
            e.printStackTrace();
        }
        return estorias;
    }

    @Override
    public Estoria selectStory(int id) {
        Estoria estoria = null;
        try
        {
            estoria = entityManager.find(Estoria.class, id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return estoria;
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
    public Estoria findEstoriaByIdProjeto(Estoria estoria) {
       try
        {
           Estoria storyFound = (Estoria) this.entityManager.createNamedQuery("Estoria.findByIdProjeto")
                .setParameter("nome",estoria.getNome())
                .setParameter("descricao",estoria.getDescricao())
                .setParameter("estimativa",estoria.getEstimativa())
                .setParameter("status",estoria.getStatus());   
                return storyFound;
        }catch(Exception e){
            return null;
        }
    }
    

    
}
