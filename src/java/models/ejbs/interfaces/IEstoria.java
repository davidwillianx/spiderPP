/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models.ejbs.interfaces;

import java.util.List;
import javax.faces.event.ActionEvent;
import models.entities.Estoria;

/**
 *
 * @author Bruno
 */

public interface IEstoria {
    
    public void saveStory(Estoria estoria);
    public Estoria selectStory(int id);
    public boolean modifyStory(Estoria estoria);
    public boolean removeStory(Estoria estoria);
    public List<Estoria> ListStory();
    Estoria findEstoriaByIdProjeto(Estoria estoria);
}
