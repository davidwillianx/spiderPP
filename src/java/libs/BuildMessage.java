/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package libs;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


public class BuildMessage {
    
    public void addInfo(String message)
    {    
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", message));
    }
    
    public void addError(String message)
    {
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro:",message));
    }
}
