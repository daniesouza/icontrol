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

import br.com.icontrol.business.ArduinoServiceLocal;
import br.com.icontrol.business.ClienteServiceLocal;
import br.com.icontrol.business.GenericServiceLocal;
import br.com.icontrol.business.UsuarioServiceLocal;
import br.com.icontrol.controller.datamodel.ClienteDataModel;
import br.com.icontrol.entity.Arduino;
import br.com.icontrol.entity.Cliente;
import br.com.icontrol.entity.Usuario;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.exceptions.WebException;
import br.com.icontrol.util.Constantes;
import br.com.icontrol.util.FacesUtil;

/**
 * @author Icontrol
 *      
 */

@ManagedBean
@ViewScoped
public class ClienteBean extends AbstractViewHelper<Cliente> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3725699119385532894L;

	@EJB
	private ClienteServiceLocal clienteService;
	
	@EJB
	private ArduinoServiceLocal arduinoService;
	
	@EJB
	private UsuarioServiceLocal usuarioService;
	
	@EJB
	private GenericServiceLocal servicoGenerico;
	
	private ClienteDataModel clienteDataModel = new ClienteDataModel();
	
	private DualListModel<Usuario>	   usuarios;
	private DualListModel<Arduino>     arduinos;
	private List<Arduino> 		       listaArduinos;
	private List<Usuario> 		       listaUsuarios;

			
	
	@Override
	public void inicializar() throws WebException {
		
		
			setEntidade(new Cliente());	
			setFiltro(new Cliente());
			clienteDataModel.setClienteLocal(clienteService);
			arduinos				= new DualListModel<Arduino>();
			usuarios 				= new DualListModel<Usuario>();

	}
	
	public void carregarListaUsuariosArduinos(){
		try{
			listaArduinos 			= (List<Arduino>) arduinoService.listarArduinosSemCliente();
			listaUsuarios			= (List<Usuario>) usuarioService.listarUsuariosSemClienteSemAdmin();
	
		} catch (ServicoException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("erro_carregar_listas"), FacesUtil.tratarMensagemErro(e)));
		}
	}

	@Override
	public void pesquisar() {
		
		clienteDataModel.setFiltro(filtro);
		
	}

	@Override
	public void reset() {
		setEntidade(new Cliente());	
	}
	
	public void novo(){
		
		carregarListaUsuariosArduinos();
		
		arduinos.setSource(listaArduinos);
		arduinos.setTarget(new ArrayList<Arduino>());
		usuarios.setSource(listaUsuarios);
		usuarios.setTarget(new ArrayList<Usuario>());

		
		reset();
	}
	
	public void editar(){
		
		try {

			carregarListaUsuariosArduinos();
			arduinos.setSource(listaArduinos);
			usuarios.setSource(listaUsuarios);
			
			entidade = servicoGenerico.obterListaLazy(entidade, Cliente.class, "arduinos");
			arduinos.setTarget(new ArrayList<Arduino>(entidade.getArduinos()));
			entidade = servicoGenerico.obterListaLazy(entidade, Cliente.class, "usuarios");
			usuarios.setTarget(new ArrayList<Usuario>(entidade.getUsuarios()));
		
		} catch (ServicoException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("erro_carregar_listas"), FacesUtil.tratarMensagemErro(e)));
		}
		
	}

	@Override
	public void salvarImpl() throws ServicoException {
		
		entidade.setArduinos(arduinos.getTarget());
		entidade.setUsuarios(usuarios.getTarget());
		
		clienteService.salvarCliente(entidade,usuarios.getSource(),arduinos.getSource());	
		
		gravarLog("Cliente "+entidade.getCpfCnpj(),Constantes.INCLUSAO);
		
	}

	@Override
	public void alterarImpl() throws ServicoException {
		
		entidade.setArduinos(arduinos.getTarget());
		entidade.setUsuarios(usuarios.getTarget());
		
		clienteService.alterarCliente(entidade,usuarios.getSource(),arduinos.getSource());	
		
		gravarLog("Cliente "+entidade.getCpfCnpj(),Constantes.ALTERACAO);
		
	}

	@Override
	public void excluirImpl() throws ServicoException {
		
		clienteService.excluirCliente(entidade);
		
		gravarLog("Cliente "+entidade.getCpfCnpj(),Constantes.EXCLUSAO);
		
		reset();
		
	}

	public ClienteServiceLocal getClienteService() {
		return clienteService;
	}

	public void setClienteService(ClienteServiceLocal ClienteService) {
		this.clienteService = ClienteService;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ClienteDataModel getClienteDataModel() {
		return clienteDataModel;
	}

	public void setClienteDataModel(ClienteDataModel clienteDataModel) {
		this.clienteDataModel = clienteDataModel;
	}

	public DualListModel<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(DualListModel<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public DualListModel<Arduino> getArduinos() {
		return arduinos;
	}

	public void setArduinos(DualListModel<Arduino> arduinos) {
		this.arduinos = arduinos;
	}
	

}
