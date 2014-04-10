package models.ejb;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import libs.exception.NoPersistException;
import libs.exception.NoRemoveException;
import models.ejbs.interfaces.IAcessar;
import models.entities.Acessar;
import models.entities.Perfil;
import models.entities.Projeto;
import models.entities.Usuario;

/**
 *
 * @author smp
 */
@Stateless
public class AcessarBean implements IAcessar{

    @PersistenceContext
    private EntityManager entityManager;
    
    @Resource
    private SessionContext sessionContext;
    
    private Acessar acessar;
    
    @Override
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void save(Perfil perfil,Usuario usuario, Projeto projeto)
    {
        try {
                this.acessar = new Acessar(perfil.getId(), projeto.getId(), usuario.getId());
                this.entityManager.persist(acessar);
                
        } catch (Exception error) {
            throw new NoPersistException("Falha na vincular");
        }
    }

    @Override
    public void remove(int idPerfil, int idUsuario, int idProjeto) {
       try{
           this.acessar = (Acessar) this.entityManager.createNamedQuery("Acessar.findByIdUsuarioAndIdProjeto")
                                .setParameter("id_projeto", idProjeto)
                                .setParameter("id_usuario", idUsuario)
                                .getSingleResult();
           this.entityManager.remove(this.acessar);
       }catch(Exception error)
       {
           throw new NoRemoveException("Falha na exclusão");
       }
    }
    
}
