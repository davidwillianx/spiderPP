/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models.ejb;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import libs.BuildHash;
import models.ejbs.interfaces.IUsuario;
import models.entities.Usuario;

/**
 *
 * @author smartphonne
 */

@Stateless
public class UsuarioBean implements IUsuario{
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Resource
    private SessionContext context;

    @Override
    public void save(Usuario usuario) {
        
        try {
            BuildHash buildHash = new BuildHash();
            usuario.setSenha(buildHash.createHash(usuario.getSenha()));
            entityManager.persist(usuario);
            
        } catch (Exception error) {
            this.context.setRollbackOnly();
        }
    }

    @Override
    public boolean enableStatus(Usuario usuario) {
        
        try{
            Usuario userFound = (Usuario)
                this.entityManager.createNamedQuery("Usuario.findByHashMail")
                                  .setParameter("hash", usuario.getHashmail())
                                  .getSingleResult();
            this.entityManager.merge(userFound);
            userFound.setStatus(true);
            
            return true;
        }catch(Exception error){
            context.setRollbackOnly();
            return false;
        }
    }

    @Override
    public Usuario findUsuarioByEmailAndSenha(Usuario usuario) {
        
        try{
            
             Usuario userFound = (Usuario) this.entityManager.createNamedQuery("Usuario.findByEmailAndSenha")
                      .setParameter("email", usuario.getEmail())
                      .setParameter("senha",usuario.getSenha())
                      .getSingleResult();
        
             return userFound;
            
        }catch(Exception error){
            return null;
        }
    }
    
}
