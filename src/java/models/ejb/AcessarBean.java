/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models.ejb;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import libs.exception.NoPersistException;
import models.ejbs.interfaces.IAcessar;
import models.entities.Acessar;
import models.entities.Perfil;
import models.entities.Projeto;
import models.entities.Usuario;

/**
 *
 * @author smp
 */
@Stateless
public class AcessarBean implements IAcessar{

    @PersistenceContext
    private EntityManager entityManager;
    
    @Resource
    private SessionContext sessionContext;
    
    private Acessar acessar;
    
    @Override
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void save(Perfil perfil,Usuario usuario, Projeto projeto)
    {
        try {
                this.entityManager.merge(perfil);
                this.acessar = new Acessar();
                this.acessar.setPerfil(perfil);
                this.acessar.setProjeto(projeto);
                this.acessar.setUsuario(usuario);
                
                this.entityManager.persist(acessar);
        } catch (Exception error) {
            throw new NoPersistException("Falha na vincular");
        }
    }
    
}
