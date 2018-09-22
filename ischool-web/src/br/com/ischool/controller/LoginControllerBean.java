package br.com.ischool.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import br.com.ischool.business.UsuarioServiceLocal;
import br.com.ischool.entity.Usuario;
import br.com.ischool.exceptions.ServicoException;
import br.com.ischool.util.FacesUtil;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 *      
 */

@ManagedBean
@SessionScoped
public class LoginControllerBean extends AbstractViewHelper<Usuario> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3373998304619377312L;

	@EJB
	private UsuarioServiceLocal usuarioService;


	public void logOut() {
		try {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			HttpServletRequest request = (HttpServletRequest) context.getRequest();		
			context.redirect(request.getContextPath()+ "/j_spring_security_logout");
			
		} catch (IOException e) {

			System.out.println("ERROR: " + e.getMessage());
		}
	}

	// ENTIDADE É O USUARIO LOGADO
	public Usuario getUsuarioLogado() throws ServicoException {

		if (entidade == null) {

			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (user != null) {

				entidade = usuarioService.pesquisarUsuarioNomeUsuario(user.getUsername());

				FacesUtil.cadastrarUsuarioSessao(entidade);

			}
		}

		return entidade;
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
	public void inicializar() {
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

//	@Override
//	public UserDetails loadUserByUsername(String userName)	throws UsernameNotFoundException, DataAccessException {
//		
//		
//		try {
//			usuarioLogado = usuarioService.pesquisarUsuarioNomeUsuario(userName);
//		} catch (ServicoException e) {
//			
//			e.printStackTrace();
//			
//			throw new DataAccessException(userName) {
//
//				/**
//				 * 
//				 */
//				private static final long serialVersionUID = 1L;
//			};
//		}
//
//		
//		return null;
//	}

}
