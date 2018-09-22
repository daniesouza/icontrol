package br.com.icontrol.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.icontrol.business.ComandoServiceLocal;
import br.com.icontrol.controller.datamodel.ComandoDataModel;
import br.com.icontrol.entity.Comando;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.Constantes;
import br.com.icontrol.util.Icone;

/**
 * @author Icontrol
 *      
 */

@ManagedBean
@ViewScoped
public class ComandoBean extends AbstractViewHelper<Comando> implements Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = -5300994858688793669L;

	@EJB
	private ComandoServiceLocal comandoService;
	
	private ComandoDataModel comandoDataModel = new ComandoDataModel();
	
	private Icone iconeSelecionado;
	private List<Icone> listaIcones = new ArrayList<Icone>();

			
	
	@Override
	public void inicializar() {
		
		filtro  = new Comando();
		comandoDataModel.setComandoLocal(comandoService);
		listaIcones.add(new Icone("icone_block.png","Selecione..."));
		listaIcones.add(new Icone("Icone_LuzOn.png","LuzOn"));
		listaIcones.add(new Icone("Icone_LuzOff.png","LuzOff"));
		listaIcones.add(new Icone("Icone_Off.png","Off"));
		listaIcones.add(new Icone("Icone_On.png","On"));
		listaIcones.add(new Icone("mais.png","Mais"));
		listaIcones.add(new Icone("menos.png","Menos"));
		
	}

	@Override
	public void pesquisar() {
		
		comandoDataModel.setFiltro(filtro);
		
	}

	@Override
	public void reset() {
		setEntidade(new Comando());	
	}
	
	public void novo(){
		reset();
		iconeSelecionado = new Icone("icone_block.png","Selecione...");

	}
	
	public void editar(){
		iconeSelecionado = new Icone("","");
		iconeSelecionado.setImagem(entidade.getIcone());
	}

	@Override
	public void salvarImpl() throws ServicoException {
		
		if(!iconeSelecionado.getImagem().equals("icone_block.png")){
			entidade.setIcone(iconeSelecionado.getImagem());
		}
		comandoService.salvarComando(entidade);
		reset();
		
		gravarLog("Comando "+entidade.getCodigo(),Constantes.INCLUSAO);

	}
	

	@Override
	public void alterarImpl() throws ServicoException {
		
		if(!iconeSelecionado.getImagem().equals("icone_block.png")){
			entidade.setIcone(iconeSelecionado.getImagem());
		}
		comandoService.alterarComando(entidade);		
		reset();
		
		gravarLog("Comando "+entidade.getCodigo(),Constantes.ALTERACAO);

	}

	@Override
	public void excluirImpl() throws ServicoException {
		
		comandoService.excluirComando(entidade);		
		reset();
		
		gravarLog("Comando "+entidade.getCodigo(),Constantes.EXCLUSAO);

		
	}

	public ComandoServiceLocal getComandoService() {
		return comandoService;
	}

	public void setComandoService(ComandoServiceLocal ComandoService) {
		this.comandoService = ComandoService;
	}

	public ComandoDataModel getComandoDataModel() {
		return comandoDataModel;
	}

	public void setComandoDataModel(ComandoDataModel ComandoDataModel) {
		this.comandoDataModel = ComandoDataModel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
