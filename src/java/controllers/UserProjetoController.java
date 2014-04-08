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

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import libs.BuildMessage;
import libs.exception.NoPersistException;
import models.ejbs.interfaces.IUsuario;
import models.entities.Perfil;
import models.entities.Usuario;

/**
 *
 * @author smartphonne
 */
@Named
@RequestScoped
public class UserProjetoController {

    private int perfilSelected;
    private Usuario usuario = new Usuario();
    private BuildMessage buildMessage;

    @EJB
    private IUsuario iUsuario;

    public UserProjetoController() {
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setPerfilSelected(int idPerfil) {
        this.perfilSelected = idPerfil;
    }

    public int getPerfilSelected() {
        return perfilSelected;
    }

    public void inviteUserProjetoWithPerfil(Usuario usuario) 
    {
        this.buildMessage = new BuildMessage();
        try {
    
            if (this.perfilSelected != 0) {
                this.iUsuario.insertUsuarioToProjetoByPerfil(usuario, this.perfilSelected);
                this.perfilSelected = 0;
                
                this.buildMessage.addInfo("Cadastro realizado com sucesso");
                
            }else this.buildMessage.addError("Selecione o papel");
        } catch (NoPersistException error) {
            this.buildMessage.addError("Falha na realização da operação");
        }
    }
}
