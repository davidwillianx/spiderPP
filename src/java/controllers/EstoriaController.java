/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import libs.BuildMessage;
import libs.Redirect;
import libs.SessionManager;
import models.ejbs.interfaces.IEstoria;
import models.entities.Estoria;
import models.entities.Projeto;

/**
 *
 * @author Bruno and Bleno
 */
@Named
@RequestScoped
public class EstoriaController {

    private BuildMessage buildMessage;
    private SessionManager sessionManager;
    private Projeto projeto;
    private List<Estoria> estorias;
    private Redirect redirect;
    private Estoria estoria;
 
    @EJB
    private IEstoria iEstoria;

    public EstoriaController() {
        this.buildMessage = new BuildMessage();
        this.estoria = new Estoria();
    }

    public Estoria getEstoria() {
        return this.estoria;
    }

    public Projeto getProjeto() {
        return this.projeto;
    }

    public List<Estoria> getEstorias() {
        try {
            return this.iEstoria.selectEstorias();
        } catch (Exception error) {
            return null;
        }
    }

    public void saveEstoria(Estoria estoria) {
        this.buildMessage = new BuildMessage();
        try {

            this.iEstoria.persistEstoria(estoria);
            this.buildMessage.addInfo("Estória criada");

        } catch (Exception error) {
            this.buildMessage.addError("Ocorreu um erro ao criar  a Estória.");
        }
    }

    public void deleteEstoria(Estoria estoria) {
        this.buildMessage = new BuildMessage();
        try {
            this.iEstoria.removeEstoria(estoria);
            this.buildMessage.addInfo("Estória removida com sucesso.");
        } catch (Exception error){
            this.buildMessage.addError("Aconteceu um erro ao remover Estória.");
        }
        
    }

    public void redirectEditarEstoria(Estoria estoria) {
        this.redirect = new Redirect();
        this.redirect.redirectTo("/projeto/editarestoria.xhtml?estoria=" + estoria.getEstoriaPK().getId());
    }
}
