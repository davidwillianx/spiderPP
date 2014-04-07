
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
import libs.SessionManager;
import libs.exception.BusinessException;
import libs.exception.NoPersistException;
import models.ejbs.interfaces.IAcessar;
import models.ejbs.interfaces.IUsuario;
import models.entities.Perfil;
import models.entities.Projeto;
import models.entities.Usuario;

/**
 *
 * @author smartphonne
 */

@Stateless
public class UsuarioBean implements IUsuario{
    
    private Usuario usuario;
    private Projeto projeto;
    private List<Usuario> usuarios;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Resource
    private SessionContext context;
    
    @EJB
    private IAcessar iAcessar;
    
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

    @Override
    public boolean enableStatus(Usuario usuario) {
        
        try{
            Usuario userFound = (Usuario)
                this.entityManager.createNamedQuery("Usuario.findByHashMail")
                                  .setParameter("hashmail", usuario.getHashmail())
                                  .getSingleResult();
            this.entityManager.merge(userFound);
            userFound.setStatus(true);
            System.err.println("usuario: "+usuario.getNome());
            return true;
            
        }catch(Exception error){ 
            context.setRollbackOnly();
            System.out.println("Error"+error.getMessage());
            return false;
        }
    }

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

    @Override
    public boolean updatePassword(Usuario usuario, String hashMail) {
       try{

           Usuario userFound = (Usuario) this.entityManager.createNamedQuery("Usuario.findByHashMail")
                          .setParameter("hashmail", hashMail)
                          .getSingleResult();
            
            this.entityManager.merge(userFound);
            userFound.setSenha(this.hash(usuario.getSenha()));
           return true;
           
       }catch(Exception error){
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

    @Override
    public void updateUsuario(Usuario usuario) {
        try
        {
            usuario.setSenha(this.hash(usuario.getSenha()));
            this.entityManager.merge(usuario);
            
        }catch(Exception error)
        {
            this.context.setRollbackOnly();
        }
    }

    @Override
    public List<Usuario> selectUsuarioOutOfProjectById() {
        try{
            
            this.sessionManager = new SessionManager();
            Projeto projeto = (Projeto) this.sessionManager.get("projeto");
            
           this.usuarios = this.entityManager.createNamedQuery("Usuario.findUsuarioOutOfProjetoId")
                               .setParameter("id_projeto", projeto.getId())
                               .getResultList();
           
           return this.usuarios;
           
        }catch(Exception error){
            throw  new BusinessException("Falha na consulta de usuários");
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
}
