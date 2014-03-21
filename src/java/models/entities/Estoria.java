/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
 * @author smartphonne
 */
@Entity
@Table(name = "estoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estoria.findAll", query = "SELECT e FROM Estoria e"),
    @NamedQuery(name = "Estoria.findById", query = "SELECT e FROM Estoria e WHERE e.id = :id"),
    @NamedQuery(name = "Estoria.findByNome", query = "SELECT e FROM Estoria e WHERE e.nome = :nome"),
    @NamedQuery(name = "Estoria.findByEstimativa", query = "SELECT e FROM Estoria e WHERE e.estimativa = :estimativa"),
    @NamedQuery(name = "Estoria.findByStatus", query = "SELECT e FROM Estoria e WHERE e.status = :status")})
public class Estoria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
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
    @JoinColumn(name = "id_projeto", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Projeto idProjeto;
    @OneToMany(mappedBy = "idEstoria")
    private Collection<Estoria> estoriaCollection;
    @JoinColumn(name = "id_estoria", referencedColumnName = "id")
    @ManyToOne
    private Estoria idEstoria;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estoria")
    private Collection<JogarRodada> jogarRodadaCollection;

    public Estoria() {
    }

    public Estoria(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Projeto getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(Projeto idProjeto) {
        this.idProjeto = idProjeto;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estoria)) {
            return false;
        }
        Estoria other = (Estoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.entities.Estoria[ id=" + id + " ]";
    }
    
}
