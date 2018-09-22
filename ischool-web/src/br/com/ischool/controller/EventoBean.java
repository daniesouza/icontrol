package br.com.ischool.controller;


import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.ischool.business.EventoServiceLocal;
import br.com.ischool.controller.datamodel.EventoDataModel;
import br.com.ischool.entity.Cliente;
import br.com.ischool.entity.Evento;
import br.com.ischool.entity.Usuario;
import br.com.ischool.exceptions.ServicoException;
import br.com.ischool.exceptions.WebException;
import br.com.ischool.util.FacesUtil;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 *      
 */

@ManagedBean
@ViewScoped
public class EventoBean extends AbstractViewHelper<Evento> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3725699119385532894L;

	@EJB
	private EventoServiceLocal eventoService;
	
	private EventoDataModel eventoDataModel = new EventoDataModel();
	

			
	
	@Override
	public void inicializar() throws WebException {
				
			setEntidade(new Evento());	
			setFiltro(new Evento());
			getFiltro().setCliente(new Cliente());	
			eventoDataModel.setEventoLocal(eventoService);
	}
	


	@Override
	public void pesquisar() {
		
		eventoDataModel.setFiltro(filtro);
		
	}

	@Override
	public void reset() {
		setEntidade(new Evento());	
	}
	
	public void novoEventoCliente(){
		
		reset();
		
		Usuario usuario = FacesUtil.recuperarUsuarioSessao();		
		getEntidade().setCliente(usuario.getCliente());
		getEntidade().setIcone("icone_evento.png");

		
	}
	
	public void editar(){

	}

	@Override
	public void salvarImpl() throws ServicoException {
		
	
		eventoService.salvarEvento(entidade);	
		
	}

	@Override
	public void alterarImpl() throws ServicoException {
		

		eventoService.alterarEvento(entidade);	
		
	}

	@Override
	public void excluirImpl() throws ServicoException {
		
		eventoService.excluirEvento(entidade);
		
	}

	public EventoServiceLocal getEventoService() {
		return eventoService;
	}

	public void setEventoService(EventoServiceLocal EventoService) {
		this.eventoService = EventoService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public EventoDataModel getEventoDataModel() {
		return eventoDataModel;
	}

	public void setEventoDataModel(EventoDataModel EventoDataModel) {
		this.eventoDataModel = EventoDataModel;
	}

}
