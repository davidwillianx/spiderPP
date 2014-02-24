/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author smp
 */
public class Dao {
    
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    
    
    /**
     *@TODO lançar exception
     */
    public Dao()
    {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("spider");
        this.entityManager = this.entityManagerFactory.createEntityManager();
    }
    
    public EntityManager getEntityManager()
    {
       return this.entityManager;
    }
    
    public void close()
    {
        this.entityManagerFactory.close();
    }
}
