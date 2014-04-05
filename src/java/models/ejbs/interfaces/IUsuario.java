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

import java.util.List;
import models.entities.Usuario;

/**
 *
 * @author smartphonne
 */
public interface IUsuario {
    void save(Usuario usuario);
    boolean enableStatus(Usuario usuario);
    Usuario findUsuarioByEmailAndSenha(Usuario usuario);
    boolean updatePassword(Usuario usuario, String haString);
    void updateUsuario(Usuario usuario);
    public List<Usuario> selectUsuarioOutOfProjectById();
}
