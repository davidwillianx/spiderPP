package models.ejb;


import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.LockTimeoutException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;
import libs.SessionManager;
import libs.exception.BusinessException;
import libs.exception.FindPerfilException;
import libs.exception.FindProjectException;
import libs.exception.NoPersistException;
import libs.exception.NoPersistProjetoException;
import libs.exception.NotFoundProjetoException;
import models.ejbs.interfaces.IAcessar;
import models.ejbs.interfaces.IPerfil;
import models.ejbs.interfaces.IProjeto;
import models.entities.Perfil;
import models.entities.Projeto;
import models.entities.Usuario;

/**
 *
 * @author BlenoVale
 */
@Stateless
public class ProjetoBean implements IProjeto {

    private static final Logger LOGGER =  Logger.getLogger(ProjetoBean.class.getName());
    private Date dateProjeto;
    private List<Projeto> projetos;
    private Perfil perfil;
    private Usuario usuario;
    private SessionManager sessionManager;
    private Projeto projeto;

    private static final int PERFIL_SCRUM_MASTER = 1;

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    private SessionContext sessionContext;

    @EJB
    private IPerfil iPerfil;
    @EJB
    private IAcessar iAcessar;

    
    @Override
    public Projeto saveProjeto(Projeto projeto) {
        try {
        
            sessionManager = new SessionManager();
            
            projeto.setDataInicio(new Date());
            entityManager.persist(projeto);
            Perfil SMPerfil = iPerfil.findPerfil(PERFIL_SCRUM_MASTER);
            
            usuario = (Usuario) sessionManager.get("usuario");
            iAcessar.save(SMPerfil, usuario, projeto);

            return projeto;
        } catch (PersistenceException error) {
            sessionContext.setRollbackOnly();
            LOGGER.logp(Level.WARNING , ProjetoBean.class.getName(), "saveProjeto", error.getMessage());
            throw new NoPersistProjetoException("Falha ao salvar projeto", error);
        }
    }
    
    
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Projeto selectProjetoById(int idProjeto) {
        try {
              return entityManager.find(Projeto.class, idProjeto);

        } catch (IllegalArgumentException error) {
            throw new NotFoundProjetoException("Erro ao pesquisar com o valor requisitado", error);
        }
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Projeto> selectProjectByUserSession() {
        try {
            sessionManager = new SessionManager();
            usuario = (Usuario) sessionManager.get("usuario");
            projetos = entityManager
                      .createNamedQuery("Projeto.findAllByUserId", Projeto.class)
                      .setParameter("id_usuario", usuario.getId())
                      .getResultList();

            return projetos;
            
        } catch (QueryTimeoutException | TransactionRequiredException | 
                 PessimisticLockException | LockTimeoutException error) {
            
            this.sessionContext.setRollbackOnly();
            LOGGER.logp(Level.WARNING , ProjetoBean.class.getName(), "selectProjectUserSession", error.getMessage());
            throw  new FindProjectException("O processo esta demorado ou não consegue concluir a operação", error);
        }
    }

    @Override
    public Perfil selectUserProjectProfile(int projectId , int userId){
        return iPerfil.selectPerfilByIdUsuarioAndIdProjeto(projectId, userId);
    }
    
    // CLASS Undersupervision --------------------------------------------------------------------
    
    
    @Override
    public void removeProjeto(String id_pkm) throws Exception {
        int id = Integer.parseInt(id_pkm);
        Projeto projetoFound = (Projeto) this.entityManager.createNamedQuery("Projeto.findById")
                .setParameter("id", id)
                .getSingleResult();
        this.entityManager.merge(projetoFound);
        this.entityManager.remove(projetoFound);

    }

    
    
    @Override
    public void updateProjeto(Projeto projeto) {
        try{
            
            this.entityManager.merge(projeto);
                    
        }catch(Exception error)
        {
            throw new NoPersistException("Falha na atualização do projeto", error);
        }
    }
}
