package br.com.ischool.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import br.com.ischool.entity.Entidade;
import br.com.ischool.exceptions.ServicoException;
import br.com.ischool.exceptions.WebException;
import br.com.ischool.util.FacesUtil;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 *      
 */

public abstract class AbstractViewHelper<T extends Entidade> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3804684865408766365L;

	private int registroPorPagina;

	protected static ResourceBundle bundle = ResourceBundle.getBundle("br.com.ischool.messages.messages");
	
	protected T filtro;
	
	protected T[] selecionados;

	protected T entidade;
	
	

	@PostConstruct
	public final void init() {
	
		FacesMessage msg = null;
		
		try {
		
		setRegistroPorPagina(6);
		
		inicializar();
		
		reset();
		
		} catch (WebException e) {
			e.printStackTrace();
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "");
		}
        if( msg != null ) {
        	FacesContext.getCurrentInstance().addMessage(null, msg);
        }
	}
		
	public final void salvarOuAtualizar() {
		FacesMessage msg = null;
		
		
        RequestContext context = RequestContext.getCurrentInstance();  
        boolean error = false; 
        
		if(entidade.getId() == null){
			
			try {
				
				salvarImpl();
			
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("mensagem_registro_inserido"), "");
			} catch (ServicoException e) {
				msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,  bundle.getString("erro_incluir"), FacesUtil.tratarMensagemErro(e));
				error = true;
				e.printStackTrace();
			}catch (Exception e) {				
				msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("erro_incluir"), e.getMessage());
				error = true;
				e.printStackTrace();
			}	
		
		}else{
			
			try {
				
				alterarImpl();
			
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("mensagem_registro_atualizado"), "");
			} catch (ServicoException e) {
				msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("erro_alterar"), FacesUtil.tratarMensagemErro(e));
				error = true;
				e.printStackTrace();
			}catch (Exception e) {			
				msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("erro_alterar"), e.getMessage());
				error = true;
				e.printStackTrace();
			}

		}
		
        if( msg != null ) {
        	FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
        context.addCallbackParam("error", error); 
		  
	}
	

	public final void excluir() {
		FacesMessage msg = null;
		
		
        RequestContext context = RequestContext.getCurrentInstance();  
        boolean error = false; 
        
		try {
			excluirImpl();
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("mensagem_registro_excluido"), "");
		} catch (ServicoException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("erro_excluir"), FacesUtil.tratarMensagemErro(e));
			error = true;
			e.printStackTrace();
		}catch (Exception e) {			
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("erro_excluir"), e.getMessage());
			error = true;
			e.printStackTrace();
		}
	
		if( msg != null ) {
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}	
		
		context.addCallbackParam("error", error); 
		
	}
	
	
	public abstract void salvarImpl() throws ServicoException;
	
	public abstract void alterarImpl() throws ServicoException;

	public abstract void excluirImpl() throws ServicoException;
	
	public abstract void inicializar() throws WebException;
	
	public abstract void pesquisar();

	public abstract void reset();
	
	
	public List<T> getSelecionadosAsList() {
		List<T> entidades = Arrays.asList(getSelecionados());
		return entidades;
	}

	public int getRegistroPorPagina() {
		return registroPorPagina;
	}

	public void setRegistroPorPagina(int registroPorPagina) {
		this.registroPorPagina = registroPorPagina;
	}

	public T getFiltro() {
		return filtro;
	}

	public void setFiltro(T filtro) {
		this.filtro = filtro;
	}

	public T[] getSelecionados() {
		return selecionados;
	}

	public void setSelecionados(T[] selecionados) {
		this.selecionados = selecionados;
	}

	public T getEntidade() {
		return entidade;
	}

	public void setEntidade(T entidade) {
		this.entidade = entidade;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
	
}
