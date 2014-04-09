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
 * @author Bruno
 */
@Embeddable
public class JogarRodadaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_usuario")
    private int idUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_estoria")
    private int idEstoria;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_projeto_estoria")
    private int idProjetoEstoria;

    public JogarRodadaPK() {
    }

    public JogarRodadaPK(int id, int idUsuario, int idEstoria, int idProjetoEstoria) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idEstoria = idEstoria;
        this.idProjetoEstoria = idProjetoEstoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdEstoria() {
        return idEstoria;
    }

    public void setIdEstoria(int idEstoria) {
        this.idEstoria = idEstoria;
    }

    public int getIdProjetoEstoria() {
        return idProjetoEstoria;
    }

    public void setIdProjetoEstoria(int idProjetoEstoria) {
        this.idProjetoEstoria = idProjetoEstoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (int) idUsuario;
        hash += (int) idEstoria;
        hash += (int) idProjetoEstoria;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JogarRodadaPK)) {
            return false;
        }
        JogarRodadaPK other = (JogarRodadaPK) object;
        if (this.id != other.id) {
            return false;
        }
        if (this.idUsuario != other.idUsuario) {
            return false;
        }
        if (this.idEstoria != other.idEstoria) {
            return false;
        }
        if (this.idProjetoEstoria != other.idProjetoEstoria) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.entities.JogarRodadaPK[ id=" + id + ", idUsuario=" + idUsuario + ", idEstoria=" + idEstoria + ", idProjetoEstoria=" + idProjetoEstoria + " ]";
    }
    
}
