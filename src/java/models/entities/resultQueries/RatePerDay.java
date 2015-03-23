/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.entities.resultQueries;

import java.util.Date;

/**
 *
 * @author davidwillianx
 */
public class RatePerDay {
 
    private long summedRate;
    private Date day;
    
    public RatePerDay(long summedRate , Date day){
        this.summedRate = summedRate;
        this.day = day;
        
    }
    
    public void setSummedRate(int summedRate){
        this.summedRate = summedRate;
    }
    
    public void setDay(Date day){
        this.day = day;
    }
    
    
    public long getSummedRate(){
        return this.summedRate;
    }
    
    public Date getDay(){
        return this.day;
    }
}
