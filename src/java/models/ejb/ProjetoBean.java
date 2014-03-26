
package models.ejb;


import java.util.Date;
import java.text.SimpleDateFormat;
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
            System.out.println("Error: " + error);
            error.getStackTrace();
        }
    }
     
    public String currentDate(){ 
        
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }
    
}
