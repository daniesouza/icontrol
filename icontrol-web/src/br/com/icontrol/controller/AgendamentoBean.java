package br.com.icontrol.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.DualListModel;

import br.com.icontrol.business.AgendamentoServiceLocal;
import br.com.icontrol.business.AtalhoServiceLocal;
import br.com.icontrol.business.GenericServiceLocal;
import br.com.icontrol.controller.datamodel.AgendamentoDataModel;
import br.com.icontrol.entity.Agendamento;
import br.com.icontrol.entity.Atalho;
import br.com.icontrol.entity.Usuario;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.Constantes;
import br.com.icontrol.util.DadosUtil;
import br.com.icontrol.util.FacesUtil;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
 *      
 */

@ManagedBean
@ViewScoped
public class AgendamentoBean extends AbstractViewHelper<Agendamento> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5399583809606127722L;

	@EJB
	private AgendamentoServiceLocal agendamentoService;
	
	@EJB
	private AtalhoServiceLocal 	atalhoService;
	
	private AgendamentoDataModel agendamentoDataModel = new AgendamentoDataModel();
	
	@EJB
	private GenericServiceLocal 	generico;
			
	private DualListModel<Atalho>	   atalhos;
	
	private List<Atalho> listaAtalhos;
	
	@Override
	public void inicializar() {
		
		setAtalhos(new DualListModel<Atalho>());
		filtro  = new Agendamento();
		agendamentoDataModel.setAgendamentoLocal(agendamentoService);
		
		Usuario usuario = FacesUtil.recuperarUsuarioSessao();

		// SE O USUARIO FOR ADMIN GERAL NAO TERA UM CLIENTE ASSOCIADO
		if(!DadosUtil.isEmpty(usuario.getCliente())){				
			listarAtalhosUsuario();	
		}else{
			
//			try {	
//				atalhoService.listarAtalhos();
//			} catch (ServicoException e) {				
//				throw new WebException(e);
//			}
		}

	}

	private void listarAtalhosUsuario() {
		
		Usuario usuario = FacesUtil.recuperarUsuarioSessao();
		
		Atalho filtro = new Atalho();
		
		Usuario us = new Usuario();
		us.setId(usuario.getId());
		
		
		filtro.setUsuario(us);
		
		try {
			listaAtalhos = (List<Atalho>) atalhoService.listarAtalhos(filtro,new Paginacao(0));
		} catch (ServicoException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void pesquisar() {
		
		agendamentoDataModel.setFiltro(filtro);
		
	}

	@Override
	public void reset() {
		setEntidade(new Agendamento());	
		getEntidade().setUsuario(FacesUtil.recuperarUsuarioSessao());
		getEntidade().setCliente(FacesUtil.recuperarUsuarioSessao().getCliente());
	}
	
	public void novo(){
		reset();
		
		atalhos.setSource(new ArrayList<Atalho>(listaAtalhos));
		atalhos.setTarget(new ArrayList<Atalho>());

	}
	
	public void editar(){

		List<Atalho> listaSource = new ArrayList<Atalho>(listaAtalhos);
		atalhos.setSource(listaSource);
		
		Agendamento agendamento = getEntidade();
		
		try {
			agendamento = generico.obterListaLazy(agendamento, Agendamento.class, "atalhos");			
			setEntidade(agendamento);
			
		} catch (ServicoException e) {
			e.printStackTrace();
			FacesUtil.exibirErro(e);
		}
		
		atalhos.setTarget(getEntidade().getAtalhos());
			
		atalhos.getSource().removeAll(atalhos.getTarget());
	}

	@Override
	public void salvarImpl() throws ServicoException {

		entidade.setAtalhos(atalhos.getTarget());
		getEntidade().setAtivo(true);
		
		agendamentoService.salvarAgendamento(entidade);
		reset();
		
		gravarLog("Agendamento "+entidade.getNome(),Constantes.INCLUSAO);

	}
	

	@Override
	public void alterarImpl() throws ServicoException {

		entidade.setAtalhos(atalhos.getTarget());
		agendamentoService.alterarAgendamento(entidade);		
		reset();
		
		gravarLog("Agendamento "+entidade.getNome(),Constantes.ALTERACAO);

	}

	@Override
	public void excluirImpl() throws ServicoException {
		
		agendamentoService.excluirAgendamento(entidade);		
		reset();
		
		gravarLog("Agendamento "+entidade.getNome(),Constantes.EXCLUSAO);

		
	}

	public AgendamentoServiceLocal getAgendamentoService() {
		return agendamentoService;
	}

	public void setAgendamentoService(AgendamentoServiceLocal AgendamentoService) {
		this.agendamentoService = AgendamentoService;
	}

	public AgendamentoDataModel getAgendamentoDataModel() {
		return agendamentoDataModel;
	}

	public void setAgendamentoDataModel(AgendamentoDataModel AgendamentoDataModel) {
		this.agendamentoDataModel = AgendamentoDataModel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public DualListModel<Atalho> getAtalhos() {
		return atalhos;
	}

	public void setAtalhos(DualListModel<Atalho> atalhos) {
		this.atalhos = atalhos;
	}
	
	

}
