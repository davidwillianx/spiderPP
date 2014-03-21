/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import models.entities.Historia;
import models.persistence.HistoriaDao;

/**
 *
 * @author Bruno
 */
@ManagedBean
@ViewScoped
public class HistoriaBean {
    
    private Historia historia;
    private HistoriaDao historiaDao;
    
    public HistoriaBean()
    {
        this.historia = new Historia();
        this.historiaDao = new HistoriaDao();
    }
    
    public Historia gethistoria()
    {
        return this.historia;
    }
    
    public void saveHistory(Historia historia)
    {
        this.historiaDao.saveHistory(historia);
        this.historia = new Historia();
    }
}
