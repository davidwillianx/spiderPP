/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libs.exception;

/**
 *
 * @author davidwillianx
 */
public class FindPerfilException  extends NotFoundException{ 

    public FindPerfilException(String message, Throwable error) {
        super(message, error);
    }
    
}