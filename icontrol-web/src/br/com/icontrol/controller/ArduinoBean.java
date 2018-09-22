package br.com.icontrol.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.DualListModel;

import br.com.icontrol.business.ArduinoServiceLocal;
import br.com.icontrol.business.CameraServiceLocal;
import br.com.icontrol.business.ClienteServiceLocal;
import br.com.icontrol.business.GenericServiceLocal;
import br.com.icontrol.controller.datamodel.ArduinoDataModel;
import br.com.icontrol.controller.datamodel.ClienteDataModel;
import br.com.icontrol.entity.Arduino;
import br.com.icontrol.entity.Camera;
import br.com.icontrol.entity.Cliente;
import br.com.icontrol.entity.Componente;
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
public class ArduinoBean extends AbstractViewHelper<Arduino> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -223264858794259737L;
	
	@EJB
	private ArduinoServiceLocal  arduinoService;
	
	@EJB
	private ClienteServiceLocal  clienteService;
	
	@EJB
	private CameraServiceLocal   cameraService;
	
	@EJB
	private GenericServiceLocal  generico;
	
	
	private ArduinoDataModel 		   arduinoDataModel;
	private ClienteDataModel 		   clienteDataModel;
	private DualListModel<Componente>  componentes;
	private DualListModel<Camera>  	   cameras;
	private List<Componente> 		   listaComponentes;
	private List<Camera> 		   	   listaCameras;
	private List<Cliente>			   listaClientes;
	

			
	
	@Override
	public void inicializar() throws WebException  {
		
	
		try {
			arduinoDataModel 		= new ArduinoDataModel();
			clienteDataModel		= new ClienteDataModel();
			componentes				= new DualListModel<Componente>();
			cameras					= new DualListModel<Camera>();
			filtro  				= new Arduino();	
			filtro.setCliente(new Cliente());
			listaComponentes 		= generico.obterEntidades(Componente.class);
			listaClientes			= generico.obterEntidades(Cliente.class);
			listaCameras			= (List<Camera>) cameraService.listarCamerasSemArduino();
			arduinoDataModel.setArduinoLocal(arduinoService);
			clienteDataModel.setClienteLocal(clienteService);
		} catch (ServicoException e) {
			e.printStackTrace();
			throw new WebException(e.getMessage());
		}
		
	}

	@Override
	public void pesquisar() {
		
		arduinoDataModel.setFiltro(filtro);
		
	}

	@Override
	public void reset() {
		setEntidade(new Arduino());	
	
		try {
			listaCameras			= (List<Camera>) cameraService.listarCamerasSemArduino();
		} catch (ServicoException e) {
			e.printStackTrace();
			FacesUtil.exibirErro(e);
		}
	}

	@Override
	public void salvarImpl() throws ServicoException {
		
		entidade.setComponentes(componentes.getTarget());
		entidade.setCameras(cameras.getTarget());
		
		arduinoService.salvarArduino(entidade,cameras.getSource());
		
		gravarLog("Arduino "+entidade.getCodigo(),Constantes.INCLUSAO);
		
		reset();
	}

	@Override
	public void alterarImpl() throws ServicoException {
		
		entidade.setComponentes(componentes.getTarget());
		entidade.setCameras(cameras.getTarget());
				
		arduinoService.alterarArduino(entidade,cameras.getSource());
		
		
		gravarLog("Arduino "+entidade.getCodigo(),Constantes.ALTERACAO);
		
		reset();
		
	}

	@Override
	public void excluirImpl() throws ServicoException {
		
		arduinoService.excluirArduino(entidade);
		
		gravarLog("Arduino "+entidade.getCodigo(),Constantes.EXCLUSAO);
			
		reset();
		
	}
	

	
	
	public void novo(){
		reset();
		componentes.setSource(listaComponentes);
		componentes.setTarget(new ArrayList<Componente>());
		
		cameras.setSource(listaCameras);
		cameras.setTarget(new ArrayList<Camera>());
	}
	
	public void editar(){
		
		List<Componente> listaSource = new ArrayList<Componente>(listaComponentes);
		componentes.setSource(listaSource);
		
		List<Camera> listaSourceCam = new ArrayList<Camera>(listaCameras);
		cameras.setSource(listaSourceCam);
		
		
		
		Arduino arduino = getEntidade();
		
		try {
			arduino = generico.obterListaLazy(arduino, Arduino.class, "componentes");	
					
			List<Componente> listComp = new ArrayList<Componente>(arduino.getComponentes());
			
			arduino = generico.obterListaLazy(arduino, Arduino.class, "cameras");		
			
			arduino.setComponentes(listComp);
			
			setEntidade(arduino);
			
		} catch (ServicoException e) {
			e.printStackTrace();
			FacesUtil.exibirErro(e);
		}
		
		componentes.setTarget(getEntidade().getComponentes());			
		componentes.getSource().removeAll(componentes.getTarget());
		
		cameras.setTarget(getEntidade().getCameras());			
		cameras.getSource().removeAll(cameras.getTarget());

		
	}
	

	public ArduinoServiceLocal getArduinoService() {
		return arduinoService;
	}

	public void setArduinoService(ArduinoServiceLocal componenteService) {
		this.arduinoService = componenteService;
	}

	public ArduinoDataModel getArduinoDataModel() {
		return arduinoDataModel;
	}

	public void setArduinoDataModel(ArduinoDataModel arduinoDataModel) {
		this.arduinoDataModel = arduinoDataModel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public DualListModel<Componente> getComponentes() {
		return componentes;
	}

	public void setComponentes(DualListModel<Componente> componentes) {
		this.componentes = componentes;
	}

	public List<Componente> getListaComponentes() {
		return listaComponentes;
	}

	public void setListaComponentes(List<Componente> listaComponentes) {
		this.listaComponentes = listaComponentes;
	}

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public ClienteDataModel getClienteDataModel() {
		return clienteDataModel;
	}

	public void setClienteDataModel(ClienteDataModel clienteDataModel) {
		this.clienteDataModel = clienteDataModel;
	}

	public DualListModel<Camera> getCameras() {
		return cameras;
	}

	public void setCameras(DualListModel<Camera> cameras) {
		this.cameras = cameras;
	}

	
}
