/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models.ejb;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import libs.SessionManager;
import libs.exception.BusinessException;
import models.ejbs.interfaces.IEstoria;
import models.entities.Estoria;
import models.entities.EstoriaPK;
import models.entities.Projeto;


/**
 *
 * @author Bruno
 */
@Stateless
public class EstoriaBean implements IEstoria{

    private List<Estoria> estorias;
    private SessionManager sessionManager;
    private Projeto projeto;
    private Estoria estoria;
    private EstoriaPK estoriaPK;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Resource
    private SessionContext sessionContext;
    
    @EJB
    private IEstoria iEstoria;
    

    @Override
    public void saveStoryBean(Estoria estoria) {
        try
        {
            this.sessionManager = new SessionManager();
            this.projeto = (Projeto) this.sessionManager.get("projeto");
            //this.entityManager.merge(this.projeto);            
            estoria.setProjeto(this.projeto);
            this.estoriaPK = new EstoriaPK(0,this.projeto.getId());
            estoria.setEstoriaPK(this.estoriaPK);
            System.err.println("$$$$$#####" + estoria.getProjeto().getId() + "               " + estoria.getEstoriaPK());
            this.entityManager.persist(estoria);
        }catch (Exception error)
            {
                System.out.println("Errado em EstoriaBean" + error.getMessage()); 
                this.sessionContext.setRollbackOnly();
            }
    }

    @Override
    public boolean removeStory(Estoria estoria) {
       boolean success = false;
       try
       {
           estoria = entityManager.find(Estoria.class, estoria.getIdEstoria());
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
                this.sessionManager = new SessionManager();
                this.projeto = (Projeto) this.sessionManager.get("Projeto");
                this.estorias = this.entityManager.createNamedQuery("Estoria.findAllByIdProjeto", Estoria.class)
                        .setParameter("id_projeto",this.projeto.getId())
                        .getResultList();
            }
        return this.estorias;
        }catch(Exception error){
            throw new BusinessException("Falha ao listar Estórias");
        }
    }
    

     
}