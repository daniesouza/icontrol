package br.com.icontrol.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.DualListModel;
import br.com.icontrol.business.ComponenteServiceLocal;
import br.com.icontrol.business.GenericServiceLocal;
import br.com.icontrol.controller.datamodel.ComponenteDataModel;
import br.com.icontrol.entity.Comando;
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
public class ComponenteBean extends AbstractViewHelper<Componente> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6276120468453992037L;

	@EJB
	private ComponenteServiceLocal  componenteService;
	@EJB
	private GenericServiceLocal 	generico;	
	private ComponenteDataModel 	componenteDataModel;
	private DualListModel<Comando>  comandos;
	private List<Comando> 			listaComandos;

			
	
	@Override
	public void inicializar() throws WebException  {
		
	
		try {
			componenteDataModel = new ComponenteDataModel();
			comandos			= new DualListModel<Comando>();
			filtro  			= new Componente();	
			listaComandos 		=  generico.obterEntidades(Comando.class);		
			componenteDataModel.setComponenteLocal(componenteService);
		} catch (ServicoException e) {
			e.printStackTrace();
			throw new WebException(e.getMessage());
		}
		
	}

	@Override
	public void pesquisar() {
		
		componenteDataModel.setFiltro(filtro);
		
	}

	@Override
	public void reset() {
		setEntidade(new Componente());	
	}

	@Override
	public void salvarImpl() throws ServicoException {
		
		entidade.setComandos(comandos.getTarget());		
		componenteService.salvarComponente(entidade);		
		reset();
		
		gravarLog("Componente "+entidade.getCodigo(),Constantes.INCLUSAO);

	}

	@Override
	public void alterarImpl() throws ServicoException {
		
		entidade.setComandos(comandos.getTarget());		
		componenteService.alterarComponente(entidade);		
		reset();
		
		gravarLog("Componente "+entidade.getCodigo(),Constantes.ALTERACAO);

		
	}

	@Override
	public void excluirImpl() throws ServicoException {
		
		componenteService.excluirComponente(entidade);		
		reset();
		
		gravarLog("Componente "+entidade.getCodigo(),Constantes.EXCLUSAO);

	}
	
	
	public void novo(){
		reset();
		comandos.setSource(new ArrayList<Comando>(listaComandos));
		comandos.setTarget(new ArrayList<Comando>());
	}
	
	public void editar(){
		
		List<Comando> listaSource = new ArrayList<Comando>(listaComandos);
		comandos.setSource(listaSource);
		
		Componente componente = getEntidade();
		
		try {
			componente = generico.obterListaLazy(componente, Componente.class, "comandos");			
			setEntidade(componente);
			
		} catch (ServicoException e) {
			e.printStackTrace();
			FacesUtil.exibirErro(e);
		}
		
		comandos.setTarget(getEntidade().getComandos());
			
		comandos.getSource().removeAll(comandos.getTarget());
		
	}

	public ComponenteServiceLocal getComponenteService() {
		return componenteService;
	}

	public void setComponenteService(ComponenteServiceLocal componenteService) {
		this.componenteService = componenteService;
	}

	public ComponenteDataModel getComponenteDataModel() {
		return componenteDataModel;
	}

	public void setComponenteDataModel(ComponenteDataModel componenteDataModel) {
		this.componenteDataModel = componenteDataModel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public DualListModel<Comando> getComandos() {
		return comandos;
	}

	public void setComandos(DualListModel<Comando> comandos) {
		this.comandos = comandos;
	}

	public List<Comando> getListaComandos() {
		return listaComandos;
	}

	public void setListaComandos(List<Comando> listaComandos) {
		this.listaComandos = listaComandos;
	}


}
