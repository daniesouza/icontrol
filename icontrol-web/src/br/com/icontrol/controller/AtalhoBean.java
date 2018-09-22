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
import br.com.icontrol.business.AtalhoServiceLocal;
import br.com.icontrol.business.ClienteServiceLocal;
import br.com.icontrol.business.GenericServiceLocal;
import br.com.icontrol.controller.datamodel.AtalhoDataModel;
import br.com.icontrol.entity.Arduino;
import br.com.icontrol.entity.Atalho;
import br.com.icontrol.entity.AtalhoArdCompComando;
import br.com.icontrol.entity.Cliente;
import br.com.icontrol.entity.Comando;
import br.com.icontrol.entity.Componente;
import br.com.icontrol.entity.Usuario;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.exceptions.WebException;
import br.com.icontrol.util.Constantes;
import br.com.icontrol.util.DadosUtil;
import br.com.icontrol.util.FacesUtil;
import br.com.icontrol.util.Icone;

/**
 * @author Icontrol
 *      
 */

@ManagedBean
@ViewScoped
public class AtalhoBean extends AbstractViewHelper<Atalho> implements Serializable{


	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1880605684804309302L;
	
	@EJB
	private GenericServiceLocal 	   generico;	

	@EJB
	private AtalhoServiceLocal 		   atalhoService;
	
	
	@EJB
	private ArduinoServiceLocal arduinoService;
	
	@EJB
	private ClienteServiceLocal		   clienteService;
	
	private AtalhoDataModel atalhoDataModel = new AtalhoDataModel();
	
	private DualListModel<Comando>	   comandos;
	private List<Componente> 		   listaComponentes;
	private List<Cliente>			   listaClientes;
	private List<Arduino> 			   arduinosAcesso;
	private Arduino					   arduino;
	private Componente 			  	   componente;
	private Icone					   iconeSelecionado;
	private List<Icone> listaIcones = new ArrayList<Icone>();
			
	
	@Override
	public void inicializar() throws WebException  {

			comandos 				= new DualListModel<Comando>();
			filtro  				= new Atalho();
			filtro.setCliente(new Cliente());
			listaComponentes 		= new ArrayList<Componente>();
			setEntidade(new Atalho());
			atalhoDataModel.setAtalhoLocal(atalhoService);
			
			listaIcones.add(new Icone("icone_block.png","Selecione..."));
			listaIcones.add(new Icone("Icone_LuzOn.png","LuzOn"));
			listaIcones.add(new Icone("Icone_LuzOff.png","LuzOff"));
			listaIcones.add(new Icone("Icone_Off.png","Off"));
			listaIcones.add(new Icone("Icone_On.png","On"));
			
			Usuario usuario = FacesUtil.recuperarUsuarioSessao();

			// SE O USUARIO FOR ADMIN GERAL NAO TERA UM CLIENTE ASSOCIADO
			if(!DadosUtil.isEmpty(usuario.getCliente())){				
				iniciarArduinosAcesso();	
			}else{
				
				try {	
					listaClientes = (List<Cliente>) clienteService.listarClientes();
				} catch (ServicoException e) {				
					throw new WebException(e);
				}
			}
		
	}
	
	
	public void iniciarArduinosAcesso() throws WebException{
		
		try {
			Usuario usuario = FacesUtil.recuperarUsuarioSessao();
			arduinosAcesso = (List<Arduino>) arduinoService.obterArduinosPermissoesPorUsuario(usuario);
		} catch (ServicoException e) {			
			throw new WebException(e);
		}
	}

	@Override
	public void pesquisar() {
		
		atalhoDataModel.setFiltro(filtro);
		
	}

	@Override
	public void reset() {
		setComponente(null);
		setArduino(null);
		comandos.getSource().clear();
		comandos.getTarget().clear();
		listaComponentes.clear();
	}

	@Override
	public void salvarImpl() throws ServicoException {
		
		Usuario usuario = FacesUtil.recuperarUsuarioSessao();
		
		getEntidade().setUsuario(usuario);
		getEntidade().setCliente(usuario.getCliente());
		getEntidade().setAtivo(true);

		if(!iconeSelecionado.getImagem().equals("icone_block.png")){
			entidade.setIcone(iconeSelecionado.getImagem());
		}
		
		atalhoService.salvarAtalho(getEntidade(), getEntidade().getAtalhosArdCompComando());
							
		gravarLog("Atalho "+entidade.getNome(),Constantes.INCLUSAO);


	}

	@Override
	public void alterarImpl() throws ServicoException {
		
		if(!iconeSelecionado.getImagem().equals("icone_block.png")){
			entidade.setIcone(iconeSelecionado.getImagem());
		}
		
		atalhoService.alterarAtalho(getEntidade(), getEntidade().getAtalhosArdCompComando());
		
		gravarLog("Atalho "+entidade.getNome(),Constantes.ALTERACAO);
		
	}

	@Override
	public void excluirImpl() throws ServicoException {
		
		atalhoService.excluirAtalho(entidade);
		
		gravarLog("Atalho "+entidade.getNome(),Constantes.EXCLUSAO);
		
		setEntidade(new Atalho());
		reset();
		
	}
	
	
	public void novo(){
		setEntidade(new Atalho());
		reset();
		iconeSelecionado = new Icone("icone_block.png","Selecione...");

	}
	

