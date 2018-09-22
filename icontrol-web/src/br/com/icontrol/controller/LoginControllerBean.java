package br.com.icontrol.controller;

import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import br.com.icontrol.business.UsuarioServiceLocal;
import br.com.icontrol.entity.Usuario;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.security.MD5CheckSum;
import br.com.icontrol.util.Constantes;
import br.com.icontrol.util.DadosUtil;
import br.com.icontrol.util.FacesUtil;
import br.com.icontrol.util.Icone;

/**
 * @author Icontrol
 *      
 */

@ManagedBean
@SessionScoped
public class LoginControllerBean extends AbstractViewHelper<Usuario> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3373998304619377312L;
//	private String username;
//	private String password;
	
	private String senhaAntiga;
	private String senhaNova;
	private String senhaConfirma;
	private boolean podeAlterar = false;
	
    private Icone  theme = new Icone("", Constantes.DEFAULT_TEMA);
    private boolean isSessionUserInfoSet = false;
	
	@Override
	public void inicializar() {
		// TODO Auto-generated method stub
		
	}


	@EJB
	private UsuarioServiceLocal usuarioService;

//	public String login() throws IOException, ServletException {
//
//		ExternalContext context = FacesContext.getCurrentInstance()
//				.getExternalContext();
//
//		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
//				.getRequestDispatcher("/j_spring_security_check?j_username="
//						+ username + "&j_password=" + password);
//		dispatcher.forward((ServletRequest) context.getRequest(),
//				(ServletResponse) context.getResponse());
//		FacesContext.getCurrentInstance().responseComplete();
//		return null;
//	}
	
	
   public void preRenderView(ComponentSystemEvent event)
    {
        if(this.isSessionUserInfoSet)
            return;

        setSessionUserInfo();
        this.isSessionUserInfoSet = true;
    }
   
   private void setSessionUserInfo()
   {
	   try {
		getUsuarioLogado();
	    this.getTheme().setNome(entidade.getTema() == null ? Constantes.DEFAULT_TEMA : entidade.getTema());
	    this.getTheme().setImagem(entidade.getTema() == null ? Constantes.DEFAULT_TEMA+".png" : entidade.getTema()+".png");

	} catch (ServicoException e) {		
		e.printStackTrace();
	}

   }

	public void logOut() {
		try {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			HttpServletRequest request = (HttpServletRequest) context.getRequest();		
			context.redirect(request.getContextPath()+ "/j_spring_security_logout");
			
		} catch (IOException e) {

			System.out.println("ERROR: " + e.getMessage());
		}
	}

	// ENTIDADE É O USUARIO LOGADO.. SE FOR NULO QUER DIZER QUE O USUARIO ESTA SE LOGANDO NO MOMENTO
	public Usuario getUsuarioLogado() throws ServicoException {

		if (entidade == null) {

			User user = (User) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();

			if (user != null) {

				entidade = usuarioService.pesquisarUsuarioNomeUsuario(user.getUsername());

				FacesUtil.cadastrarUsuarioSessao(entidade);
				
				gravarLog("Login "+entidade.getUsuario(), Constantes.LOGIN);

			}
		}

		return entidade;
	}


	public void salvarTema() throws ServicoException{
		entidade.setTema(theme.getNome());

		alterarImpl();
	}
	
	
	public void salvarSenha() throws ServicoException{
		
		try {
			
			FacesMessage msg = null;
			
			if(senhaNova.length()<4){
				msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Tamanho de senha muito pequena. Favor Inserir uma senha maior!","");
			}
			else{
				
				if(senhaNova.equals(senhaConfirma)){
					
					entidade.setDataSenha(new Date());
					entidade.setTrocarSenha(false);
					entidade.setTentativasSenhaIncorreta((byte)0);
					entidade.setSenha(MD5CheckSum.gerarSenhaHash(senhaNova));
					
					podeAlterar = false;
					salvarOuAtualizar();
					
				}else{			
					msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Confirmação de senha não confere com a nova senha!","");
				}
				
			}
			
		
			if( msg != null ) {
				FacesContext.getCurrentInstance().addMessage(null, msg);
				podeAlterar = false;
			}


		} catch (NoSuchAlgorithmException e) {
			throw new ServicoException(e);
		}
		
	}
	
	public void checkSenhaAntiga(){
		
		FacesMessage msg = null;
		
		if(!DadosUtil.isEmpty(senhaAntiga)){
			
			try {
				String pass = MD5CheckSum.gerarSenhaHash(senhaAntiga);
				
				if(!pass.equals(entidade.getSenha())){				
					msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "senha inválida!", "");
				}
			} catch (NoSuchAlgorithmException e) {
				msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "falha ao checar senha!", e.getMessage());
				e.printStackTrace();
			}
		}
		else{			
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "senha não pode ser vazia!","");			
		}
		

		if( msg != null ) {
			FacesContext.getCurrentInstance().addMessage(null, msg);
			podeAlterar = false;
		}
		else{
			podeAlterar = true;
		}
		
	}
	
	public String home(){
		
		Usuario usuario = FacesUtil.recuperarUsuarioSessao();
		
		if(!DadosUtil.isEmpty(usuario)){
				
			if(usuario.getAutoridade().equals(usuario.getClient())){
				
				return "/paginas/client/principal";
			}else if(usuario.getAutoridade().equals(usuario.getAdminCliente())){
				
				return "/paginas/adminClient/principal";
			}else if(usuario.getAutoridade().equals(usuario.getAdministrador())){
				
				return "/paginas/admin/principal";
			}
		}

			
		
		return "/paginas/public/home";
		
	}

	@Override
	public void salvarImpl() throws ServicoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void alterarImpl() throws ServicoException {
	
		
		usuarioService.alterarUsuario(entidade);	
	}

	@Override
	public void excluirImpl() throws ServicoException {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void pesquisar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	public Icone getTheme() {
		
		if(theme == null){
			theme = new Icone("", Constantes.DEFAULT_TEMA);
		}
		
		return theme;
	}

	public void setTheme(Icone theme) {
		this.theme = theme;
	}

	public String getSenhaAntiga() {
		return senhaAntiga;
	}

	public void setSenhaAntiga(String senhaAntiga) {
		this.senhaAntiga = senhaAntiga;
	}

	public String getSenhaNova() {
		return senhaNova;
	}

	public void setSenhaNova(String senhaNova) {
		this.senhaNova = senhaNova;
	}

	public String getSenhaConfirma() {
		return senhaConfirma;
	}

	public void setSenhaConfirma(String senhaConfirma) {
		this.senhaConfirma = senhaConfirma;
	}

	public boolean isPodeAlterar() {
		return podeAlterar;
	}

	public void setPodeAlterar(boolean podeAlterar) {
		this.podeAlterar = podeAlterar;
	}

	

}
