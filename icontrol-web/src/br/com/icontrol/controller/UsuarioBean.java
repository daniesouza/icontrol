package br.com.icontrol.controller;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
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
import br.com.icontrol.business.UsuarioServiceLocal;
import br.com.icontrol.controller.datamodel.ClienteDataModel;
import br.com.icontrol.controller.datamodel.UsuarioDataModel;
import br.com.icontrol.entity.Cliente;
import br.com.icontrol.entity.Grupo;
import br.com.icontrol.entity.Usuario;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.security.MD5CheckSum;
import br.com.icontrol.util.Constantes;
import br.com.icontrol.util.DadosUtil;
import br.com.icontrol.util.FacesUtil;

/**
 * @author Icontrol
 *      
 */

@ManagedBean
@ViewScoped
public class UsuarioBean extends AbstractViewHelper<Usuario> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6591098012361011680L;

	@EJB
	private UsuarioServiceLocal  usuarioService;
	@EJB
	private ClienteServiceLocal  clienteService;
	@EJB
	private GenericServiceLocal  generico;
	
	private UsuarioDataModel 	 usuarioDataModel;
	private ClienteDataModel	 clienteDataModel;
	private DualListModel<Grupo> grupos;
	private List<Grupo>			 listaGrupos;
	@EJB
	private GenericServiceLocal servicoGenerico;
			
	
	@Override
	public void inicializar() {
		
		filtro  = new Usuario();
		filtro.setCliente(new Cliente());		
		usuarioDataModel = new UsuarioDataModel();
		usuarioDataModel.setUsuarioLocal(usuarioService);
		clienteDataModel		= new ClienteDataModel();
		clienteDataModel.setClienteLocal(clienteService);
		grupos = new DualListModel<Grupo>();
		setEntidade(new Usuario());
	}

	@Override
	public void pesquisar() {
		
		usuarioDataModel.setFiltro(filtro);
		
	}

	@Override
	public void reset() {
		grupos.getSource().clear();
		grupos.getTarget().clear();
	}

	@Override
	public void salvarImpl() throws ServicoException {
		
		if(entidade.getAutoridade().equals(Constantes.ADMINISTRADOR)){
			entidade.setCliente(null);
		}
		
		try {
			entidade.setSenha(MD5CheckSum.gerarSenhaHash(entidade.getSenha()));
			
		} catch (NoSuchAlgorithmException e) {
			throw new ServicoException(e);
		}
		
		usuarioService.salvarUsuario(entidade);

		salvarGrupos();
		
		gravarLog("Usuario "+entidade.getUsuario(),Constantes.INCLUSAO);

	}

	@Override
	public void alterarImpl() throws ServicoException {
		
		if(entidade.getAutoridade().equals(Constantes.ADMINISTRADOR)){
			entidade.setCliente(null);
		}
		
		usuarioService.alterarUsuario(entidade);
		
		salvarGrupos();
		
		gravarLog("Usuario "+entidade.getUsuario(),Constantes.ALTERACAO);

			
	}
	
	
	private void salvarGrupos() throws ServicoException{
	
			for(Grupo grupo:entidade.getGrupos()){
				
				grupo = generico.obterListaLazy(grupo, Grupo.class, "usuarios");
				
				grupo.getUsuarios().remove(entidade);
				servicoGenerico.atualizar(grupo);
			}
			
			
			if(!entidade.getAutoridade().equals(Constantes.ADMINISTRADOR)){
				

				for(Grupo grupo:grupos.getSource()){

					grupo = generico.obterListaLazy(grupo, Grupo.class, "usuarios");
					
					grupo.getUsuarios().remove(entidade);
					servicoGenerico.atualizar(grupo);
				}
				
				for(Grupo grupo:grupos.getTarget()){
					
					grupo = generico.obterListaLazy(grupo, Grupo.class, "usuarios");
					
					if(!grupo.getUsuarios().contains(entidade)){
						grupo.getUsuarios().add(entidade);
					}
					servicoGenerico.atualizar(grupo);
				}
				
			}
			

		
	}

	@Override
	public void excluirImpl() throws ServicoException {
		
		usuarioService.excluirUsuario(entidade);
		
		gravarLog("Usuario "+entidade.getUsuario(),Constantes.EXCLUSAO);
		
		reset();

		
	}
	
	
	public void editar() {
		FacesMessage msg = null;
		try {
			Usuario usuario = getEntidade();
			usuario = servicoGenerico.obterListaLazy(usuario, Usuario.class, "grupos");
			setEntidade(usuario);
			List<Grupo> gruposUsuario = usuario.getGrupos();
			popularGrupos(usuario.getCliente());
			this.grupos.setSource(new ArrayList<Grupo>(listaGrupos));
			this.grupos.getSource().removeAll(gruposUsuario);
			this.grupos.setTarget(new ArrayList<Grupo>(gruposUsuario));
			
			
		} catch (ServicoException e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "");
			e.printStackTrace();
		}
		
        if( msg != null ) {
        	FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
	}
	
	public void novo() {
		reset();
		setEntidade(new Usuario());
		getEntidade().setAtivo(false);
	}
	
	public void novoUsuarioCliente() {
		
		Usuario usuario = FacesUtil.recuperarUsuarioSessao();		
		popularGrupos(usuario.getCliente());		
		setEntidade(new Usuario());
		getEntidade().setAtivo(false);
		grupos.setSource(new ArrayList<Grupo>(listaGrupos));
		grupos.getTarget().clear();
	}
	
	
	public void ativarDesativar() {
		FacesMessage msg = null;

			try {
				String chaveMsg;
				if (entidade.getAtivo()) {
					entidade.setAtivo(false);
					usuarioService.alterarUsuario(entidade);
					chaveMsg = "mensagem_usuario_desativado";
				} else {
					entidade.setAtivo(true);
					usuarioService.alterarUsuario(entidade);
					chaveMsg = "mensagem_usuario_ativado";
				}
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString(chaveMsg), "");
			} catch (ServicoException e) {
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO, FacesUtil.tratarMensagemErro(e), "");
				e.printStackTrace();
			}
		
		if( msg != null ) {
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}		
	}
	
	public void popularPickList(){
		reset();
		popularGrupos(entidade.getCliente());
		grupos.setSource(new ArrayList<Grupo>(listaGrupos));
		
	}
	
	public void popularGrupos(Cliente cliente){
		
		try {
			
			if(!DadosUtil.isEmpty(cliente)){
				Cliente c = generico.obterListaLazy(cliente, Cliente.class, "grupos");
				listaGrupos 	= c.getGrupos();
			}
			else{
				listaGrupos = new ArrayList<Grupo>();
			}
			
			
			
		} catch (ServicoException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("erro_carregar_listas"), FacesUtil.tratarMensagemErro(e)));
		}
	}
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public UsuarioServiceLocal getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioServiceLocal usuarioService) {
		this.usuarioService = usuarioService;
	}

	public UsuarioDataModel getUsuarioDataModel() {
		return usuarioDataModel;
	}

	public void setUsuarioDataModel(UsuarioDataModel usuarioDataModel) {
		this.usuarioDataModel = usuarioDataModel;
	}

	public DualListModel<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(DualListModel<Grupo> grupos) {
		this.grupos = grupos;
	}

	public GenericServiceLocal getServicoGenerico() {
		return servicoGenerico;
	}

	public void setServicoGenerico(GenericServiceLocal servicoGenerico) {
		this.servicoGenerico = servicoGenerico;
	}

	public ClienteDataModel getClienteDataModel() {
		return clienteDataModel;
	}

	public void setClienteDataModel(ClienteDataModel clienteDataModel) {
		this.clienteDataModel = clienteDataModel;
	}


	
	

}
