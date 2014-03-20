/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package libs;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author smp
 */
public class BuildHash
{
    
    public String createHash(String hash) throws UnsupportedEncodingException
    {
        try{        
             MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
             messageDigest.update(hash.getBytes("UTF-8"));
             byte[] digest = messageDigest.digest();
             BigInteger bigInteger = new BigInteger(1, digest);
             String hashed = bigInteger.toString(16);
             System.out.println("Hash: " + hashed);
             
             return hashed;
            
        }catch( NoSuchAlgorithmException | UnsupportedEncodingException error)
        {
            return "";
        }    
    }
    }
       

