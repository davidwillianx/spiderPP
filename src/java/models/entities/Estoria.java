/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Bruno
 */
@Entity
@Table(name = "estoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estoria.findAll", query = "SELECT e FROM Estoria e"),
    @NamedQuery(name = "Estoria.findById", query = "SELECT e FROM Estoria e WHERE e.estoriaPK.id = :id"),
    @NamedQuery(name = "Estoria.findByIdProjeto", query = "SELECT e FROM Estoria e WHERE e.estoriaPK.idProjeto = :idProjeto"),
    @NamedQuery(name = "Estoria.findByNome", query = "SELECT e FROM Estoria e WHERE e.nome = :nome"),
    @NamedQuery(name = "Estoria.findByEstimativa", query = "SELECT e FROM Estoria e WHERE e.estimativa = :estimativa"),
    @NamedQuery(name = "Estoria.findByStatus", query = "SELECT e FROM Estoria e WHERE e.status = :status")})
public class Estoria implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EstoriaPK estoriaPK;
    @Size(max = 30)
    @Column(name = "nome")
    private String nome;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "estimativa")
    private Integer estimativa;
    @Column(name = "status")
    private Boolean status;
    @JoinColumn(name = "id_projeto", nullable = false , referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Projeto projeto;
    @OneToMany(mappedBy = "idEstoria")
    private Collection<Estoria> estoriaCollection;
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "id_estoria", referencedColumnName = "id", insertable = false, updatable = false),
        @JoinColumn(name = "id", referencedColumnName = "id",insertable = false, updatable = false)
    })
    private Estoria idEstoria;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estoria")
    private Collection<JogarRodada> jogarRodadaCollection;

    public Estoria() {
    }

    public Estoria(EstoriaPK estoriaPK) {
        this.estoriaPK = estoriaPK;
    }

    public Estoria(int id, int idProjeto) {
        this.estoriaPK = new EstoriaPK(id, idProjeto);
    }

    public EstoriaPK getEstoriaPK() {
        return estoriaPK;
    }

    public void setEstoriaPK(EstoriaPK estoriaPK) {
        this.estoriaPK = estoriaPK;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getEstimativa() {
        return estimativa;
    }

    public void setEstimativa(Integer estimativa) {
        this.estimativa = estimativa;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    @XmlTransient
    public Collection<Estoria> getEstoriaCollection() {
        return estoriaCollection;
    }

    public void setEstoriaCollection(Collection<Estoria> estoriaCollection) {
        this.estoriaCollection = estoriaCollection;
    }

    public Estoria getIdEstoria() {
        return idEstoria;
    }

    public void setIdEstoria(Estoria idEstoria) {
        this.idEstoria = idEstoria;
    }

    @XmlTransient
    public Collection<JogarRodada> getJogarRodadaCollection() {
        return jogarRodadaCollection;
    }

    public void setJogarRodadaCollection(Collection<JogarRodada> jogarRodadaCollection) {
        this.jogarRodadaCollection = jogarRodadaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estoriaPK != null ? estoriaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estoria)) {
            return false;
        }
        Estoria other = (Estoria) object;
        if ((this.estoriaPK == null && other.estoriaPK != null) || (this.estoriaPK != null && !this.estoriaPK.equals(other.estoriaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.entities.Estoria[ estoriaPK=" + estoriaPK + " ]";
    }
    
}
