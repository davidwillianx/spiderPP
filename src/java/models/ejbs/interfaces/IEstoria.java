/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models.ejbs.interfaces;

import java.util.List;
import models.entities.Estoria;
import models.entities.EstoriaPK;


/**
 *
 * @author Bruno
 */

public interface IEstoria {
    
    public void saveStoryBean(Estoria estoria);
    public boolean modifyStory(Estoria estoria);
    public boolean removeStory(Estoria estoria);
    public List<Estoria> getEstorias();
    public Estoria selectEstoriaById(int id);
    public void updateEstoriaBean(Estoria estoria);
}
