/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 
package models.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author smartphonne
 */
@Embeddable
public class EstimativaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_estoria")
    private int idEstoria;

    public EstimativaPK() {
    }

    public EstimativaPK(int id, int idEstoria) {
        this.id = id;
        this.idEstoria = idEstoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getIdEstoria() {
        return idEstoria;
    }

    public void setIdEstoria(int idEstoria) {
        this.idEstoria = idEstoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (int) idEstoria;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstimativaPK)) {
            return false;
        }
        EstimativaPK other = (EstimativaPK) object;
        if (this.id != other.id) {
            return false;
        }
        if (this.idEstoria != other.idEstoria) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.entities.EstimativaPK[ id=" + id + ", idEstoria=" + idEstoria + " ]";
    }
    
}
