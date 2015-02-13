/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models.ejbs.interfaces;

import java.util.List;
import models.entities.Mensagem;

/**
 *
 * @author smartphonne
 */
public interface IMensagem {
    public void save(Mensagem mensagem);
//    public List<Mensagem> getMensagensByProjeto();
}
 