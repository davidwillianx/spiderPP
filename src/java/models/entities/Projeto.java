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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Bruno
 */
@Entity
@Table(name = "projeto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Projeto.findAll", query = "SELECT p FROM Projeto p"),
    @NamedQuery(name = "Projeto.findById", query = "SELECT p FROM Projeto p WHERE p.id = :id"),
    @NamedQuery(name = "Projeto.findByNome", query = "SELECT p FROM Projeto p WHERE p.nome = :nome")})
public class Projeto implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProjeto")
    private Collection<Estoria> estoriaCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 122)
    @Column(name = "nome")
    private String nome;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "descricao")
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projeto")
    private Collection<Acessar> acessarCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProjeto")
    private Collection<Historia> historiaCollection;

    public Projeto() {
    }

    public Projeto(Integer id) {
        this.id = id;
    }

    public Projeto(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
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
    public Collection<Acessar> getAcessarCollection() {
        return acessarCollection;
    }

    public void setAcessarCollection(Collection<Acessar> acessarCollection) {
        this.acessarCollection = acessarCollection;
    }

    @XmlTransient
    public Collection<Historia> getHistoriaCollection() {
        return historiaCollection;
    }

    public void setHistoriaCollection(Collection<Historia> historiaCollection) {
        this.historiaCollection = historiaCollection;
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
        if (!(object instanceof Projeto)) {
            return false;
        }
        Projeto other = (Projeto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.entities.Projeto[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Estoria> getEstoriaCollection() {
        return estoriaCollection;
    }

    public void setEstoriaCollection(Collection<Estoria> estoriaCollection) {
        this.estoriaCollection = estoriaCollection;
    }
    
}
