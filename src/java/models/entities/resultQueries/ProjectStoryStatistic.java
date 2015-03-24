/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.entities.resultQueries;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author davidwillianx
 */
public class ProjectStoryStatistic {
 
    private long summedRate;
    private String day;
    private double quantityOfStories;
    
    public ProjectStoryStatistic(long summedRate , Date day){
        this.summedRate = summedRate;
        this.day = this.parseDateToDMY(day);
        
        
    }
    
    public long getSummedRate(){
        return this.summedRate;
    }
    
    public String getDay(){
        return this.day;
    }
    
    private String parseDateToDMY(Date day){
        DateFormat  datePatterDMY = new SimpleDateFormat("yyyy-mm-dd");
        return datePatterDMY.format(day);
        
    }
    
    
}
