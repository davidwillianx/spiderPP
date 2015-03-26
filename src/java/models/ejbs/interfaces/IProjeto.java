
package models.ejbs.interfaces;

import java.util.List;
import models.entities.Perfil;
import models.entities.Projeto;

/**
 *
 * @author BlenoVale
 */
public interface IProjeto {
    
    public List<Projeto> selectProjectByUserSession();
    public void removeProjeto (String id_pkm) throws Exception;
    public Projeto selectProjetoById(int idProjeto);
    
    public void updateProjeto(Projeto projeto);
    
    //Methods were reviwed
    
    public Projeto saveProjeto(Projeto projeto);
    public Perfil selectUserProjectProfile(int projectId , int userId);
}
