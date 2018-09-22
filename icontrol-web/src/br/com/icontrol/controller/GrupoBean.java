package br.com.icontrol.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DualListModel;

import br.com.icontrol.business.ClienteServiceLocal;
import br.com.icontrol.business.GenericServiceLocal;
import br.com.icontrol.business.GrupoServiceLocal;
import br.com.icontrol.controller.datamodel.ClienteDataModel;
import br.com.icontrol.controller.datamodel.GrupoDataModel;
import br.com.icontrol.entity.Arduino;
import br.com.icontrol.entity.Cliente;
import br.com.icontrol.entity.Comando;
import br.com.icontrol.entity.Componente;
import br.com.icontrol.entity.Grupo;
import br.com.icontrol.entity.GrupoArdCompPermissoes;
import br.com.icontrol.entity.Usuario;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.exceptions.WebException;
import br.com.icontrol.util.Constantes;
import br.com.icontrol.util.DadosUtil;
import br.com.icontrol.util.FacesUtil;

/**
 * @author Icontrol
 *      
 */

@ManagedBean
@ViewScoped
public class GrupoBean extends AbstractViewHelper<Grupo> implements Serializable{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2385055728959112391L;
	
	
	@EJB
	private GrupoServiceLocal  		   grupoService;
	@EJB
	private GenericServiceLocal 	   generico;	
	@EJB
	private ClienteServiceLocal 	   clienteService;
	
	private GrupoDataModel 		  	   grupoDataModel;
	private DualListModel<Comando>	   comandos;
	private DualListModel<Usuario>	   usuarios;
	private DualListModel<Arduino>     arduinos;
	private ClienteDataModel 		   clienteDataModel;
	private List<Componente> 		   listaComponentes;
	private List<Arduino> 		       listaArduinos;
	private List<Usuario> 		       listaUsuarios;
	private Arduino					   arduino;
	private Componente 			  	   componente;
	private boolean habilitarEdicaoCLiente = true;
			
	
	@Override
	public void inicializar() throws WebException  {
		
			grupoDataModel 			= new GrupoDataModel();
			clienteDataModel		= new ClienteDataModel();
			arduinos				= new DualListModel<Arduino>();
			comandos 				= new DualListModel<Comando>();
			usuarios 				= new DualListModel<Usuario>();
			filtro  				= new Grupo();
			filtro.setCliente(new Cliente());
			listaComponentes 		= new ArrayList<Componente>();
			grupoDataModel.setGrupoLocal(grupoService);	
			clienteDataModel.setClienteLocal(clienteService);
			setEntidade(new Grupo());
		
	}

	@Override
	public void pesquisar() {
		
		grupoDataModel.setFiltro(filtro);
		
	}

	@Override
	public void reset() {
		setComponente(null);
		setArduino(null);
		comandos.getSource().clear();
		comandos.getTarget().clear();
		usuarios.getSource().clear();
		usuarios.getTarget().clear();
		arduinos.getSource().clear();
		arduinos.getTarget().clear();
		listaComponentes.clear();
	}

	@Override
	public void salvarImpl() throws ServicoException {
						
		grupoService.salvarGrupo(getEntidade(), getEntidade().getGruposArdCompPermissoes(), this.arduinos.getTarget(), this.usuarios.getTarget());
		
		gravarLog("Grupo "+entidade.getNome(),Constantes.INCLUSAO);


	}

	@Override
	public void alterarImpl() throws ServicoException {
			
		grupoService.alterarGrupo(getEntidade(), getEntidade().getGruposArdCompPermissoes(), this.arduinos.getTarget(), this.usuarios.getTarget());
		
		gravarLog("Grupo "+entidade.getNome(),Constantes.ALTERACAO);
		
	}

	@Override
	public void excluirImpl() throws ServicoException {
		
		grupoService.excluirGrupo(entidade);
		
		gravarLog("Grupo "+entidade.getNome(),Constantes.EXCLUSAO);
		
		reset();
		
	}
	
	
	public void novo(){
		setEntidade(new Grupo());
		reset();
		habilitarEdicaoCLiente=true;
	}
	
	public void novoViaAdmin(){
		setEntidade(new Grupo());
		reset();
		
		Usuario u = FacesUtil.recuperarUsuarioSessao();
		
		getEntidade().setCliente(u.getCliente());
		
		popularArduinos(u.getCliente());
		arduinos.setSource(new ArrayList<Arduino>(listaArduinos));
		popularUsuarios(u.getCliente());
		usuarios.setSource(new ArrayList<Usuario>(listaUsuarios));	
		
		
	}
	
