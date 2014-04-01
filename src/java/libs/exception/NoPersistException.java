/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package libs.exception;

import javax.ejb.ApplicationException;

/**
 *
 * @author smp
 */

@ApplicationException(rollback = true)
public class NoPersistException extends RuntimeException{
        
    public NoPersistException(String message)
    {
        super(message);
    }
}
