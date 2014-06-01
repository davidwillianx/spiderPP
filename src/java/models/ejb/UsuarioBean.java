
package models.ejb;

import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import libs.BuildHash;
import libs.BuildMail;
import libs.SessionManager;
import libs.exception.BusinessException;
import libs.exception.NoPersistException;
import libs.exception.NoRemoveException;
import libs.exception.NotFoundException;
import models.ejbs.interfaces.IAcessar;
import models.ejbs.interfaces.IPerfil;
import models.ejbs.interfaces.IUsuario;
import models.entities.Perfil;
import models.entities.Projeto;
import models.entities.Usuario;
import models.entities.resultQueries.TeamMembership;

/**
 *
 * @author smartphonne
 */

@Stateless
public class UsuarioBean implements IUsuario{
    
    private Usuario usuario;
    private Projeto projeto;
    private List<Usuario> usuarios;
    private int perfilSelected;
    
    private BuildMail buildMail;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Resource
    private SessionContext context;
    
    @EJB
    private IAcessar iAcessar;
    
    @EJB
    private IPerfil iPerfil;
    
    private BuildHash buildHash;
    
    private SessionManager sessionManager;

    @Override
    public void save(Usuario usuario) {
        
        try {
            usuario.setSenha(this.hash(usuario.getSenha()));
            usuario.setHashmail(this.hash(usuario.getEmail()));
            
            this.entityManager.persist(usuario);
            
        } catch (Exception error) {
            this.context.setRollbackOnly();
        }
    }

      /**
       *@TODO lancar exception
       */
    @Override
    public boolean enableStatus(Usuario usuario) {
        
        try{
            Usuario userFound = (Usuario)
                this.entityManager.createNamedQuery("Usuario.findByHashMail")
                                  .setParameter("hashmail", usuario.getHashmail())
                                  .getSingleResult();
            this.entityManager.merge(userFound);
            userFound.setStatus(true);
            return true;
            
        }catch(Exception error){ 
            context.setRollbackOnly();
            return false;
        }
    }

      /**
       *@TODO lancar exception
       */
    @Override
    public Usuario findUsuarioByEmailAndSenha(Usuario usuario) {
        
        try{
            usuario.setSenha(this.hash(usuario.getSenha()));
            
             Usuario userFound = (Usuario) this.entityManager.createNamedQuery("Usuario.findByEmailAndSenha")
                      .setParameter("email", usuario.getEmail())
                      .setParameter("senha",usuario.getSenha())
                      .getSingleResult();
        
             return userFound;
            
        }catch(Exception error){
            return null;
        }
    }

       /**
       *@TODO lancar exception
       */
    @Override
    public boolean updatePassword(Usuario usuario, String hashMail) {
       try{

           Usuario userFound = (Usuario) this.entityManager.createNamedQuery("Usuario.findByHashMail")
                          .setParameter("hashmail", hashMail)
                          .getSingleResult();
            
            this.entityManager.merge(userFound);
            userFound.setSenha(this.hash(usuario.getSenha()));
           return true;
           
       }catch(UnsupportedEncodingException error){
           //TODO Lançar um exception
           System.out.println("ErrorUpdatePassword: "+error.getMessage());
           this.context.setRollbackOnly();
           return false;
       }
    }
    
    private String hash(String string) throws UnsupportedEncodingException{
        this.buildHash = new BuildHash();
        return this.buildHash.createHash(string);
    }

      /**
     * @param usuario
       *@TODO lancar exception NoPersist pois já tem método para rollback
       */
    @Override
    public void updateUsuario(Usuario usuario) {
        try
        {
            usuario.setSenha(this.hash(usuario.getSenha()));
            this.entityManager.merge(usuario);
            
        }catch(UnsupportedEncodingException error)
        {
            this.context.setRollbackOnly();
        }
    }

    @Override
    public List<Usuario> selectUsuarioOutOfProjectById() {
        try{
            
            this.sessionManager = new SessionManager();
            this.projeto = (Projeto) this.sessionManager.get("projeto");
            
           this.usuarios = this.entityManager.createNamedQuery("Usuario.findUsuarioOutOfProjetoId")
                               .setParameter("id_projeto", projeto.getId())
                               .getResultList();
           
           return this.usuarios;
            
        }catch(Exception error){
            throw  new NotFoundException("Falha na consulta de usuários");
        }
    } 

    @Override
    public void insertUsuarioToProjetoByPerfil(Usuario usuario,int idPerfil) {
        try
        {
            this.sessionManager = new SessionManager();
            this.usuario = usuario;
            this.projeto = (Projeto) this.sessionManager.get("projeto");
            Perfil perfil = new Perfil();
            perfil.setId(idPerfil); 
            
            this.iAcessar.save(perfil, this.usuario, this.projeto);
         
        }catch(NoPersistException error){
            throw  new BusinessException("Falha na persistência");
        }catch(Exception error){
            throw new BusinessException("Falha na persistência");
        }
    }

//    @Override
//    public List<Usuario> selectUsuarioOfProjeto() 
//    {
//        try{
//            this.sessionManager = new SessionManager();
//            this.projeto =  (Projeto) this.sessionManager.get("projeto");
//            this.usuarios =  this.entityManager.createNamedQuery("Usuario.findUsuarioOfProjetoId")
//                              .setParameter("id_projeto", this.projeto.getId())
//                              .getResultList();
//            
//            return this.usuarios;
//                                
//        }catch(Exception error){
//            throw new NotFoundException("Falha ao executar a busca");
//        }
//    }
//    
    
    @Override
     public List<TeamMembership> selectUsuarioOfProjeto()
     {
         try{
             sessionManager = new SessionManager();
             projeto = (Projeto) sessionManager.get("projeto");
             
             return  entityManager.createNamedQuery("Usuario.findMembershipsOfProjeto")
                                   .setParameter("id_projeto", projeto.getId())
                                   .getResultList();
         
         }catch(Exception error){
             throw  new NotFoundException("Falha ao encontrar resultados");
         }
     }
    

    @Override
    public void removeUsuarioOfProjeto(int idUsuario) {
       
        try{
            
            this.sessionManager = new SessionManager();
            
            this.usuario = this.entityManager.find(Usuario.class, idUsuario);
            this.projeto = (Projeto) this.sessionManager.get("projeto");
            
            Perfil perfil = this.iPerfil.selectPerfilByIdUsuarioAndIdProjeto(projeto.getId(), idUsuario);
            
            this.iAcessar.remove(perfil.getId(), idUsuario, this.projeto.getId());
            
        }catch(NoRemoveException error)
        {
            throw new BusinessException("Falha na exclusão");
        }
        
        
    }

    @Override
    public void saveAndInviteOnProjeto(Usuario usuario, int idPerfil) {
        try{
            
            this.buildMail  = new BuildMail();
            this.sessionManager = new SessionManager();
            this.projeto = (Projeto) this.sessionManager.get("projeto");
            this.save(usuario);
            this.entityManager.flush();
            
            Perfil perfil = new Perfil(idPerfil);
            this.iAcessar.save(perfil, usuario, this.projeto);
            
            this.buildMail.sendRegisterAndInviteProjetoNotification(
                                                usuario.getEmail(),
                                                usuario.getNome(),
                                                this.hash(usuario.getEmail()));
            
        }catch(UnsupportedEncodingException error){
            
            throw new BusinessException("Falha na operação");
        }
    }

    @Override
    public Usuario selectUsuarioById(int idUsuario) {
        
        try{
            return   entityManager.find(Usuario.class, idUsuario);
        }catch(Exception error){
            throw new NotFoundException("Falha ao encontrar usario");
        }
    }
    
    
}
