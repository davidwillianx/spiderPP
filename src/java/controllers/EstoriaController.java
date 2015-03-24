/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
 
import java.math.BigDecimal;
import java.util.List; 
import javax.ejb.EJB; 
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import libs.BuildMessage; 
import libs.Redirect;
import models.ejbs.interfaces.IEstoria;
import models.entities.Estoria;
import models.entities.Projeto;
import models.entities.resultQueries.ProjectStoryStatistic;
 
/**
 *
 * @author Bruno and Bleno
 */
@Named
@RequestScoped
public class EstoriaController {

    private BuildMessage buildMessage;
    private Projeto projeto;
    private List<Estoria> estorias;
    private List<Estoria> subtasks;
    private List<Estoria> parentEstorias;
    
    private Redirect redirect;
    private Estoria estoria;
    private int idEstoria;
    private boolean  isHasSubtask;
 
    @EJB
    private IEstoria iEstoria;

    public EstoriaController() {
        this.buildMessage = new BuildMessage();
        this.estoria = new Estoria();
    }

    public Estoria getEstoria() {
        return this.estoria;
    }

    public Projeto getProjeto() {
        return this.projeto;
    }
    
    public int getIdEstoria()
    {
        return idEstoria;
    }

    public List<Estoria> getEstorias() {
        try {
            estorias =  this.iEstoria.selectEstorias();
            return estorias;
        } catch (Exception error) {
            return null;
        } 
    }
     
    public List<Estoria> getSubtasks(){
        try {
              return this.subtasks;
        } catch (Exception e) {
            return null;
        }
    }

    public void saveEstoria(Estoria estoria) {
        this.buildMessage = new BuildMessage();
        try {

            this.iEstoria.persistEstoria(estoria);
            this.buildMessage.addInfo("Estória cadastrada coom sucesso!");
            this.estoria = new Estoria();

        } catch (Exception error) {
            this.buildMessage.addError("Ocorreu um erro ao criar  a Estória.");
        }
    }

    public void deleteEstoria(Estoria estoria) {
        this.buildMessage = new BuildMessage();
        try {
            this.iEstoria.removeEstoria(estoria);
            this.buildMessage.addInfo("Estória removida com sucesso.");
        } catch (Exception error){
            this.buildMessage.addError("Aconteceu um erro ao remover Estória.");
        }
        
    }

    public void redirectEditarEstoria(Estoria estoria) {
        this.redirect = new Redirect();
        this.redirect.redirectTo("/projeto/editarestoria.xhtml?estoria=" + estoria.getEstoriaPK().getId());
    }
    
    public int showTotalEstimativaByEstorias (){
        return this.iEstoria.totalEstimativaProjeto();
    }
    
    public float showMeanEstimativasByEstoria (){
        return this.iEstoria.meanEstorias();
    } 
    
    //-----------------------------------
    
    
    public boolean isSubTask(){
        return isHasSubtask;
    }
     
    
    public boolean hasSubTask(int idEstoria) {
        try {
           return !this.getAllSubtaskByParentId(idEstoria).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
    
    
    public List<Estoria> getParentEstorias()
    {
        try {
            return iEstoria.selectParentEstorias();
        } catch (Exception e) {
            return null;
        }
    }
    
     
    public List<Estoria> getAllSubtaskByParentId(int idEstoria) {
        try {
            this.subtasks = iEstoria.selectAllChildren(idEstoria);
            return this.subtasks;
        } catch (Exception e) {
            return null;
        }
    }
    
    public double showEstoriaPercentageRated(int idProjeto){
        long numberOfRated = iEstoria.selectNumberOfRated(idProjeto);
        int numbeOfEstoria = iEstoria.selectEstorias().size();
        
        double ratedPercentage = (100  * numberOfRated )/numbeOfEstoria;
    
        
        return ratedPercentage;
    }
    
    public  JsonArray showSummedRatePerDays( int idProjeto ){
        try {
            List<ProjectStoryStatistic> ratesPerDays = iEstoria.selectSummedRatePerDay(idProjeto);
            
            JsonArrayBuilder summedRatesPerDay = Json.createArrayBuilder();
            
            for (ProjectStoryStatistic ratePerDay : ratesPerDays ){
                
                JsonObjectBuilder jsonSummedRate = Json.createObjectBuilder()
                                       .add("day", ratePerDay.getDay())
                                       .add("sumdRate", ratePerDay.getSummedRate());
                
                summedRatesPerDay.add(jsonSummedRate);
            }
            
            return summedRatesPerDay.build();
            
        } catch (Exception e) {
            return null ;//LOOOOGGGGG
        }
    }
    
    public JsonArray showJsonQuantityForEachRate(int idProjeto){
        try {
            List<Object[]> quantityForEachRate = iEstoria.selectQuantityOfRate(idProjeto);
            
            JsonArrayBuilder jsonQuantity = Json.createArrayBuilder();
            
            for (Object[] quantityRate : quantityForEachRate){
                JsonObjectBuilder jsonQuantiyRate = Json.createObjectBuilder().add("label", Integer.parseInt(quantityRate[0].toString())).add("value", Integer.parseInt(quantityRate[1].toString()));
                jsonQuantity.add(jsonQuantiyRate);
            }
            
            return jsonQuantity.build();
        } catch (Exception e) {
            return null;
        }
    }
}
