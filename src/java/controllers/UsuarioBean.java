/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import javax.faces.bean.ManagedBean;
import libs.BuildHash;
import libs.BuildMail;
import models.entities.Usuario;
import models.persistence.UsuarioDao;

/**
 *
 * @author smp
 */

@ManagedBean
public class UsuarioBean {
    
    private Usuario usuario;
    private UsuarioDao usuarioDao;
    
    public UsuarioBean()
    {
        this.usuario = new Usuario();
        this.usuarioDao =  new UsuarioDao();
    }
    
    public Usuario  getUsuario()
    {
        return this.usuario;
    }
    
    //Events
    
    public void save(Usuario usuario)
    {
        try {
              this.usuarioDao.save(usuario);
              
              BuildMail buildMail =  new BuildMail();
              BuildHash buildHash = new BuildHash();
              buildMail.sendRegisterNotification(usuario.getEmail(), usuario.getNome(),
                                                buildHash.createHash(usuario.getEmail()));
              
        } catch (Exception e) {
            System.out.println("error: "+ e.getMessage());
            e.printStackTrace();       
        }
    }
}
