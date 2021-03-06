package models.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @NamedQuery(name = "Estoria.findByIdProjeto", query = "SELECT e FROM Estoria e WHERE e.projeto.id = :idProjeto"),
    @NamedQuery(name = "Estoria.findByNome", query = "SELECT e FROM Estoria e WHERE e.nome = :nome"),
    @NamedQuery(name = "Estoria.findByStatus", query = "SELECT e FROM Estoria e WHERE e.status = :status"),
    @NamedQuery(name = "Estoria.findAllChildren", query = "SELECT s FROM Estoria e JOIN e.subtasks s WHERE e.estoriaPK.id = :id"),
    @NamedQuery(name = "Estoria.findAllParents", query = "SELECT e FROM Estoria e WHERE e.estoriaPK.id NOT IN (SELECT s.estoriaPK.id FROM Estoria e JOIN e.subtasks s)")

}) 
public class Estoria implements Serializable {
 
    @Column(name = "data_criacao")
    @Temporal(TemporalType.DATE)
    private Date dataCriacao;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estoria")
    private Collection<Estimativa> estimativaCollection;
    
    @ManyToOne
    @JoinColumns({@JoinColumn(name = "id_estoria", referencedColumnName = "id", updatable = false, insertable = true, nullable = true)
                  ,@JoinColumn(name = "id_projeto" , referencedColumnName = "id_projeto" , insertable = false, updatable = false)})
    private Estoria subtask;
    
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId  
    protected EstoriaPK estoriaPK;
    
    @Size(max = 100,message = "O título não pode exceder a 100 caracteres")
    @Column(name = "nome")
    private String nome;
    @Lob
    @Size(max = 2147483647,message = "Esta descrição excede o tamanho aceitado, por favor resuma seu texto")
    @Column(name = "descricao")
    private String descricao;
    
     
    
    @Column(name = "status")
    private Boolean status = false;
     
    @JoinColumn(name = "id_projeto", nullable = false , referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Projeto projeto;
    
    @OneToMany(mappedBy = "subtask",cascade = CascadeType.PERSIST) 
    private Collection<Estoria> subtasks;

    
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
    public Collection<Estoria> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(Collection<Estoria> estoriaCollection) {
        this.subtasks = estoriaCollection;
    }

    public Estoria getSubtask() {
        return subtask;
    }

    public void setSubtask(Estoria subtask) {
        this.subtask = subtask;
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


    @XmlTransient
    public Collection<Estimativa> getEstimativaCollection() {
        return estimativaCollection;
    }

    public void setEstimativaCollection(Collection<Estimativa> estimativaCollection) {
        this.estimativaCollection = estimativaCollection;
    }
    
      public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
        
    @Column(name = "estimativa")
    private Integer estimativa;
    
     public Integer getEstimativa() {
        return estimativa;
    }

    public void setEstimativa(Integer estimativa) {
        this.estimativa = estimativa;
    }
    
   
}