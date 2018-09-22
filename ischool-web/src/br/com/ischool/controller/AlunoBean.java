package br.com.ischool.controller;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DualListModel;

import br.com.ischool.business.AlunoServiceLocal;
import br.com.ischool.business.ClasseServiceLocal;
import br.com.ischool.business.GenericServiceLocal;
import br.com.ischool.business.UsuarioServiceLocal;
import br.com.ischool.controller.datamodel.AlunoDataModel;
import br.com.ischool.entity.Aluno;
import br.com.ischool.entity.Classe;
import br.com.ischool.entity.Cliente;
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
public class AlunoBean extends AbstractViewHelper<Aluno> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3725699119385532894L;

	@EJB
	private AlunoServiceLocal alunoService;
	
	
	@EJB
	private UsuarioServiceLocal usuarioService;
	
	@EJB
	private ClasseServiceLocal classeService;
	
	@EJB
	private GenericServiceLocal servicoGenerico;
	
	private AlunoDataModel alunoDataModel = new AlunoDataModel();
	
	private DualListModel<Usuario>	   responsaveis;
	private List<Usuario> 		       listaUsuarios;
	
	private DualListModel<Classe>	   classes;
	private List<Classe> 		       listaClasses;

			
	
	@Override
	public void inicializar() throws WebException {
				
			setEntidade(new Aluno());	
			setFiltro(new Aluno());
			getFiltro().setCliente(new Cliente());	
			alunoDataModel.setAlunoLocal(alunoService);
			responsaveis = new DualListModel<Usuario>();
			classes = new DualListModel<Classe>();

	}
	
	public void carregarListaResponsaveis() throws ServicoException,Exception{
				
			Usuario usuario = new Usuario();
			
			usuario.setCliente(new Cliente());
			
			usuario.getCliente().setIdCliente(FacesUtil.recuperarUsuarioSessao().getCliente().getIdCliente());
			
			listaUsuarios	= (List<Usuario>) usuarioService.listarUsuarios(usuario, null);
	
	}
	
	public void carregarListaClasses() throws ServicoException,Exception{
			
			Usuario usuario = FacesUtil.recuperarUsuarioSessao();
			
			Classe filtro = new Classe();			
			filtro.setCliente(new Cliente());			
			filtro.getCliente().setIdCliente(usuario.getCliente().getIdCliente());
			filtro.setAno(Calendar.getInstance().get(Calendar.YEAR));
			
			
			listaClasses =  (List<Classe>) classeService.listarClasses(filtro, null);	

	}

	@Override
	public void pesquisar() {
		
		alunoDataModel.setFiltro(filtro);
		
	}

	@Override
	public void reset() {
		setEntidade(new Aluno());	
	}
	
	public void novo(){
		
		try{
			
			reset();
			carregarListaResponsaveis();
			carregarListaClasses();
	
			responsaveis.setSource(listaUsuarios);
			responsaveis.setTarget(new ArrayList<Usuario>());
			
			classes.setSource(listaClasses);
			classes.setTarget(new ArrayList<Classe>());
			
			Usuario usuario = FacesUtil.recuperarUsuarioSessao();		
			getEntidade().setCliente(usuario.getCliente());
		
		} catch (ServicoException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("erro_carregar_listas"), FacesUtil.tratarMensagemErro(e)));
		}
		catch(Exception e){			
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("erro_carregar_listas"), e.getMessage()));
		}
		
	}
	
	public void editar(){
		
		try {

			carregarListaResponsaveis();
			carregarListaClasses();

			
			responsaveis.setSource(listaUsuarios);
			classes.setSource(listaClasses);

			entidade = servicoGenerico.obterListaLazy(entidade, Aluno.class, "usuarios");
			responsaveis.setTarget(new ArrayList<Usuario>(entidade.getUsuarios()));
			
			responsaveis.getSource().removeAll(entidade.getUsuarios());
			
			entidade = servicoGenerico.obterListaLazy(entidade, Aluno.class, "classes");
			classes.setTarget(new ArrayList<Classe>(entidade.getClasses()));
					
			classes.getSource().removeAll(entidade.getClasses());
		
		} catch (ServicoException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("erro_carregar_listas"), FacesUtil.tratarMensagemErro(e)));
		}catch(Exception e){			
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("erro_carregar_listas"), e.getMessage()));
		}
		
	}

	@Override
	public void salvarImpl() throws ServicoException {
		
		entidade.setUsuarios(responsaveis.getTarget());
		entidade.setClasses(classes.getTarget());
		
		
		alunoService.salvarAluno(entidade);	
		
	}

	@Override
	public void alterarImpl() throws ServicoException {
		
		entidade.setUsuarios(responsaveis.getTarget());
		entidade.setClasses(classes.getTarget());
		
		alunoService.alterarAluno(entidade,responsaveis.getSource(),classes.getSource());	
		
	}

	@Override
	public void excluirImpl() throws ServicoException {
		
		alunoService.excluirAluno(entidade);
		
	}

	public AlunoServiceLocal getAlunoService() {
		return alunoService;
	}

	public void setAlunoService(AlunoServiceLocal AlunoService) {
		this.alunoService = AlunoService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public AlunoDataModel getAlunoDataModel() {
		return alunoDataModel;
	}

	public void setAlunoDataModel(AlunoDataModel AlunoDataModel) {
		this.alunoDataModel = AlunoDataModel;
	}

	public DualListModel<Usuario> getResponsaveis() {
		return responsaveis;
	}

	public void setResponsaveis(DualListModel<Usuario> responsaveis) {
		this.responsaveis = responsaveis;
	}

	public DualListModel<Classe> getClasses() {
		return classes;
	}

	public void setClasses(DualListModel<Classe> classes) {
		this.classes = classes;
	}

}
