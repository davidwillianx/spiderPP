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
import models.entities.resultQueries.TeamMembership;

/**
 *
 * @author smartphonne
 */
public interface IUsuario {
    void save(Usuario usuario);
    void saveAndInviteOnProjeto(Usuario usuario, int idPerfil);
    boolean enableStatus(Usuario usuario);
    Usuario findUsuarioByEmailAndSenha(Usuario usuario);
    boolean updatePassword(Usuario usuario, String haString);
    void updateUsuario(Usuario usuario);
    public List<Usuario> selectUsuarioOutOfProjectById();
//    public List<Usuario> selectUsuarioOfProjeto();
    public List<TeamMembership> selectUsuarioOfProjeto();
    public void insertUsuarioToProjetoByPerfil(Usuario usuario, int idPerfil);
    public void removeUsuarioOfProjeto(int idUsuario);
    public Usuario selectUsuarioById(int idUsuario);
}
