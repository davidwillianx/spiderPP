/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package libs;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author smp
 */
public class Redirect {
    
    private FacesContext facesContext;
    private HttpServletRequest request;
    private  HttpServletResponse response;
    private static final Logger LOGGER  = Logger.getLogger(Redirect.class.getName());
    
    public Redirect()
    {
        this.facesContext = FacesContext.getCurrentInstance();
        this.response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        this.request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
    }
    
    public void redirectTo(String page) {
        try {

            response.sendRedirect(request.getContextPath() + page);

        } catch (IOException | IllegalStateException error) {
            LOGGER.logp(Level.WARNING, Redirect.class.getName(), "redirectTo", "Problema ao redirecionar");
        }
    }
}
