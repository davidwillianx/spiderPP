/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import libs.BuildMessage;
import libs.Redirect;
import models.ejbs.interfaces.IEstoria;
import models.entities.Estoria;

/**
 *
 * @author Bruno and Bleno
 */
@Named
@RequestScoped
public class UpdateEstoriaController {

    private Estoria estoria;
    private BuildMessage buildMessage;
    private Redirect redirect;
    private String id;

    @EJB
    private IEstoria iEstoria;

    public UpdateEstoriaController() {
        this.estoria = new Estoria();
        this.id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("estoria");
    }

    public Estoria getEstoria() {
        return this.estoria;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void viewEstoria() {
        this.buildMessage = new BuildMessage();
        try {
            this.estoria = this.iEstoria.selectEstoriaById(this.id);
        } catch (NumberFormatException error) {
            this.buildMessage.addError("Falha na operacao");
        }
    }

    public void editEstoria(Estoria estoria) {
        this.buildMessage = new BuildMessage();
        try {
            this.iEstoria.updateEstoria(estoria, this.id);
            this.buildMessage.addInfo("Estória Atualizada com Sucesso");
        } catch (NumberFormatException error) {
            this.buildMessage.addError("Falha na atualização das informações");
        }
    }
}
