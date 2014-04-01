package models.ejb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import libs.exception.NoPersistException;
import models.ejbs.interfaces.IPerfil;
import models.ejbs.interfaces.IProjeto;
import models.entities.Projeto;

/**
 *
 * @author BlenoVale
 */
@Stateless
public class ProjetoBean implements IProjeto {

    private Date dateProjeto;
    private List<Projeto> projetos;

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    private SessionContext sessionContext;
    
    
    private IPerfil iPerfil;
    

    @Override
    public void saveProjeto(Projeto projeto) {
        try {
            
            
            projeto.setDataInicio(currentDate());
            this.entityManager.persist(projeto);
            
            
        } catch (NoPersistException  error ) {
            System.out.println("Error: " + error);
            error.getStackTrace();
        }catch(Exception error)
        {
            this.sessionContext.setRollbackOnly();
            System.out.println("Error: " + error);
            error.getStackTrace();
        }
        
    }

    public Date currentDate() throws ParseException {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); // posso usar outras mascaras para formatação
        this.dateProjeto = simpleDateFormat.parse(simpleDateFormat.format(date));
        System.out.println("Data Atual: " + this.dateProjeto);
        return this.dateProjeto;
    }

    @Override
    public List<Projeto> getProjetos() {
        try {
            if (this.projetos == null) {
                this.projetos = this.entityManager.createNamedQuery("Projeto.findAll", Projeto.class)
                        .getResultList();
            }
            return this.projetos;
        } catch (Exception error) {
            this.sessionContext.setRollbackOnly();
            System.out.println("Error: " + error);
            return null;
        }
    }

    @Override
    public void mergeProjeto(Projeto projeto) {
        try {
            this.entityManager.merge(projeto);
            
        } catch (Exception error) {
            this.sessionContext.setRollbackOnly();
        }
    }

    @Override
    public void removeProjeto(String id_pkm) throws Exception {
        int id = Integer.parseInt(id_pkm);
        Projeto projetoFound = (Projeto) this.entityManager.createNamedQuery("Projeto.findById")
                .setParameter("id", id)
                .getSingleResult();
        this.entityManager.merge(projetoFound);
        this.entityManager.remove(projetoFound);

    }

}
