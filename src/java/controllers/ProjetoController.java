
package controllers;

import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
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
    
    // injeta o stateless
    @EJB
    private IProjeto iProjeto;
    
    public ProjetoController()
    {
        this.projeto = new Projeto();
    }

    public Projeto getProjeto ()
    {
        return this.projeto;
    }
    
    public void saveProjeto (Projeto projeto)
    {
        try {
            this.iProjeto.saveProjeto(projeto);
        } catch (Exception error){
            System.out.println("Ocorreu um erro: " + error);
            error.printStackTrace();
        } 
    }
}
