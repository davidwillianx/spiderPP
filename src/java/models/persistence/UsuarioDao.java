/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models.persistence;

import javax.persistence.EntityManager;
import models.entities.Usuario;
import libs.BuildHash;

/**
 *
 * @author smp
 */
public class UsuarioDao {
    
   private Dao dao;
   private EntityManager entityManager;
   
   private Dao getDao()
   {
       if(this.dao instanceof Dao )
           return this.dao;
       else return this.dao = new Dao();
   }
   
   public void save(Usuario usuario)
   {
     try{
         //DOCrypt
         this.entityManager = this.getDao().getEntityManager();
         this.entityManager.getTransaction().begin();
  
         //Create Hash  
         BuildHash buildHash = new BuildHash();
         String  hashSenha = buildHash.createHash(usuario.getSenha());
         usuario.setSenha(hashSenha);      
         
         this.entityManager.persist(usuario);
         this.entityManager.getTransaction().commit();
     }catch(Exception error)
     {
         this.entityManager.getTransaction().rollback();
     }
        this.entityManager.close();
        this.dao.close();   
   }   
   
   public boolean enableStatus (Usuario usuario)
   {
       try {
            this.entityManager = this.getDao().getEntityManager();
            this.entityManager.getTransaction().begin();
           
            Usuario usuarioHashMail = (Usuario) entityManager.createNamedQuery("Usuario.findByMail")
                                .setParameter("hashmail", usuario.getHashmail())
                                .getSingleResult();
            
            entityManager.merge(usuarioHashMail);
            usuarioHashMail.setStatus(true);
            this.entityManager.getTransaction().commit();
            return true;
            
       } catch (IllegalArgumentException | IllegalStateException error) {
            this.entityManager.getTransaction().rollback();
            return false;
       }finally {
            this.entityManager.close();
            this.dao.close();
       } 
   }
   
   public Usuario selectUsuarioByEmailAndSenha(Usuario usuario)
   {
       try{
           this.entityManager = this.getDao().getEntityManager();
          Usuario usuarioResponse = (Usuario) this.entityManager.createNamedQuery("Usuario.findByEmailAndSenha")
                               .setParameter("senha", usuario.getSenha())
                               .setParameter("email", usuario.getEmail())
                               .getSingleResult();
          
          return usuarioResponse;
                   
       }catch(Exception  error)
       {
           System.out.println("Algum problema ocorreu"+ error.getMessage());
           return null;
       }
   }
   
   public Usuario searchMail(String email) 
   {
   
       try {
       
           this.entityManager = this.getDao().getEntityManager();
           this.entityManager.getTransaction().begin();
           
           Usuario usuarioMail = (Usuario) this.entityManager.createNamedQuery("Usuario.findByEmail")
                                                       .setParameter("email", email)
                                                       .getSingleResult();
           
           this.entityManager.merge(usuarioMail);
           
           this.entityManager.getTransaction().commit();
           return usuarioMail;
       } catch (Exception e){
           
           this.entityManager.getTransaction().rollback();
           return null;
       } finally {
           
           this.entityManager.close();
           this.dao.close();
       }
       
       
   }
   
      public void mergeUsuario (Usuario usuario)
      {
       try {
          this.entityManager = this.getDao().getEntityManager();
          this.entityManager.getTransaction().begin();
           
            Usuario use = (Usuario) entityManager.createNamedQuery("Usuario.findByMail")
                                .setParameter("hashmail", usuario.getHashmail())
                                .getSingleResult();
          
           this.entityManager.merge(use);
           use.setSenha(usuario.getSenha());
           this.entityManager.getTransaction().commit();
           
       } catch (Exception error){
           System.out.println("Erro: " + error);
           this.entityManager.getTransaction().rollback();
           
       }
     }
     
}
