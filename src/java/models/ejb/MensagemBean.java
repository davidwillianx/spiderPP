/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models.ejb;

import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import libs.SessionManager;
import libs.exception.NoPersistException;
import models.ejbs.interfaces.IMensagem;
import models.ejbs.interfaces.IProjeto;
import models.ejbs.interfaces.IUsuario;
import models.entities.Mensagem;
import models.entities.Projeto;
import models.entities.Usuario;

/**
 *
 * @author smartphonne
 */

@Stateless
public class MensagemBean {

    @PersistenceContext
    private EntityManager entityManager;
    
    @EJB
    private IProjeto iProjeto;
    @EJB
    private IUsuario iUsuario;
    
    private Projeto projeto;
    private Usuario usuario;
    private Mensagem mensagem;
    
    
    public void save(int idProjeto, int idUsuario, String message) {
        try {
              
            this.projeto = iProjeto.selectProjetoById(idProjeto);
            this.usuario = iUsuario.selectUsuarioById(idUsuario);
            System.err.println(" idP "+this.projeto.getNome()+ " idU"+this.usuario.getNome());
            
            this.mensagem = new Mensagem();
             this.mensagem.setUsuario(this.usuario);
             this.mensagem.setProjeto(this.projeto);
             this.mensagem.setDataRecebido(new Date());
             this.mensagem.setTexto(message);
            
            
            entityManager.persist( this.mensagem);
            
        } catch (Exception error) {
            
            throw  new NoPersistException("Falha ao salvar mensagem "+error.getMessage());
        }
    }

//    @Override
//    public List<Mensagem> getMensagensByProjeto() {
//    }
    
    
    
    
}
