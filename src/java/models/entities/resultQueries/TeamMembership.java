/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models.entities.resultQueries;

/**
 *
 * @author smp
 */
public class TeamMembership {
    
    private int idUsuario;
    private int idPerfil;
    private String nome;
    private String email;
    private String papel;
    private String descricacao;
    
    public TeamMembership(String nome, String email, String descricacao, String papel, int idPerfil, int idUsuario)
    {
        this.nome = nome;
        this.email = email;
        this.descricacao = descricacao;
        this.papel = papel;
        this.idPerfil = idPerfil;
        this.idUsuario = idUsuario;
    }
    
    public void setNome(String nome)
    {
        this.nome  = nome;
    }
    
    public String getNome()
    {
        return this.nome;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public String getEmail()
    {
        return this.email;
    }
    
    public void setPapel(String papel)
    {
        this.papel = papel;
    }
    
    public String getPapel()
    {
        return this.papel;
    }
    
    public void setDescricao(String descricacao)
    {
        this.descricacao = descricacao;
    }
    
    public String getDescricacao()
    {
        return this.descricacao;
    }
    
    public int getIdPerfil()
    {
        return this.idPerfil;
    } 
    
    public int getIdUsuario()
    {
        return this.idUsuario;
    }
}
