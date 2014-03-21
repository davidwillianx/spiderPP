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
 * @author smp
 */
@Entity
@Table(name = "historia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Historia.findAll", query = "SELECT h FROM Historia h"),
    @NamedQuery(name = "Historia.findById", query = "SELECT h FROM Historia h WHERE h.id = :id"),
    @NamedQuery(name = "Historia.findByNome", query = "SELECT h FROM Historia h WHERE h.nome = :nome"),
    @NamedQuery(name = "Historia.findByEstimativa", query = "SELECT h FROM Historia h WHERE h.estimativa = :estimativa"),
    @NamedQuery(name = "Historia.findByStatus", query = "SELECT h FROM Historia h WHERE h.status = :status")})
public class Historia implements Serializable {
    @JoinColumn(name = "id_projeto", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Projeto idProjeto;
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
    @Lob
    @Size(max = 2147483647)
    @Column(name = "forum")
    private String forum;
    @Column(name = "status")
    private Boolean status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "historia")
    private Collection<JogarRodada> jogarRodadaCollection;
    @OneToMany(mappedBy = "idHistoria")
    private Collection<Historia> historiaCollection;
    @JoinColumn(name = "id_historia", referencedColumnName = "id")
    @ManyToOne
    private Historia idHistoria;

    public Historia() {
    }

    public Historia(Integer id) {
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

    public String getForum() {
        return forum;
    }

    public void setForum(String forum) {
        this.forum = forum;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<JogarRodada> getJogarRodadaCollection() {
        return jogarRodadaCollection;
    }

    public void setJogarRodadaCollection(Collection<JogarRodada> jogarRodadaCollection) {
        this.jogarRodadaCollection = jogarRodadaCollection;
    }

    @XmlTransient
    public Collection<Historia> getHistoriaCollection() {
        return historiaCollection;
    }

    public void setHistoriaCollection(Collection<Historia> historiaCollection) {
        this.historiaCollection = historiaCollection;
    }

    public Historia getIdHistoria() {
        return idHistoria;
    }

    public void setIdHistoria(Historia idHistoria) {
        this.idHistoria = idHistoria;
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
        if (!(object instanceof Historia)) {
            return false;
        }
        Historia other = (Historia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.entities.Historia[ id=" + id + " ]";
    }

    public Projeto getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(Projeto idProjeto) {
        this.idProjeto = idProjeto;
    }
    
}
