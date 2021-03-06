
package models.ejbs.interfaces;

import java.util.List;
import models.entities.Projeto;

/**
 *
 * @author BlenoVale
 */
public interface IProjeto {
    public void saveProjeto (Projeto projeto);
    public List<Projeto> selectProjetoByUsuario();
    public void removeProjeto (String id_pkm) throws Exception;
    public Projeto selectProjetoById(int idProjeto);
    public int selectProjetoUsuarioPerfil(int idProjeto, int idUsuario);
    public void updateProjeto(Projeto projeto);
    
}
