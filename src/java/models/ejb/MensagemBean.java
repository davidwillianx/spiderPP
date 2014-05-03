/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models.ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import libs.SessionManager;
import models.ejbs.interfaces.IMensagem;
import models.entities.Mensagem;
import models.entities.Projeto;
import models.entities.Usuario;

/**
 *
 * @author smartphonne
 */

@Stateless
public class MensagemBean implements IMensagem{

    @PersistenceContext
    private EntityManager entityManager;
    
    private Mensagem mensagem;
    
    private SessionManager sessionManager;
    
    @Override
    public void save(Mensagem mensagem) {
        
        try {
            System.err.println("dddd");
            sessionManager = new SessionManager();
            mensagem = new Mensagem();
            mensagem.setProjeto( (Projeto) sessionManager.get("projeto") );
            mensagem.setUsuario((Usuario) sessionManager.get("usuario"));
            
            
            entityManager.persist(mensagem);
            
        } catch (Exception error) {
            System.err.println(" Falha: "+error.getMessage());
        }
    }

//    @Override
//    public List<Mensagem> getMensagensByProjeto() {
//    }
    
    
    
    
}
