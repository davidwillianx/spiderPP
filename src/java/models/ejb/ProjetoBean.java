
package models.ejb;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import models.ejbs.interfaces.IProjeto;
import models.entities.Projeto;

/**
 *
 * @author BlenoVale
 */
@Stateless
public class ProjetoBean implements IProjeto{
    
    // cria uma conexão para fazer o crud 
    @PersistenceContext 
    private EntityManager entityManager;
    
    // da rollback quando acontece algum erro de transação
    @Resource
    private SessionContext sessionContext;
    
    @Override
    public void saveProjeto (Projeto projeto)
    {
        try {
            this.entityManager.persist(projeto);
        } catch (Exception error) {
            this.sessionContext.setRollbackOnly();
        }
    }
    
}
