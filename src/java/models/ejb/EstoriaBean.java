/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.ejb;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
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

//    @EJB
//    private IEstoria iEstoria;

    @Override
    public void saveStoryBean(Estoria estoria) {
        try {
            this.sessionManager = new SessionManager();
            this.projeto = (Projeto) this.sessionManager.get("projeto");
            estoria.setProjeto(this.projeto);
            this.estoriaPK = new EstoriaPK(0, this.projeto.getId());
            estoria.setEstoriaPK(this.estoriaPK);
            this.entityManager.persist(estoria);
        } catch (Exception error) {
            System.err.println("xErrado em EstoriaBean" + error.getMessage());
            this.sessionContext.setRollbackOnly();
        }
    }

    @Override
    public boolean removeStory(Estoria estoria) {
        boolean success = false;
        try {
            estoria = entityManager.find(Estoria.class, estoria.getIdEstoria());
            entityManager.remove(estoria);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    @Override
    public boolean modifyStory(Estoria estoria) {
        boolean success = false;
        try {
            entityManager.merge(estoria);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;

    }

    @Override
    public List<Estoria> getEstorias() {
        try {
            this.sessionManager = new SessionManager();
            this.projeto = (Projeto) this.sessionManager.get("projeto");
            this.estorias = this.entityManager.createNamedQuery("Estoria.findByIdProjeto", Estoria.class)
                    .setParameter("idProjeto", this.projeto.getId())
                    .getResultList();
            return this.estorias;
        } catch (Exception error) {
            this.sessionContext.setRollbackOnly();
            return null;
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Estoria selectEstoriaById(int idEstoria) {
        try {
            this.estoria = this.entityManager.find(Estoria.class, idEstoria);
            return this.estoria;
        } catch (Exception error) {
            throw new BusinessException("Falha ao consultar estória");
        }
    }

    @Override
    public void updateEstoriaBean(Estoria estoria) {
        try {
            this.entityManager.merge(estoria);
        } catch (Exception error) {
            throw new NoPersistException("Falha na atualização da Estória");
        }
    }

}
