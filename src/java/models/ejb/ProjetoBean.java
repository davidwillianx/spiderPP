package models.ejb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public void saveProjeto(Projeto projeto) {
        try {
            this.sessionManager = new SessionManager();
            projeto.setDataInicio(currentDate());
            this.entityManager.persist(projeto);
            this.perfil = this.iPerfil.findPerfil(PERFIL_SCRUM_MASTER);
            this.usuario = (Usuario) this.sessionManager.get("usuario");
            this.iAcessar.save(this.perfil, this.usuario, projeto);
            

        } catch (NoPersistException error) {
            throw new BusinessException("Falha ao salvar projeto");
        } catch (Exception error) {
            this.sessionContext.setRollbackOnly();
            throw new BusinessException("Falha ao salvar projeto");
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
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Projeto> selectProjetoByUsuario() {
        try {
            sessionManager = new SessionManager();
            usuario = (Usuario) sessionManager.get("usuario");
            this.projetos = this.entityManager.createNamedQuery("Projeto.findAllByUserId", Projeto.class)
                    .setParameter("id_usuario", usuario.getId())
                    .getResultList();

            return this.projetos;
        } catch (Exception error) {
            this.sessionContext.setRollbackOnly();
            return null;
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

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Projeto selectProjetoById(int idProjeto) {
        try {
            this.projeto = this.entityManager.find(Projeto.class, idProjeto);
            return this.projeto;
        } catch (Exception error) {
            throw new BusinessException("Falha na consulta do projeto");
        }
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public int selectProjetoUsuarioPerfil(int idProjeto, int idUsuario)
    {
        try{
            this.perfil = this.iPerfil.selectPerfilByIdUsuarioAndIdProjeto(idProjeto, idUsuario);
            return this.perfil.getId();
        }catch(BusinessException error)
        {
            System.out.println("Exception x");
            throw new BusinessException((error.getMessage()));
        }
    }

    @Override
    public void updateProjeto(Projeto projeto) {
        try{
            
            this.entityManager.merge(projeto);
                    
        }catch(Exception error)
        {
            throw new NoPersistException("Falha na atualização do projeto");
        }
    }
}
