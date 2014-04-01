package controllers;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import libs.BuildMessage;
import libs.Redirect;
import models.ejbs.interfaces.IProjeto;
import models.entities.Projeto;

/**
 *
 * @author BlenoVale
 */
//msm coisa que MenagedBean
@Named
@RequestScoped
public class ProjetoController {

    private Projeto projeto;
    private Redirect redirect;
    private BuildMessage buildMessage;
    private String pkm;

    // injeta o stateless
    @EJB
    private IProjeto iProjeto;

    public ProjetoController() {
        this.projeto = new Projeto();
        this.redirect = new Redirect();
        this.pkm = String.valueOf(FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap()
                .get("pkm"));
    }

    public void setPkm(String pkm) {
        this.pkm = pkm;
    }

    public String getPkm() {
        return this.pkm;
    }

    public Projeto getProjeto() {
        return this.projeto;
    }

    public void newProjeto() throws IOException {
        this.redirect = new Redirect();
        this.redirect.redirectTo("/user/projeto/cadastro_projeto.xhtml");
    }

    public void saveProjeto(Projeto projeto) {
        
        this.buildMessage = new BuildMessage();
        
        try {
            this.iProjeto.saveProjeto(projeto);
            this.buildMessage.addInfo("Projeto cadastrado com Sucesso");
        
        } catch (Exception error) {
            this.buildMessage.addError("Erro ao cadastrar Projeto.");
            System.out.println("Ocorreu um erro: " + error);
            error.printStackTrace();
        }
    }

    public List<Projeto> getProjetos() {
        try {
            return this.iProjeto.getProjetos();
        } catch (Exception error) {
            System.out.println("Ocorreu um erro: " + error);
            error.printStackTrace();
            return null;
        }
    }

    public void configProjeto(Projeto projeto) {
        this.buildMessage = new BuildMessage();

        try {
            this.projeto = projeto;
            this.iProjeto.mergeProjeto(this.projeto);
            this.redirect.redirectTo("/user/projeto/visualiza.xhtml?pkm=" + this.projeto.getId());
            System.out.println("Projeto:\nID:" + this.projeto.getId()
                    + "\nNome:" + this.projeto.getNome()
                    + "\nDescrição:" + this.projeto.getDescricao());
        } catch (Exception error) {
            System.out.println("Erro: " + error);
        }
    }

    public void removeProjeto(Projeto projeto) throws Exception {
        System.out.println("pkm:" + this.pkm);
        this.iProjeto.removeProjeto(this.pkm);
        this.redirect.redirectTo("/user/home.xhtml");
    }
}
