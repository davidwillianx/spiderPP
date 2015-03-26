/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models.ejbs.interfaces;

import java.util.List;
import models.entities.Perfil;

/**
 *
 * @author smp
 */
public interface IPerfil {
    Perfil findPerfil(int idPerfil);
    Perfil selectPerfilByIdUsuarioAndIdProjeto(int idProjeto, int idUsuario);
    Perfil selectUserProfileByProjctIdAndUserId(int projectId, int userId);
    List<Perfil> selectAll();
    
    
}
