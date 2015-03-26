/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package libs.exception;

/**
 *
 * @author smp
 */
public class BusinessException extends RuntimeException{
    public BusinessException(String message, Throwable error)
    {
        super(message, error);
    }
}
