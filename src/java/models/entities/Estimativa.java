/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author smartphonne
 */
@Entity
@Table(name = "estimativa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estimativa.findAll", query = "SELECT e FROM Estimativa e"),
    @NamedQuery(name = "Estimativa.findById", query = "SELECT e FROM Estimativa e WHERE e.estimativaPK.id = :id"),
    @NamedQuery(name = "Estimativa.findByIdEstoria", query = "SELECT e FROM Estimativa e WHERE e.estimativaPK.idEstoria = :idEstoria"),
    @NamedQuery(name = "Estimativa.findByData", query = "SELECT e FROM Estimativa e WHERE e.data = :data")})
public class Estimativa implements Serializable {
    
    @JoinColumns({
        @JoinColumn(name = "id_estoria", referencedColumnName = "id", insertable = false, updatable = false),
        @JoinColumn(name = "id", referencedColumnName = "id",insertable = false, updatable = false)
    })
    @ManyToOne(optional = false)
    private Estoria estoria;
   
    @Column(name = "pontuacao")
    private Integer pontuacao;
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    private static final long serialVersionUID = 1L;
     
    @EmbeddedId
    protected EstimativaPK estimativaPK;

    

    public Estimativa() {
    }

    public Estimativa(EstimativaPK estimativaPK) {
        this.estimativaPK = estimativaPK;
    }

    public Estimativa(int id, int idEstoria) {
        this.estimativaPK = new EstimativaPK(id,idEstoria);
    }

    public EstimativaPK getEstimativaPK() {
        return estimativaPK;
    }

    public void setEstimativaPK(EstimativaPK estimativaPK) {
        this.estimativaPK = estimativaPK;
    }



    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Estoria getEstoria() {
        return estoria;
    }
 
    public void setEstoria(Estoria estoria) {
        this.estoria = estoria;
    }
 
    @Override   
    public int hashCode() {
        int hash = 0;  
        hash += (estimativaPK != null ? estimativaPK.hashCode() : 0);
        return hash;
    } 
 
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estimativa)) {
            return false;
        }
        Estimativa other = (Estimativa) object;
        if ((this.estimativaPK == null && other.estimativaPK != null) || (this.estimativaPK != null && !this.estimativaPK.equals(other.estimativaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.entities.Estimativa[ estimativaPK=" + estimativaPK + " ]";
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }

}
