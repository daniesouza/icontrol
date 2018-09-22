package br.com.icontrol.util;


import java.util.List;
import java.util.ResourceBundle;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.icontrol.entity.Usuario;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.DadosUtil;
import br.com.icontrol.util.Propriedades;

/**
 * @author Icontrol
 *      
 */

public class FacesUtil {

	public static void cadastrarUsuarioSessao(Usuario usuario){
		
		 FacesContext context = FacesContext.getCurrentInstance();  
		 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);  
		 
		 session.setAttribute("usuarioLogado", usuario);		
	}
	
	public static Usuario recuperarUsuarioSessao(){
		
		 FacesContext context = FacesContext.getCurrentInstance();  
		 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);  
		 
		 return (Usuario) session.getAttribute("usuarioLogado");
	}	
	public static String getIpUsuario() {
		HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();  
		String ip = httpServletRequest.getRemoteAddr();
		return ip;
	}

	public static Object getAtributoSessao(String nome) {
		Object obj = getSessao().getAttribute(nome);
		return obj;		
	}
	
	private static HttpSession getSessao() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return session;
	}

	public static Object getBackingBean(String name) {
		FacesContext context = FacesContext.getCurrentInstance();
		Application app = context.getApplication();
																															//#{%s}
		ValueExpression expression = app.getExpressionFactory().createValueExpression(context.getELContext(), String.format("#{s}", name), Object.class);
		return expression.getValue(context.getELContext());
	}

	public static MethodExpression createMethodExpression(String action) {  
		MethodExpression methodExpression = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().createMethodExpression(  
				FacesContext.getCurrentInstance().getELContext(), action, null, new Class<?>[0]);  
		return methodExpression;  
	}      

	public static String obterPaginaAtual() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String nomePagina = facesContext.getViewRoot().getViewId().replace("/", "");
		nomePagina = nomePagina.replace("xhtml", "jsf");
		return nomePagina;
	}

   

	public static String tratarMensagemErro(ServicoException se) {
		StringBuilder retorno = new StringBuilder(); 
		List<String> mensagens = se.getMensagens();
		if (DadosUtil.isEmpty(mensagens)) {
			retorno.append(se.getMessage());
		} else {
			for (String mensagem : mensagens) {
				String valor = Propriedades.obterValor(mensagem);
				retorno.append(valor);
				retorno.append("\n");
			}
		}
		return retorno.toString();
	}
	
	public static void exibirErro(String msg) {
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, "");
		FacesContext.getCurrentInstance().addMessage(null, fm);
	}
	
	public static void exibirErro(ServicoException se) {
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, tratarMensagemErro(se), "");
		FacesContext.getCurrentInstance().addMessage(null, fm);
	}
	
	public static void exibirErro(Exception e) {
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "");
    	FacesContext.getCurrentInstance().addMessage(null, fm);
	}	
	
	public static void exibirAlerta(String msg) {
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, "");
		FacesContext.getCurrentInstance().addMessage(null, fm);
	}
	
	public static void exibirInfo(String msg) {
		ResourceBundle bundle = ResourceBundle.getBundle("mensagens");
		if( bundle.containsKey(msg) ) {
			msg = bundle.getString(msg);
		}
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, "");			
		FacesContext.getCurrentInstance().addMessage(null, fm);
	}	
}