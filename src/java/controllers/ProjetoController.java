package controllers;


import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import libs.BuildHash;
import libs.BuildMessage;
import libs.Redirect;
import libs.SessionManager;
import libs.exception.BusinessException;
import libs.exception.FindProjectException;
import libs.exception.NoPersistProjetoException;
import libs.exception.NotFoundProjetoException;
import models.ejbs.interfaces.IAcessar;
import models.ejbs.interfaces.IProjeto;
import models.entities.Projeto;


/**
 *
 * @author BlenoVale
 */
@Named
@RequestScoped
public class ProjetoController {
    
    private static final Logger LOGGER = Logger.getLogger(ProjetoController.class.getName());
    
    private Projeto projeto = new Projeto();
    private Redirect redirect;
    private BuildMessage buildMessage = new BuildMessage();
    private String pkm;
    private List<Projeto> projetos;
    
    private SessionManager sessionManager;

    
    @EJB
    private IProjeto iProjeto;
    @EJB
    private IAcessar acessarBusiness;
    
    

    public ProjetoController() {
        this.redirect = new Redirect();
        this.buildMessage = new BuildMessage();
    }

    public Projeto getProjeto() {
        return this.projeto;
    }
    
    public void saveProjeto(Projeto projeto) {
        
        buildMessage = new BuildMessage();
        
        try {
            Projeto savedProjeto = iProjeto.saveProjeto(projeto);
            buildMessage.addInfo("Projeto cadastrado com Sucesso");
            this.signProjeto(savedProjeto.getId());
        
        } catch (NoPersistProjetoException error) {
            buildMessage.addError(error.getMessage());
            LOGGER.logp(Level.WARNING , ProjetoController.class.getName(), "saveProjeto", error.getMessage());
        }
    }
    
    
    public void signProjeto(int idProjeto)
    {
        try{
           projeto = iProjeto.selectProjetoById(idProjeto);
           sessionManager = new SessionManager();
           sessionManager.set("projeto", projeto);
            
           this.redirectToProject();
           
        }catch(NotFoundProjetoException error)
        {
           this.buildMessage.addError(error.getMessage());
           LOGGER.logp(Level.WARNING , ProjetoController.class.getName(), "signProjeto", error.getMessage());
        }
    }

    public List<Projeto> getProjetos() {
        try {
        
            projetos = this.iProjeto.selectProjectByUserSession();

        } catch (FindProjectException error) {
            LOGGER.logp(Level.WARNING , ProjetoController.class.getName(), "getProjetos", error.getMessage());
        }
        
        return projetos;
    }
    
    private void redirectToProject(){
        redirect = new Redirect();
        redirect.redirectTo("/projeto/");
    }
    
    //-----------------------------------------------------------------------

    
    
    
    public int showUserProjetoPersmission(int idProjeto, int idUsuario)
    {
        try{
            return iProjeto.selectProjetoUsuarioPerfil(idProjeto, idUsuario);
            
        }catch(BusinessException error)
        {
            this.redirect.redirectTo("/index.xhtml");
            return 0;
        }
    }
    
    
    
    
    
    
    
    
    
    
    public void preEditProjeto(int idProjeto)
    {
        try{
            this.projeto = this.iProjeto.selectProjetoById(idProjeto);
            this.redirect.redirectTo("/projeto/editar.xhtml");
        }catch(BusinessException error)
        {
            this.buildMessage.addError(error.getMessage());
        }
    }
    
    public void exitProjeto()
    {
        try{
            this.sessionManager =  new SessionManager();
            this.sessionManager.remove("projeto");
            this.redirect.redirectTo("/user/index.xhtml");
        }catch(Exception error)
        {
            System.err.println("Falha ao sair");
        }
    }
    
    public String showHashProject(int idProjeto) throws UnsupportedEncodingException
    {
        BuildHash hashBuilder = new BuildHash();
        return hashBuilder.buildHashStringURL(String.valueOf(idProjeto));
    }
    
    public String showHashPerfil(int idUsuario, int idProjeto) throws UnsupportedEncodingException
    {
        int idPerfil =  this.showUserProjetoPersmission(idProjeto, idUsuario);
        BuildHash buildHash = new BuildHash();
        return buildHash.buildHashStringURL(String.valueOf(idPerfil));
    }
    
    public String showHashUsuario(int idUsuario) throws UnsupportedEncodingException{
        
        return new BuildHash().buildHashStringURL(String.valueOf(idUsuario));
    }

    public String showMemberProfileDescription(int idUsuario, int idProjeto){
        try {
             return acessarBusiness.findMemberProfile(idUsuario,idProjeto).getNome();
        } catch (Exception e) {
            return "Error: nao foi encontrado perfil para o usuario";
        }
    }
    
    
    
}
