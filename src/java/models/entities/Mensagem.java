/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author smartphonne
 */
@Entity
@Table(name = "mensagem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mensagem.findAll", query = "SELECT m FROM Mensagem m"),
    @NamedQuery(name = "Mensagem.findById", query = "SELECT m FROM Mensagem m WHERE m.mensagemPK.id = :id"),
    @NamedQuery(name = "Mensagem.findByIdProjeto", query = "SELECT m FROM Mensagem m WHERE m.mensagemPK.idProjeto = :idProjeto"),
    @NamedQuery(name = "Mensagem.findByIdUsuario", query = "SELECT m FROM Mensagem m WHERE m.mensagemPK.idUsuario = :idUsuario"),
    @NamedQuery(name = "Mensagem.findByDataRecebido", query = "SELECT m FROM Mensagem m WHERE m.dataRecebido = :dataRecebido")})
public class Mensagem implements Serializable {
    @Size(max = 222)
    @Column(name = "autor")
    private String autor;
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MensagemPK mensagemPK;
    @Lob
    @Size(max = 65535)
    @Column(name = "texto")
    private String texto;
    @Column(name = "data_recebido")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRecebido;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;
    @JoinColumn(name = "id_projeto", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Projeto projeto;

    public Mensagem() {
    }

    public Mensagem(MensagemPK mensagemPK) {
        this.mensagemPK = mensagemPK;
    }

    public Mensagem(int id, int idProjeto, int idUsuario) {
        this.mensagemPK = new MensagemPK(id, idProjeto, idUsuario);
    }
    
    public Mensagem(int idProjeto, int idUsuario)
    {
        this.mensagemPK = new MensagemPK(idProjeto, idUsuario);
    }
    
    public MensagemPK getMensagemPK() {
        return mensagemPK;
    }

    public void setMensagemPK(MensagemPK mensagemPK) {
        this.mensagemPK = mensagemPK;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getDataRecebido() {
        return dataRecebido;
    }

    public void setDataRecebido(Date dataRecebido) {
        this.dataRecebido = dataRecebido;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mensagemPK != null ? mensagemPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mensagem)) {
            return false;
        }
        Mensagem other = (Mensagem) object;
        if ((this.mensagemPK == null && other.mensagemPK != null) || (this.mensagemPK != null && !this.mensagemPK.equals(other.mensagemPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.entities.Mensagem[ mensagemPK=" + mensagemPK + " ]";
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
    
}
