/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models.ejbs.interfaces;

import models.entities.Usuario;

/**
 *
 * @author smartphonne
 */
public interface IUsuario {
    void save(Usuario usuario);
    boolean enableStatus(Usuario usuario);
    Usuario findUsuarioByEmailAndSenha(Usuario usuario);
}