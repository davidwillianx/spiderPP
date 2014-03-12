/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

    import javax.faces.application.FacesMessage;
    import javax.faces.bean.ManagedBean;
    import javax.faces.context.FacesContext;
    import javax.faces.event.ActionEvent;


    /**
     *
     * @author Bruno
     */
    @ManagedBean
    public class MessagesController {

        public void addInfo(ActionEvent actionEvent) 
        {  
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Informação: ", "Para efetivar "
                    + "o cadastro, acesse o link de confirmação no seu e-mail"));  
        }  

    }