	public void novoViaAdmin(){
//		setEntidade(new Atalho());
//		reset();
//		
//		Usuario u = FacesUtil.recuperarUsuarioSessao();
//		
//		getEntidade().setCliente(u.getCliente());
//		
//		popularArduinos(u.getCliente());
//		arduinos.setSource(new ArrayList<Arduino>(listaArduinos));
//		popularUsuarios(u.getCliente());
//		usuarios.setSource(new ArrayList<Usuario>(listaUsuarios));	
		
		
	}
	
	public void editar(){
		
		FacesMessage msg = null;
		try {
			reset();
			Atalho atalho = getEntidade();
			List<AtalhoArdCompComando> modulosFuncionalidade = atalhoService.obterAtalhoArdCompComando(atalho.getId());
			atalho.setAtalhosArdCompComando(modulosFuncionalidade);
			
			setEntidade(atalho);

			iconeSelecionado.setImagem(entidade.getIcone());

		} catch (ServicoException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "");
			e.printStackTrace();
		}
		
        if( msg != null ) {
        	FacesContext.getCurrentInstance().addMessage(null, msg);
        }			
		
	}
	
	
	public void popularComponentes() {
		try {
			if(arduino != null){
				listaComponentes = new ArrayList<Componente>(arduino.getComponentes());
			}else{
				listaComponentes.clear();			
			}
			comandos = new DualListModel<Comando>();

			componente = null;
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.exibirErro(e);
		}
	}

	
	public void popularComando() {
		try {
			
			if(this.componente != null){
			
				List<AtalhoArdCompComando> grupoArdCompPermissoes = getEntidade().getAtalhosArdCompComando();
				List<Comando> comandosGrupo = new ArrayList<Comando>();
				
				for (AtalhoArdCompComando grupoArdCompPermissao : grupoArdCompPermissoes) {
					if (grupoArdCompPermissao.getComponente().equals(this.componente) && grupoArdCompPermissao.getArduino().equals(this.arduino) ) {
						
						comandosGrupo.add(grupoArdCompPermissao.getComando());
					}
				}

				List<Comando> comandoComponente = componente.getComandos();
				
				List<Comando> componentesNaoInclusos = new ArrayList<Comando>(comandoComponente);
				componentesNaoInclusos.removeAll(comandosGrupo);
				comandos.setSource(componentesNaoInclusos);
				comandos.setTarget(comandosGrupo);
			}else{
				comandos = new DualListModel<Comando>();
			}
		} catch (Exception e) {
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
		Atalho atalho = getEntidade();
		
		List<AtalhoArdCompComando> listaRemove = new ArrayList<AtalhoArdCompComando>();
		
		List<AtalhoArdCompComando> gruposArdCompPermissoes = getEntidade().getAtalhosArdCompComando();
				
		if(gruposArdCompPermissoes != null){
			
			for (Comando comando : comandos) {
				AtalhoArdCompComando grupoArdCompPermissoes = new AtalhoArdCompComando();
				grupoArdCompPermissoes.setComando(comando);
				grupoArdCompPermissoes.setComponente(componente);
				grupoArdCompPermissoes.setArduino(arduino);
				grupoArdCompPermissoes.setAtalho(atalho);
				boolean contemGrupoArd = false;
				for (AtalhoArdCompComando grupoPerm : gruposArdCompPermissoes) {
					if (grupoArdCompPermissoes.equals(grupoPerm)) {
						contemGrupoArd = true;
					}
				}
				if(!contemGrupoArd) {
					gruposArdCompPermissoes.add(grupoArdCompPermissoes);
				}
			}

				
			for (AtalhoArdCompComando grupoArdCompPermissoes : gruposArdCompPermissoes) {
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
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Componente> getListaComponentes() {
		return listaComponentes;
	}

	public void setListaComponentes(List<Componente> listaComponentes) {
		this.listaComponentes = listaComponentes;
	}

	public GenericServiceLocal getGenerico() {
		return generico;
	}

	public void setGenerico(GenericServiceLocal generico) {
		this.generico = generico;
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


	public AtalhoServiceLocal getAtalhoService() {
		return atalhoService;
	}


	public void setAtalhoService(AtalhoServiceLocal atalhoService) {
		this.atalhoService = atalhoService;
	}


	public ArduinoServiceLocal getArduinoService() {
		return arduinoService;
	}


	public void setArduinoService(ArduinoServiceLocal arduinoService) {
		this.arduinoService = arduinoService;
	}


	public ClienteServiceLocal getClienteService() {
		return clienteService;
	}


	public void setClienteService(ClienteServiceLocal clienteService) {
		this.clienteService = clienteService;
	}




	public List<Cliente> getListaClientes() {
		return listaClientes;
	}


	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}


	public List<Arduino> getArduinosAcesso() {
		return arduinosAcesso;
	}


	public void setArduinosAcesso(List<Arduino> arduinosAcesso) {
		this.arduinosAcesso = arduinosAcesso;
	}


	public AtalhoDataModel getAtalhoDataModel() {
		return atalhoDataModel;
	}


	public void setAtalhoDataModel(AtalhoDataModel atalhoDataModel) {
		this.atalhoDataModel = atalhoDataModel;
	}


	public Icone getIconeSelecionado() {
		return iconeSelecionado;
	}


	public void setIconeSelecionado(Icone iconeSelecionado) {
		this.iconeSelecionado = iconeSelecionado;
	}


	public List<Icone> getListaIcones() {
		return listaIcones;
	}


	public void setListaIcones(List<Icone> listaIcones) {
		this.listaIcones = listaIcones;
	}


	

}
