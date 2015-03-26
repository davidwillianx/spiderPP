package controllers;


import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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
import libs.exception.FindPerfilException;
import libs.exception.FindProjectException;
import libs.exception.NoPersistProjetoException;
import libs.exception.NotFoundProjetoException;
import models.ejbs.interfaces.IAcessar;
import models.ejbs.interfaces.IProjeto;
import models.entities.Perfil;
import models.entities.Projeto;


/**
 *
 * @author BlenoVale
 * @author DavidWillianx
 */
@Named
@RequestScoped
public class ProjetoController {
    
    private static final Logger LOGGER = Logger.getLogger(ProjetoController.class.getName());
    private BuildHash hashProjectId, hashUserId, hashProfile;
    
    private Projeto projeto = new Projeto();
    private Redirect redirect;
    private BuildMessage buildMessage = new BuildMessage();
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
    
    public Perfil showUserPermission(int projectId, int userId){
        Perfil userProjectProfile = new Perfil();
        try {
             userProjectProfile = iProjeto.selectUserProjectProfile(projectId, userId);
        } catch (FindPerfilException error) {
            redirect.redirectTo("/index.xhtml");
        }
        return userProjectProfile;
    }
    
    
    public void goOutProject() {
        sessionManager = new SessionManager();
        sessionManager.remove("projeto");
        this.redirect.redirectTo("/user/index.xhtml");
    }
    
    private void redirectToProject(){
        redirect = new Redirect();
        redirect.redirectTo("/projeto/");
    }
    

    public String showHashProjectId(int projectId){
        
        try {
             hashProjectId = new BuildHash(Integer.toString(projectId));    
            
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException error) {
            
            this.buildMessage.addError("Problemas ao gerar seu acesse ao jogo, reinicie a página e tente novamente");
            LOGGER.logp(Level.SEVERE, ProjetoController.class.getName(), "showHashProjectId", "Falha ao gerar o hash do projeto", error);
        }
        
        return hashProjectId.buildHashURLData();
    }
    
    public String showHashProfile(int userId, int projectId){
        
        
        try {
            Perfil userProfile = this.showUserPermission(projectId, userId);
            hashProfile = new BuildHash(
                        Integer.toString(userProfile.getId())
            );
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException error) {
            
            this.buildMessage.addError("Problemas ao gerar seu acesse ao jogo, reinicie a página e tente novamente");
            LOGGER.logp(Level.SEVERE, ProjetoController.class.getName(), "showHashProfile", "Falha ao gerar o hash do projeto", error);
        }
        return hashProfile.buildHashURLData();
    }
    
    public String showHashUserId(int userId){
        try {
            hashUserId = new BuildHash(
                    Integer.toString(userId)
            );
            
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException error) {
            
            this.buildMessage.addError("Problemas ao gerar seu acesse ao jogo, reinicie a página e tente novamente");
            LOGGER.logp(Level.SEVERE, ProjetoController.class.getName(), "showHashUserId", "Falha ao gerar o hash do projeto", error);
        }
        
        return hashUserId.buildHashURLData();
    }
//-----------------------------------------------------------------------
     public String showMemberProfileDescription(int idUsuario, int idProjeto){
        try {
             return acessarBusiness.findMemberProfile(idUsuario,idProjeto).getNome();
        } catch (Exception e) {
            return "Error: nao foi encontrado perfil para o usuario";
        }
    }
    
}
