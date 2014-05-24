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

/**
 *
 * @author smp
 */


public class EstimativaBean {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Resource
    private SessionContext sessionContext;
    
    
    
}
