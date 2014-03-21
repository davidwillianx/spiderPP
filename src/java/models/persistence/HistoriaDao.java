/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models.persistence;

import javax.persistence.EntityManager;
import models.entities.Historia;

/**
 *
 * @author Bruno
 */
public class HistoriaDao {

   private Dao dao;
   private EntityManager entityManager;
   
   private Dao getDao()
   {
       if(this.dao instanceof Dao )
           return this.dao;
       else return this.dao = new Dao();
   }
   
   public void saveHistory (Historia historia)
   {
       try{
            this.entityManager = this.getDao().getEntityManager();
            this.entityManager.getTransaction().begin();
            this.entityManager.persist(historia);
            this.entityManager.getTransaction().commit();
       }catch(Exception error)
       {
           this.entityManager.getTransaction().rollback();
       }    
            this.entityManager.close();
            this.dao.close();   
       }
}

 