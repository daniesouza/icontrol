package br.com.ischool.controller;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.ischool.business.ClasseServiceLocal;
import br.com.ischool.business.EventoClasseServiceLocal;
import br.com.ischool.business.EventoServiceLocal;
import br.com.ischool.controller.datamodel.EventoDataModel;
import br.com.ischool.entity.Classe;
import br.com.ischool.entity.Cliente;
import br.com.ischool.entity.Evento;
import br.com.ischool.entity.EventoClasse;
import br.com.ischool.exceptions.ServicoException;
import br.com.ischool.exceptions.WebException;
import br.com.ischool.util.Constantes;
import br.com.ischool.util.DadosUtil;
import br.com.ischool.util.FacesUtil;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 *      
 */

@ManagedBean
@ViewScoped
public class GerenciadorEventoBean extends AbstractViewHelper<EventoClasse> implements Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = 5130523415627084693L;

	@EJB
	private EventoServiceLocal eventoService;
	
	@EJB
	private EventoClasseServiceLocal eventoClasseService;
	
	@EJB
	private ClasseServiceLocal classeService;
	
	private EventoDataModel eventoDataModel = new EventoDataModel();
	
	private List<EventoClasse> eventoClasseManha;
	private List<EventoClasse> eventoClasseTarde;
	private List<EventoClasse> eventoClasseOutros;
	private List<Classe>	   listaClasses;
	
	private Classe classeSelecionada;
	
	private Evento[] eventosSelecionados;  
			
	
	@Override
	public void inicializar() throws WebException {
			
			setEntidade(new EventoClasse());
			getEntidade().setIdEventoClasse(0l); // adicionado o ID para poder SEMPRE EXECUTAR O METODO alterarImpl
			listaClasses 	   = new ArrayList<Classe>();
			eventoClasseManha  = new ArrayList<EventoClasse>();
			eventoClasseTarde  = new ArrayList<EventoClasse>();
			eventoClasseOutros = new ArrayList<EventoClasse>();
	
			eventoDataModel.setEventoLocal(eventoService);
			
			carregarListaClasses();
	}
	
	
	@Override
	public void reset() {
		eventoClasseManha.clear();
		eventoClasseTarde.clear();
		eventoClasseOutros.clear();
		eventosSelecionados = null;
	}

	public void carregarListaClasses() throws WebException{
		try{
			
			Classe filtro = new Classe();
			
			filtro.setCliente(new Cliente());
			
			filtro.getCliente().setIdCliente(FacesUtil.recuperarUsuarioSessao().getCliente().getIdCliente());
			filtro.setAno(Calendar.getInstance().get(Calendar.YEAR));
			
			listaClasses = (List<Classe>) classeService.listarClasses(filtro, null);
			
		} catch (ServicoException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("erro_carregar_listas"), FacesUtil.tratarMensagemErro(e)));
		}catch(Exception e){			
			throw new WebException(e.getMessage());
		}
	}
	
	
	public void carregarEventosClasses(){
		
		reset();
		
		if(!DadosUtil.isEmpty(classeSelecionada) && !DadosUtil.isEmpty(classeSelecionada.getIdClasse())){
				
			try {
				
				EventoClasse filtro = new EventoClasse();
				
				filtro.setClasse(new Classe());
				filtro.getClasse().setIdClasse(classeSelecionada.getIdClasse());
				
				List<EventoClasse> lista = (List<EventoClasse>) eventoClasseService.listarEventoClasses(filtro, null);
				
				for(EventoClasse evt:lista){
					
					switch(evt.getTipo()){
						
						case Constantes.TIPO_MANHA:{
							eventoClasseManha.add(evt);
							break;
						}
						
						case Constantes.TIPO_TARDE:{
							eventoClasseTarde.add(evt);
							break;
						}
						
						case Constantes.TIPO_OUTROS:{
							eventoClasseOutros.add(evt);
							break;
						}				
								
					}
					
				}
						
				
			} catch (ServicoException e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("erro_carregar_listas"), FacesUtil.tratarMensagemErro(e)));
			}catch(Exception e){			
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("erro_carregar_listas"), e.getMessage()));
			}
		
		}
		
	}
	
	
	public void inserirSelecionadosManha(){
		
		if(!DadosUtil.isEmpty(eventosSelecionados)){
			
			for(Evento evento:eventosSelecionados){
				
				EventoClasse eventoClasse = new EventoClasse();
				
				eventoClasse.setEvento(evento);
				eventoClasse.setClasse(classeSelecionada);
				eventoClasse.setTipo(Constantes.TIPO_MANHA);
				
				eventoClasseManha.add(eventoClasse);
			}
		}
		
	}
	
	public void excluirSelecionadosManha(EventoClasse eventoClasse){
			
		if(!DadosUtil.isEmpty(eventoClasseManha)){
			eventoClasseManha.remove(eventoClasse);
		}
	}

	public void inserirSelecionadosTarde(){
		
		if(!DadosUtil.isEmpty(eventosSelecionados)){
			
			for(Evento evento:eventosSelecionados){
				
				EventoClasse eventoClasse = new EventoClasse();
				
				eventoClasse.setEvento(evento);
				eventoClasse.setClasse(classeSelecionada);
				eventoClasse.setTipo(Constantes.TIPO_TARDE);
				
				eventoClasseTarde.add(eventoClasse);
			}
		}
		
	}
	
	public void excluirSelecionadosTarde(EventoClasse eventoClasse){
			
		if(!DadosUtil.isEmpty(eventoClasseTarde)){
			eventoClasseTarde.remove(eventoClasse);
		}
	}
	
	public void inserirSelecionadosOutros(){
		
		if(!DadosUtil.isEmpty(eventosSelecionados)){
			
			for(Evento evento:eventosSelecionados){
				
				EventoClasse eventoClasse = new EventoClasse();
				
				eventoClasse.setEvento(evento);
				eventoClasse.setClasse(classeSelecionada);
				eventoClasse.setTipo(Constantes.TIPO_OUTROS);
				
				eventoClasseOutros.add(eventoClasse);
			}
		}
		
	}
	
	public void excluirSelecionadosOutros(EventoClasse eventoClasse){
			
		if(!DadosUtil.isEmpty(eventoClasseOutros)){
			eventoClasseOutros.remove(eventoClasse);
		}
	}
	
	@Override
	public void pesquisar() {
		
		
	}

	@Override
	public void salvarImpl() throws ServicoException {
				
	}

	@Override
	public void alterarImpl() throws ServicoException {
		
		Set<EventoClasse> listaSalvar = new HashSet<EventoClasse>();
		
		listaSalvar.addAll(eventoClasseManha);
		listaSalvar.addAll(eventoClasseTarde);
		listaSalvar.addAll(eventoClasseOutros);
		

		eventoClasseService.atualizarEventoClasse(listaSalvar,classeSelecionada.getIdClasse());	
			
	}

	@Override
	public void excluirImpl() throws ServicoException {		
		
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


	public List<Classe> getListaClasses() {
		return listaClasses;
	}


	public void setListaClasses(List<Classe> listaClasses) {
		this.listaClasses = listaClasses;
	}


	public Classe getClasseSelecionada() {
		return classeSelecionada;
	}


	public void setClasseSelecionada(Classe classeSelecionada) {
		this.classeSelecionada = classeSelecionada;
	}


	public EventoClasseServiceLocal getEventoClasseService() {
		return eventoClasseService;
	}


	public void setEventoClasseService(EventoClasseServiceLocal eventoClasseService) {
		this.eventoClasseService = eventoClasseService;
	}


	public List<EventoClasse> getEventoClasseManha() {
		return eventoClasseManha;
	}


	public void setEventoClasseManha(List<EventoClasse> eventoClasseManha) {
	//	this.eventoClasseManha = eventoClasseManha;
	}


	public List<EventoClasse> getEventoClasseTarde() {
		return eventoClasseTarde;
	}


	public void setEventoClasseTarde(List<EventoClasse> eventoClasseTarde) {
		//this.eventoClasseTarde = eventoClasseTarde;
	}


	public List<EventoClasse> getEventoClasseOutros() {
		return eventoClasseOutros;
	}


	public void setEventoClasseOutros(List<EventoClasse> eventoClasseOutros) {
	//	this.eventoClasseOutros = eventoClasseOutros;
	}


	public Evento[] getEventosSelecionados() {
		return eventosSelecionados;
	}


	public void setEventosSelecionados(Evento[] eventosSelecionados) {
		this.eventosSelecionados = eventosSelecionados;
	}

	
}
