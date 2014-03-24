/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent; 
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author smartphonne
 */

@FacesValidator(value = "passvalidator")
public class PasswordValidator implements Validator{
    
    private String password;
    private UIInput psui;
    private String confPassword;
    

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        this.confPassword = (String) value;
        this.psui = (UIInput) context.getViewRoot().findComponent("formRegister:novasenha");
        this.password = (String) this.psui.getValue();
        
        if(!this.confPassword.equals(this.password)){
            FacesMessage message = new FacesMessage(
                                        FacesMessage.SEVERITY_ERROR
                                        ,"Valores não correspondem"
                                        ,"Valores não correspondem"
                                        );
            
            throw new ValidatorException(message);
        }
    }
    
}
