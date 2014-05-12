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
import libs.exception.NotFoundException;
import models.ejbs.interfaces.IMensagem;
import models.ejbs.interfaces.IProjeto;
import models.ejbs.interfaces.IUsuario;
import models.entities.Mensagem;
import models.entities.Projeto;
import models.entities.Usuario;
import socket.ChatMessage;

/**
 *
 * @author smartphonne
 */

@Stateless
public class MensagemBean {

    @PersistenceContext
    private EntityManager entityManager;
    private Mensagem mensagem;
    private List<Mensagem> mensagens;
    
    
    public void save(ChatMessage message) {
        try {
            
            this.mensagem = new Mensagem(message.getIdProjeto() , message.getIdUsuario());
            this.mensagem.setDataRecebido(new Date());
            this.mensagem.setTexto(message.getMessage());
            this.mensagem.setAutor(message.getAuthor());
            
            
            entityManager.persist(this.mensagem);
            
        } catch (Exception error) {
            throw  new NoPersistException("Falha ao salvar mensagem "+error.getMessage());
        }
    }

    
    public List<Mensagem> getMensagensByProjeto(int idProjeto) {

        try {

            this.mensagens = entityManager.createNamedQuery("Mensagem.findByIdProjeto")
                    .setParameter("idProjeto", idProjeto)
                    .getResultList();

            return this.mensagens;

        } catch (Exception error) {
            throw new NotFoundException("Falha ao encontrar informaçoes");
        }
    }
    
    
    
    
}
