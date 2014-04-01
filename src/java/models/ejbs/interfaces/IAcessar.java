/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models.ejbs.interfaces;


import models.entities.Perfil;
import models.entities.Projeto;
import models.entities.Usuario;

/**
 *
 * @author smp
 */
public interface IAcessar {
   void save(Perfil perfil, Usuario usuario, Projeto projeto);
}
