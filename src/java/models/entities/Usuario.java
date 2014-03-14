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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author smp
 */
@Entity
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findById", query = "SELECT u FROM Usuario u WHERE u.id = :id"),
    @NamedQuery(name = "Usuario.findByNome", query = "SELECT u FROM Usuario u WHERE u.nome = :nome"),
    @NamedQuery(name = "Usuario.findByEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email"),
    @NamedQuery(name = "Usuario.findBySenha", query = "SELECT u FROM Usuario u WHERE u.senha = :senha"),
    @NamedQuery(name = "Usuario.findByEmailAndSenha", query = "SELECT u FROM Usuario u WHERE u.senha = :senha AND u.email = :email AND u.status = TRUE"),
    @NamedQuery(name = "Usuario.findByHashMail", query = "SELECT u FROM Usuario u WHERE u.hashmail = :hashmail")})

public class Usuario implements Serializable {
    @Size(max = 122)
    @Column(name = "hashmail")
    private String hashmail;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private boolean status;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull(message="O campo nome não pode ser vazio")
    @Size(min = 8, max = 122, message="O campo usuário deve ter no mínimo oito caracteres")
    @Column(name = "nome")
    private String nome;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull(message="O campo email não pode ser vazio")
    @Size(min = 6, max = 122, message ="Email inválido")
    @Pattern(regexp="^([0-9a-zA-Z]+([_.-]?[0-9a-zA-Z]+)*@[0-9a-zA-Z]+[0-9,a-z,A-Z,.,-]*(.){1}[a-zA-Z]{2,4})+$", message="Email inválido")
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull(message="O campo senha não pode ser vazio")
    @Size(min = 6, max = 122, message =" o campo senha deve conter no mínimo 6 caracteres")
    @Column(name = "senha")
    private String senha;
     
    
    @JoinTable(name = "usuario_area_atuacao", joinColumns = {
        @JoinColumn(name = "id_usuario", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "id_area_atuacao", referencedColumnName = "id")})
    @ManyToMany
    private Collection<AreaAtuacao> areaAtuacaoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<JogarRodada> jogarRodadaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<Acessar> acessarCollection;

    public Usuario() {
    }

    public Usuario(Integer id) {
        this.id = id;
    }

    public Usuario(Integer id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @XmlTransient
    public Collection<AreaAtuacao> getAreaAtuacaoCollection() {
        return areaAtuacaoCollection;
    }

    public void setAreaAtuacaoCollection(Collection<AreaAtuacao> areaAtuacaoCollection) {
        this.areaAtuacaoCollection = areaAtuacaoCollection;
    }

    @XmlTransient
    public Collection<JogarRodada> getJogarRodadaCollection() {
        return jogarRodadaCollection;
    }

    public void setJogarRodadaCollection(Collection<JogarRodada> jogarRodadaCollection) {
        this.jogarRodadaCollection = jogarRodadaCollection;
    }

    @XmlTransient
    public Collection<Acessar> getAcessarCollection() {
        return acessarCollection;
    }

    public void setAcessarCollection(Collection<Acessar> acessarCollection) {
        this.acessarCollection = acessarCollection;
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
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.entities.Usuario[ id=" + id + " ]";
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getHashmail() {
        return hashmail;
    }

    public void setHashmail(String hashmail) {
        this.hashmail = hashmail;
    }
}
   
    