	public void editar(){
		
		FacesMessage msg = null;
		try {
			reset();
			Grupo grupo = getEntidade();
			grupo = generico.obterListaLazy(grupo, Grupo.class, "arduinos");
			List<GrupoArdCompPermissoes> modulosFuncionalidade = grupoService.obterGrupoArdCompPermissoes(grupo.getId());
			grupo.setGruposArdCompPermissoes(modulosFuncionalidade);
			List<Usuario> lista = ((Grupo)generico.obterListaLazy(grupo, Grupo.class, "usuarios")).getUsuarios();
			
			setEntidade(grupo);
			
			popularArduinos(entidade.getCliente());
			popularUsuarios(entidade.getCliente());
			
			usuarios.setSource(new ArrayList<Usuario>(listaUsuarios));		
			usuarios.getSource().removeAll(lista);
			usuarios.setTarget(lista);
			
			
			List<Arduino> arduinos = grupo.getArduinos();

			this.arduinos.setSource(new ArrayList<Arduino>(listaArduinos));
			this.arduinos.getSource().removeAll(arduinos);
			this.arduinos.setTarget(arduinos);	

			if(!DadosUtil.isEmpty(entidade.getCliente())){
				habilitarEdicaoCLiente = false;
			}else{
				habilitarEdicaoCLiente = true;
			}

		} catch (ServicoException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "");
			e.printStackTrace();
		}
		
