/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
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
import libs.exception.NotFoundException;
import models.ejbs.interfaces.IPerfil;
import models.entities.Perfil;

/**
 *
 * @author smp
 */
@Stateless
public class PerfilBean  implements  IPerfil{

    @PersistenceContext
    private EntityManager entityManager;
    
    @Resource
    private SessionContext sessionContext;

    @Override
    public Perfil findPerfil(int idPerfil) {
        try{
            
            return (Perfil)  this.entityManager.createNamedQuery("Perfil.findById")
                                    .setParameter("id", idPerfil)
                                    .getSingleResult();
        }catch(Exception error)
        {
           throw new NotFoundException("Perfil não encontrado");
        }
    }
    
    
}
