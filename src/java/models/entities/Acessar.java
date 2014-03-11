/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models.entities;

import java.io.Serializable;
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
 * @author smp
 */
@Entity
@Table(name = "acessar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Acessar.findAll", query = "SELECT a FROM Acessar a"),
    @NamedQuery(name = "Acessar.findByIdPerfil", query = "SELECT a FROM Acessar a WHERE a.acessarPK.idPerfil = :idPerfil"),
    @NamedQuery(name = "Acessar.findByIdSala", query = "SELECT a FROM Acessar a WHERE a.acessarPK.idSala = :idSala"),
    @NamedQuery(name = "Acessar.findByIdUsuario", query = "SELECT a FROM Acessar a WHERE a.acessarPK.idUsuario = :idUsuario")})
public class Acessar implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AcessarPK acessarPK;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;
    @JoinColumn(name = "id_sala", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Sala sala;
    @JoinColumn(name = "id_perfil", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Perfil perfil;

    public Acessar() {
    }

    public Acessar(AcessarPK acessarPK) {
        this.acessarPK = acessarPK;
    }

    public Acessar(int idPerfil, int idSala, int idUsuario) {
        this.acessarPK = new AcessarPK(idPerfil, idSala, idUsuario);
    }

    public AcessarPK getAcessarPK() {
        return acessarPK;
    }

    public void setAcessarPK(AcessarPK acessarPK) {
        this.acessarPK = acessarPK;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (acessarPK != null ? acessarPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Acessar)) {
            return false;
        }
        Acessar other = (Acessar) object;
        if ((this.acessarPK == null && other.acessarPK != null) || (this.acessarPK != null && !this.acessarPK.equals(other.acessarPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.entities.Acessar[ acessarPK=" + acessarPK + " ]";
    }
    
}