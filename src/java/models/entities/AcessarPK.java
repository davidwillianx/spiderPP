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
public class AcessarPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_perfil")
    private int idPerfil;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_sala")
    private int idSala;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_usuario")
    private int idUsuario;

    public AcessarPK() {
    }

    public AcessarPK(int idPerfil, int idSala, int idUsuario) {
        this.idPerfil = idPerfil;
        this.idSala = idSala;
        this.idUsuario = idUsuario;
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPerfil;
        hash += (int) idSala;
        hash += (int) idUsuario;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AcessarPK)) {
            return false;
        }
        AcessarPK other = (AcessarPK) object;
        if (this.idPerfil != other.idPerfil) {
            return false;
        }
        if (this.idSala != other.idSala) {
            return false;
        }
        if (this.idUsuario != other.idUsuario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.entities.AcessarPK[ idPerfil=" + idPerfil + ", idSala=" + idSala + ", idUsuario=" + idUsuario + " ]";
    }
    
}
