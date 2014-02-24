/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author smp
 */
@Entity
@Table(name = "area_atuacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AreaAtuacao.findAll", query = "SELECT a FROM AreaAtuacao a"),
    @NamedQuery(name = "AreaAtuacao.findById", query = "SELECT a FROM AreaAtuacao a WHERE a.id = :id"),
    @NamedQuery(name = "AreaAtuacao.findByNome", query = "SELECT a FROM AreaAtuacao a WHERE a.nome = :nome"),
    @NamedQuery(name = "AreaAtuacao.findByDescricao", query = "SELECT a FROM AreaAtuacao a WHERE a.descricao = :descricao")})
public class AreaAtuacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 145)
    @Column(name = "nome")
    private String nome;
    @Size(max = 245)
    @Column(name = "descricao")
    private String descricao;
    @ManyToMany(mappedBy = "areaAtuacaoCollection")
    private Collection<Usuario> usuarioCollection;

    public AreaAtuacao() {
    }

    public AreaAtuacao(Integer id) {
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

    @XmlTransient
    public Collection<Usuario> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
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
        if (!(object instanceof AreaAtuacao)) {
            return false;
        }
        AreaAtuacao other = (AreaAtuacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.entities.AreaAtuacao[ id=" + id + " ]";
    }
    
}
