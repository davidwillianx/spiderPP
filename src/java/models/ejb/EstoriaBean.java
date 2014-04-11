/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.ejb;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import libs.SessionManager;
import libs.exception.BusinessException;
import libs.exception.NoPersistException;
import models.ejbs.interfaces.IEstoria;
import models.entities.Estoria;
import models.entities.EstoriaPK;
import models.entities.Projeto;

/**
 *
 * @author Bruno and Bleno
 */
@Stateless
public class EstoriaBean implements IEstoria {

    private List<Estoria> estorias;
    private SessionManager sessionManager;
    private Projeto projeto;
    private Estoria estoria;
    private EstoriaPK estoriaPK;

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    private SessionContext sessionContext;

    @Override
    public void persistEstoria(Estoria estoria) {
        try {
            this.sessionManager = new SessionManager();
            this.projeto = (Projeto) this.sessionManager.get("projeto");
            estoria.setProjeto(this.projeto);
            this.estoriaPK = new EstoriaPK(0, this.projeto.getId());
            estoria.setEstoriaPK(this.estoriaPK);
            this.entityManager.persist(estoria);
            
        } catch (Exception error) {
            this.sessionContext.setRollbackOnly();
        }
    }

    @Override
    public void removeEstoria(Estoria estoria) {
        try {
            
           this.estoria = this.entityManager.merge(estoria);
            this.entityManager.remove(this.estoria);
        } catch (Exception error)
        {
            System.err.println("Erro em EstoriaBean-removeEstoria-->" + error.getMessage());
        }
    }
    
    @Override
    public List<Estoria> selectEstorias() {
        try {
            this.sessionManager = new SessionManager();
            this.projeto = (Projeto) this.sessionManager.get("projeto");
            this.estorias = this.entityManager.createNamedQuery("Estoria.findByIdProjeto", Estoria.class)
                                              .setParameter("idProjeto", this.projeto.getId())
                                              .getResultList();
            return this.estorias;
        } catch (Exception error) {
            this.sessionContext.setRollbackOnly();
            System.err.println("Erro em EstoriaBean-selectEstorias--> " + error.getMessage());
            return null;
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Estoria selectEstoriaById(String idEstoria) {
        try {
            this.estoria = (Estoria) this.entityManager.createNamedQuery("Estoria.findById")
                                                       .setParameter("id", Integer.parseInt(idEstoria))
                                                       .getSingleResult();
            return this.estoria;
        } catch (Exception error) {
            System.err.println("Error em EstoriaBean-selecEstoriaById-->" + error.getMessage());
            throw new BusinessException("Falha ao consultar estória");
        }
    }

    @Override
    public void updateEstoria(Estoria estoria, String id) {
        try {
            this.sessionManager = new SessionManager();
            this.projeto = (Projeto) this.sessionManager.get("projeto");
            this.estoriaPK = new EstoriaPK(Integer.parseInt(id), this.projeto.getId());
            estoria.setEstoriaPK(this.estoriaPK);
            
            System.err.println("id-->" + id);
            System.err.println("this.projeto.getId()-->" + this.projeto.getId());
            System.err.println("this.estoriaPK-->" + this.estoriaPK);
            this.entityManager.merge(estoria);
        } catch (Exception error) {
            System.err.println("Error em EstoriaBean-updateEstoria-->" + error.getMessage());
            throw new NoPersistException("Falha na atualização da Estória");
        }
    }

}
