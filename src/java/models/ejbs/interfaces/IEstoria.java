/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models.ejbs.interfaces;

import java.util.List;
import models.entities.Estoria;


/**
 *
 * @author Bruno and Bleno
 */

public interface IEstoria {
    
    public void persistEstoria(Estoria estoria);
    public void persistSubtask(int idEstoria,Estoria subtask);
    public void removeEstoria(Estoria estoria);
    public List<Estoria> selectEstorias();
    public Estoria selectEstoriaById(String id);
    public void updateEstoria(Estoria estoria, String id);
    public int totalEstimativaProjeto();
    public float meanEstorias ();
    public Estoria selectEstoriaByIdS(int idEstoria);
    public List<Estoria> selectAllChildren(int idEstoria);
    
    
    public List<Estoria> selectParentEstorias();
}
