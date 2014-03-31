
package models.ejb;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
    
    private Date dateProjeto;
    private List<Projeto> projetos;
    
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
            projeto.setDataInicio(currentDate());
            this.entityManager.persist(projeto);
        } catch (Exception error) {
            this.sessionContext.setRollbackOnly();
            System.out.println("Error: " + error);
            error.getStackTrace();
        }
    }
     
    public Date currentDate() throws ParseException
    { 
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); // posso usar outras mascaras para formatação
        this.dateProjeto = simpleDateFormat.parse(simpleDateFormat.format(date));
        System.out.println("Data Atual: " + this.dateProjeto);
        return this.dateProjeto;
    }
    
    @Override
    public List<Projeto> getProjetos ()
    {
        try {
            if (this.projetos == null) {
                this.projetos = this.entityManager.createNamedQuery("Projeto.findAll", Projeto.class)
                                                  .getResultList();
            }
            return this.projetos;
        } catch (Exception error){
            this.sessionContext.setRollbackOnly();
            System.out.println("Error: " + error);
            return null;
        }
    }

    @Override
    public Projeto mergeProjeto(Projeto projeto) throws Exception
    {
            this.entityManager.merge(projeto);
            return projeto;
    }
    
    @Override
    public void removeProjeto (String id_pkm) throws Exception
    {
        int id = Integer.parseInt(id_pkm);
        Projeto projetoFound = (Projeto) this.entityManager.createNamedQuery("Projeto.findById")
                                                           .setParameter("id", id)
                                                           .getSingleResult();
        this.entityManager.merge(projetoFound);
        this.entityManager.remove(projetoFound);
        
    }
    
}
