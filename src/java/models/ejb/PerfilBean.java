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

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager; 
import javax.persistence.LockTimeoutException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;
import libs.exception.BusinessException;
import libs.exception.FindPerfilException;
import libs.exception.NotFoundException;
import models.ejbs.interfaces.IPerfil;
import models.entities.Perfil;


/**
 *
 * @author smp
 */
@Stateless
public class PerfilBean  implements  IPerfil{

    private static final Logger LOGGER =  Logger.getLogger(PerfilBean.class.getName());
    private Perfil perfil;
    private List<Perfil> perfis;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Resource
    private SessionContext sessionContext;

    
    @Override
    public Perfil selectUserProfileByProjctIdAndUserId(int projectId, int userId) {
        try {

            List<Perfil> userProfile = entityManager
                    .createNamedQuery("Perfil.findByProjectIdAndUserId")
                    .setParameter("id_projeto", projectId)
                    .setParameter("id_usuario", userId)
                    .setMaxResults(1).getResultList();

            return userProfile.get(0);

        } catch (IllegalArgumentException | QueryTimeoutException | TransactionRequiredException |
                PessimisticLockException | LockTimeoutException error) {

            LOGGER.logp(Level.WARNING, PerfilBean.class.getName(), "selectUserProfileByProjectIdAndUserId", error.getMessage());

            throw new FindPerfilException("Os valores para busca não resultam em "
                    + "  uma consulta válida ou o sistema não esta respondendo de maneira adequada", error);

        }
    }


    
    //--------------------------------------------------------------------------
    
    
        @Override
    public Perfil selectPerfilByIdUsuarioAndIdProjeto(int idProjeto, int idUsuario)
    {
        try{
            
            perfil = (Perfil)  entityManager.createNamedQuery("Perfil.findByProjectIdAndUserId")
                                .setParameter("id_projeto", idProjeto)             
                                .setParameter("id_usuario", idUsuario)
                                .getSingleResult();
            
            return perfil;
            
        }catch(Exception error)
        {
            
            throw new BusinessException("Falha ao buscar perfil do usuário", error);
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public Perfil findPerfil(int idPerfil) {
        try{
           
          
            return (Perfil)  this.entityManager.createNamedQuery("Perfil.findById")
                                    .setParameter("id", idPerfil)
                                    .getSingleResult();
          
        }catch(Exception error)
        {
           throw new NotFoundException("Perfil não encontrado", error);
        }
    }
    


    @Override
    public List<Perfil> selectAll() {
        try{
            this.perfis = this.entityManager.createNamedQuery("Perfil.findAll").getResultList();
            return this.perfis;
        }catch(Exception error){
            throw  new NotFoundException("Falha na busca dos perfis", error);
        }
    }

    
    
}
