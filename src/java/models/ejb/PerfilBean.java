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
import libs.exception.BusinessException;
import libs.exception.NotFoundException;
import models.ejbs.interfaces.IPerfil;
import models.entities.Perfil;

/**
 *
 * @author smp
 */
@Stateless
public class PerfilBean  implements  IPerfil{

    
    private Perfil perfil;
    
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
    
    @Override
    public Perfil selectPerfilByIdUsuarioAndIdProjeto(int idProjeto, int idUsuario)
    {
        try{
            this.perfil = (Perfil) this.entityManager.createNamedQuery("Perfil.findByIdProjetoAndIdUsuario")
                                .setParameter("id_projeto", idProjeto)             
                                .setParameter("id_usuario", idUsuario)
                                .getSingleResult();
            
            return this.perfil;
            
        }catch(Exception error)
        {
            throw new BusinessException("Falha ao buscar perfil do usuário");
        }
    }
    
}
