/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package libs;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import libs.exception.BuildHashException;
import sun.misc.BASE64Encoder;

/**
 *
 * @author smp
 */
public class BuildHash {
    
    
    /**@TODO falta ajustar a exception*/
    public String createHash(String hash)
    {
        try{        
             MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
             messageDigest.update(hash.getBytes());
             BASE64Encoder base64Enconder = new BASE64Encoder();
             String hashed = base64Enconder.encode(messageDigest.digest());
             
             return hashed;
            
        }catch( NoSuchAlgorithmException error)
        {
            System.out.println("sadasdsad");
            return "";
        }    
    }    
}
