/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.util.Date;
import javax.json.JsonObject;
import models.entities.EstoriaPK;
/**
 *
 * @author smartphonnee
 */
public class Estoria {
    private String name;
    private String description;
    private Date creationDate;
    private int idProjeto;
    
    public Estoria(String name, String description , Date creationDate){
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
    }
     
    public Estoria(JsonObject jsonStory){
      this.name = jsonStory.getString("name");
      this.description = jsonStory.getString("description");
      this.idProjeto = jsonStory.getInt("projectId");
      
    }

    public void setCurrentDate(){
        this.creationDate = new Date();
    }
    
    public models.entities.Estoria buildEstoriaEntity(){
        models.entities.Estoria estoriaEntity = new models.entities.Estoria();
        estoriaEntity.setNome(name);
        estoriaEntity.setDescricao(description);
        estoriaEntity.setDataCriacao(creationDate);  
        estoriaEntity.setEstoriaPK(new EstoriaPK(0, idProjeto));
        
        return estoriaEntity;
    }
    
    
}