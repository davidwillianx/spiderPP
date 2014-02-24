/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;
import javax.faces.bean.ManagedBean;
import models.persistence.UsuarioDao;
import models.entities.Usuario;

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
    
    public void salvar(Usuario usuario)
    {
        this.usuarioDao.salvar(usuario);
    }
    
}
