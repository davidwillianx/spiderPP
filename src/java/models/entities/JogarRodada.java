/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author smartphonne
 */
@Entity
@Table(name = "jogar_rodada")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "JogarRodada.findAll", query = "SELECT j FROM JogarRodada j"),
    @NamedQuery(name = "JogarRodada.findById", query = "SELECT j FROM JogarRodada j WHERE j.jogarRodadaPK.id = :id"),
    @NamedQuery(name = "JogarRodada.findByIdUsuario", query = "SELECT j FROM JogarRodada j WHERE j.jogarRodadaPK.idUsuario = :idUsuario"),
    @NamedQuery(name = "JogarRodada.findByIdEstoria", query = "SELECT j FROM JogarRodada j WHERE j.jogarRodadaPK.idEstoria = :idEstoria"),
    @NamedQuery(name = "JogarRodada.findByEstimativa", query = "SELECT j FROM JogarRodada j WHERE j.estimativa = :estimativa")})
public class JogarRodada implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected JogarRodadaPK jogarRodadaPK;
    @Column(name = "estimativa")
    private Integer estimativa;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;
    @JoinColumn(name = "id_estoria", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Estoria estoria;

    public JogarRodada() {
    }

    public JogarRodada(JogarRodadaPK jogarRodadaPK) {
        this.jogarRodadaPK = jogarRodadaPK;
    }

    public JogarRodada(int id, int idUsuario, int idEstoria) {
        this.jogarRodadaPK = new JogarRodadaPK(id, idUsuario, idEstoria);
    }

    public JogarRodadaPK getJogarRodadaPK() {
        return jogarRodadaPK;
    }

    public void setJogarRodadaPK(JogarRodadaPK jogarRodadaPK) {
        this.jogarRodadaPK = jogarRodadaPK;
    }

    public Integer getEstimativa() {
        return estimativa;
    }

    public void setEstimativa(Integer estimativa) {
        this.estimativa = estimativa;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
        hash += (jogarRodadaPK != null ? jogarRodadaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JogarRodada)) {
            return false;
        }
        JogarRodada other = (JogarRodada) object;
        if ((this.jogarRodadaPK == null && other.jogarRodadaPK != null) || (this.jogarRodadaPK != null && !this.jogarRodadaPK.equals(other.jogarRodadaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.entities.JogarRodada[ jogarRodadaPK=" + jogarRodadaPK + " ]";
    }
    
}