        if( msg != null ) {
        	FacesContext.getCurrentInstance().addMessage(null, msg);
        }			
		
	}
	
	public void popularPickList(){
		reset();
		popularArduinos(entidade.getCliente());
		arduinos.setSource(new ArrayList<Arduino>(listaArduinos));
		popularUsuarios(entidade.getCliente());
		usuarios.setSource(new ArrayList<Usuario>(listaUsuarios));	
		
	}
	
	public void popularArduinos(Cliente cliente){
		
		try {
			if(!DadosUtil.isEmpty(cliente)){
				Cliente c = generico.obterListaLazy(cliente, Cliente.class, "arduinos");			
				listaArduinos 	= c.getArduinos();
			}else{
				listaArduinos = new ArrayList<Arduino>();
			}
			
		} catch (ServicoException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("erro_carregar_listas"), FacesUtil.tratarMensagemErro(e)));
		}
	}
	
	public void popularUsuarios(Cliente cliente){
		
		try {
			if(!DadosUtil.isEmpty(cliente)){
				Cliente c	= generico.obterListaLazy(cliente, Cliente.class, "usuarios");			
				listaUsuarios = c.getUsuarios();
			}else{
				listaUsuarios = new ArrayList<Usuario>();
			}
		} catch (ServicoException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("erro_carregar_listas"), FacesUtil.tratarMensagemErro(e)));
		}
	}
	
	public void popularComponentes() {
		try {
			if(arduino != null){
				arduino = generico.obterListaLazy(arduino, Arduino.class, "componentes");
				listaComponentes = arduino.getComponentes();
			}else{
				listaComponentes.clear();			
			}
			comandos = new DualListModel<Comando>();
			componente = null;

		} catch (ServicoException e) {
			e.printStackTrace();
			FacesUtil.exibirErro(e);
		}
	}

	
	public void popularComando() {
		try {
			
			if(this.componente != null){
			
				List<GrupoArdCompPermissoes> grupoArdCompPermissoes = getEntidade().getGruposArdCompPermissoes();
				List<Comando> comandosGrupo = new ArrayList<Comando>();
				
				for (GrupoArdCompPermissoes grupoArdCompPermissao : grupoArdCompPermissoes) {
					if (grupoArdCompPermissao.getComponente().equals(this.componente) && grupoArdCompPermissao.getArduino().equals(this.arduino) ) {
						
						comandosGrupo.add(grupoArdCompPermissao.getComando());
					}
				}
				
				Componente componenteAux = generico.obterListaLazy(this.componente, Componente.class, "comandos");
				List<Comando> comandoComponente = componenteAux.getComandos();
				
				List<Comando> componentesNaoInclusos = new ArrayList<Comando>(comandoComponente);
				componentesNaoInclusos.removeAll(comandosGrupo);
				comandos.setSource(componentesNaoInclusos);
				comandos.setTarget(comandosGrupo);
			}else{
				comandos = new DualListModel<Comando>();
			}
		} catch (ServicoException e) {
			e.printStackTrace();
			FacesUtil.exibirErro(e);
		}
	}
	
	public void setComandos(DualListModel<Comando> comandos) {
		this.comandos = comandos;
	}
	
	public void atualizarComandos(){
		atualizarGrupoComando(this.arduino, this.componente, comandos.getTarget());
	}
	
	
	private void atualizarGrupoComando(Arduino arduino, Componente componente, List<Comando> comandos) {
		Grupo grupo = getEntidade();
		
		List<GrupoArdCompPermissoes> listaRemove = new ArrayList<GrupoArdCompPermissoes>();
		List<GrupoArdCompPermissoes> gruposArdCompPermissoes = grupo.getGruposArdCompPermissoes();
		
		if(gruposArdCompPermissoes != null){
			
			for (Comando comando : comandos) {
				GrupoArdCompPermissoes grupoArdCompPermissoes = new GrupoArdCompPermissoes();
				grupoArdCompPermissoes.setComando(comando);
				grupoArdCompPermissoes.setComponente(componente);
				grupoArdCompPermissoes.setArduino(arduino);
				grupoArdCompPermissoes.setGrupo(grupo);
				boolean contemGrupoArd = false;
				for (GrupoArdCompPermissoes grupoPerm : gruposArdCompPermissoes) {
					if (grupoArdCompPermissoes.equals(grupoPerm)) {
						contemGrupoArd = true;
					}
				}
				if(!contemGrupoArd) {
					gruposArdCompPermissoes.add(grupoArdCompPermissoes);
				}
			}
		
			for (GrupoArdCompPermissoes grupoArdCompPermissoes : gruposArdCompPermissoes) {
				if( !grupoArdCompPermissoes.getArduino().equals(arduino) || !grupoArdCompPermissoes.getComponente().equals(componente) ) {
					continue;
				}
				
				boolean possuiComando = false;
				for (Comando comando : comandos) {
					if(grupoArdCompPermissoes.getComando().equals(comando)) {
						possuiComando = true;
					}
				}
				if (!possuiComando) {
					listaRemove.add(grupoArdCompPermissoes);			
				}
			}
			
			if(!listaRemove.isEmpty()){
				gruposArdCompPermissoes.removeAll(listaRemove);			
			}
		}
	}
	
	public void zerarComponentesComandos(){
		
		setComponente(null);
		setArduino(null);
		comandos.getSource().clear();
		comandos.getTarget().clear();
		listaComponentes.clear();
	}
	
	public GrupoServiceLocal getGrupoService() {
		return grupoService;
	}

	public void setGrupoService(GrupoServiceLocal componenteService) {
		this.grupoService = componenteService;
	}

	public GrupoDataModel getGrupoDataModel() {
		return grupoDataModel;
	}

	public void setGrupoDataModel(GrupoDataModel GrupoDataModel) {
		this.grupoDataModel = GrupoDataModel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Componente> getListaComponentes() {
		return listaComponentes;
	}

	public void setListaComponentes(List<Componente> listaComponentes) {
		this.listaComponentes = listaComponentes;
	}

	public GrupoServiceLocal getComponenteService() {
		return grupoService;
	}

	public void setComponenteService(GrupoServiceLocal componenteService) {
		this.grupoService = componenteService;
	}

	public GenericServiceLocal getGenerico() {
		return generico;
	}

	public void setGenerico(GenericServiceLocal generico) {
		this.generico = generico;
	}

	public DualListModel<Arduino> getArduinos() {
		return arduinos;
	}

	public void setArduinos(DualListModel<Arduino> arduinos) {
		this.arduinos = arduinos;
	}


	public Arduino getArduino() {
		return arduino;
	}

	public void setArduino(Arduino arduino) {
		this.arduino = arduino;
	}

	public Componente getComponente() {
		return componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	public DualListModel<Comando> getComandos() {
		return comandos;
	}

	public DualListModel<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(DualListModel<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public ClienteDataModel getClienteDataModel() {
		return clienteDataModel;
	}

	public void setClienteDataModel(ClienteDataModel clienteDataModel) {
		this.clienteDataModel = clienteDataModel;
	}

	public boolean isHabilitarEdicaoCLiente() {
		return habilitarEdicaoCLiente;
	}

	public void setHabilitarEdicaoCLiente(boolean habilitarEdicaoCLiente) {
		this.habilitarEdicaoCLiente = habilitarEdicaoCLiente;
	}

	

}
