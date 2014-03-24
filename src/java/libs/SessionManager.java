/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package libs;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author smartphonne
 */
public class SessionManager {
    
    private FacesContext facesContext;
    private ExternalContext externalContext;
    private HttpSession session;
    
      
    public SessionManager()
    {
        this.facesContext = FacesContext.getCurrentInstance();
        this.externalContext = facesContext.getExternalContext();
        this.session = (HttpSession) externalContext.getSession(false);
    }
    
    public void set(String key, Object value)
    {
        this.session.setAttribute(key, value);
    }
    
    public Object get(String key)
    {
        return  this.session.getAttribute(key);
    } 
    
    public void remove(String key)
    {
        this.session.removeAttribute(key);
    }
}
