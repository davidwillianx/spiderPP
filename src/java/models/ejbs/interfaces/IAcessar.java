/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models.ejbs.interfaces;

import models.entities.Acessar;
import models.entities.Perfil;

/**
 *
 * @author smp
 */
public interface IAcessar {
   void save(Acessar acessar, Perfil perfil);
}
