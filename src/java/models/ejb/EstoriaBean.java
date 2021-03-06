/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.ejb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
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
import libs.exception.NotFoundException;
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
            estoria.setDataCriacao(currentDate());
            estoria.setStatus(Boolean.FALSE);
            this.entityManager.persist(estoria);

        } catch (Exception error) {
            this.sessionContext.setRollbackOnly();
            throw new NoPersistException("falha ao persistir estoria");
        }
    }
    
    @Override 
    public void persistSubtask(int idEstoria,Estoria subtask) {
        try {
            estoria = this.entityManager.createNamedQuery("Estoria.findById",Estoria.class)
                                .setParameter("id", idEstoria)
                                .getSingleResult();
            
            subtask.setEstoriaPK(new EstoriaPK(0,estoria.getProjeto().getId())); 
            subtask.setSubtask(estoria);
            this.entityManager.persist(subtask);  
            
            
        } catch (Exception e) {
            throw new NoPersistException("falha ao persistir subtask");
        } 
    }
    
    
    @Override
    public void persistSubtasks(int idEstoria, Collection<Estoria> subtasks) {
        try {
            Estoria rootEstoria = entityManager.createNamedQuery("Estoria.findById", Estoria.class)
                        .setParameter("id", idEstoria)
                        .getSingleResult();
            
            
            for(Estoria subtaks :  subtasks){
                subtaks.setSubtask(rootEstoria);
            }
            rootEstoria.setSubtasks(subtasks);
               
        } catch (Exception e) {
           throw new NoPersistException("Falha ao persistir estorias");
        }
    }


    
    
    @Override
    
    public void removeEstoria(Estoria estoria) {
        try {

            this.estoria = this.entityManager.merge(estoria);
            this.entityManager.remove(this.estoria);
        } catch (Exception error) {
            System.err.println("Erro em EstoriaBean-removeEstoria-->" + error.getMessage());
        }
    }

    /*TODO Evitar buscas constates quando o resultado não muda*/
    @Override
    public List<Estoria> selectEstorias() {
        try {
            this.sessionManager = new SessionManager();
            this.projeto = (Projeto) this.sessionManager.get("projeto");

            this.estorias = this.entityManager.createNamedQuery("Estoria.findByIdProjeto", Estoria.class)
                    .setParameter("idProjeto", this.projeto.getId())
                    .getResultList();
            

            if (this.estorias.isEmpty()) {
                System.err.println(">>> alright");
            }
            return this.estorias;
        } catch (Exception error) {
            this.sessionContext.setRollbackOnly();
            System.err.println("Erro em EstoriaBean-selectEstorias--> " + error.getMessage());
            return null;
        }
    }

    //TODO as classes realizam mesma operação, precisa mapear para refatorar
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
    public Estoria selectEstoriaByIdS(int idEstoria) {
        try {
            this.estoria = (Estoria) this.entityManager.createNamedQuery("Estoria.findById")
                    .setParameter("id", idEstoria)
                    .getSingleResult();
            return this.estoria;
        } catch (Exception error) { 
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
        } catch (NumberFormatException error) {
            System.err.println("Error em EstoriaBean-updateEstoria-->" + error.getMessage());
            throw new NoPersistException("Falha na atualização da Estória");
        }
    }

    //TODO adicionar em um lib
    public Date currentDate() throws ParseException {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); // posso usar outras mascaras para formatação
        return simpleDateFormat.parse(simpleDateFormat.format(date));

    }

   
  
    @Override 
    public List<Estoria> selectAllChildren(int idEstoria) {
        try {
            estorias =  entityManager.createNamedQuery("Estoria.findAllChildren", Estoria.class)
                                        .setParameter("id", idEstoria)
                                        .getResultList();
            return estorias;
        } catch (Exception e) {
            throw  new NotFoundException("fala ao realizar operação");
        } 
    }
    
    @Override
    public List<Estoria> selectParentEstorias()
    {
        try {
            return entityManager.createNamedQuery("Estoria.findAllParents").getResultList();
        } catch (Exception e) {
            throw new NotFoundException("Nenhum Resultado");
        }
    }
    
    
    
    
    
    
    
    
    
    //----------------------------- under supervision
    
     @Override
    public float meanEstorias() {
        try {
            float media = 0;
            if (this.selectEstorias().size() != 0) {
                media = totalEstimativaProjeto() / this.selectEstorias().size();
                System.err.println("---->MédiaEstorias: " + media);
            }
            return media;
        } catch (Exception error) {
            System.err.println("Error em EstoriaBean-updateEstoria-->" + error.getMessage());
            throw new NoPersistException("Falha no metodo que gera a media do projeto");
        }
    }
    
     @Override
    public int totalEstimativaProjeto() {
        try {
            int total = 0;
            this.estorias = selectEstorias();
            for (Estoria estoria1 : this.estorias) {
                this.estoria = estoria1;
                if (this.estoria.getEstimativa() != null) {
                    total = total + this.estoria.getEstimativa();
                }
            }
            System.err.println("---->TotalEstorias: " + total);
            return total;
        } catch (Exception error) {
            System.err.println("Error em EstoriaBean-updateEstoria-->" + error.getMessage());
            throw new NoPersistException("Falha no metodo que gera o total das estimativas");
        }
    }


    

   
    

}
