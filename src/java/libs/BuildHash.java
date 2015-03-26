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
 * @author DavidWillianx
 */
public class BuildHash {
    
    MessageDigest messageCreator;
    byte[] digestResult ;
    BigInteger dataHashed;
    String dataToHash;
       
    /*@param dataToHash*/
    public BuildHash(String dataToHash) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        this.dataToHash = dataToHash;
        this.messageCreator = MessageDigest.getInstance("SHA-256");
        this.messageCreator.update(dataToHash.getBytes("UTF-8"));
    }
    
    public String createHash() {
        this.digestResult = messageCreator.digest();
        this.dataHashed = new BigInteger(1, digestResult);
        return this.dataHashed.toString(16);
    }
    
    public String buildHashURLData(){
        String hashedData = this.createHash();
        return hashedData.concat(this.dataToHash);
    }
}
