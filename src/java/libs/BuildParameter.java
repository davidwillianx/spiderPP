/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package libs;

import javax.faces.context.FacesContext;

/**
 *
 * @author BlenoVale
 */
public class BuildParameter {
    
    public static String getRequestParameter (String Parameter)
    {
        return (String) FacesContext.getCurrentInstance().getExternalContext()
                                                         .getRequestParameterMap()
                                                         .get(Parameter);
    }
}
