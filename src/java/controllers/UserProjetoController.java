/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import models.entities.Perfil;
import models.entities.Usuario;

/**
 *
 * @author smartphonne
 */

@Named
@RequestScoped
public class UserProjetoController {
    
    private Perfil perfil;
    private Usuario usuario = new Usuario();
    
    public UserProjetoController()
    {
    }
    
    public void inveteUserProjetoWithPerfil()
    {
        System.err.println("Estamos aqui");
    } 
    
    public Usuario getUsuario()
    {
        return this.usuario;
    }
    
    public Perfil getPerfil()
    {
        return this.perfil;
    }
}
