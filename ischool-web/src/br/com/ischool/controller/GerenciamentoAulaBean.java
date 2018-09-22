package br.com.ischool.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import br.com.ischool.business.EventoClasseServiceLocal;
import br.com.ischool.business.EventoExecutadoServiceLocal;
import br.com.ischool.business.GenericServiceLocal;
import br.com.ischool.entity.Aluno;
import br.com.ischool.entity.Classe;
import br.com.ischool.entity.Cliente;
import br.com.ischool.entity.EventoClasse;
import br.com.ischool.entity.EventoExecutado;
import br.com.ischool.entity.Usuario;
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
public class GerenciamentoAulaBean extends AbstractViewHelper<EventoExecutado> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6390001812920897476L;
	
	
	@EJB
	private EventoClasseServiceLocal eventoClasseService;
	
	@EJB
	private EventoExecutadoServiceLocal eventoExecutadoService;
	
	@EJB
	private GenericServiceLocal servicoGenerico;
	

	private List<EventoClasse> eventoClasseManha;
	private List<EventoClasse> eventoClasseTarde;
	private List<EventoClasse> eventoClasseOutros;
	private List<Classe>	   listaClasses;
	private List<Aluno>		   listaAlunos;
	
	
	
	@Override
	public void inicializar() throws WebException {
			
			setEntidade(new EventoExecutado());
			entidade.setDataExecucao(new Date());
			listaClasses 	   = new ArrayList<Classe>();
			eventoClasseManha  = new ArrayList<EventoClasse>();
			eventoClasseTarde  = new ArrayList<EventoClasse>();
			eventoClasseOutros = new ArrayList<EventoClasse>();
	
			
			carregarListaClasses();
	}
	
	
	@Override
	public void reset() {
		eventoClasseManha.clear();
		eventoClasseTarde.clear();
		eventoClasseOutros.clear();
	}

	public void carregarListaClasses() throws WebException{
		try{
			

			Usuario professor = servicoGenerico.obterListaLazy(FacesUtil.recuperarUsuarioSessao(), Usuario.class, "classes");
			
			for(Classe classe:professor.getClasses()){
				
				if(classe.getAno() == Calendar.getInstance().get(Calendar.YEAR)){
					
					listaClasses.add(classe);
				}
				
			}
						
		} catch (ServicoException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("erro_carregar_listas"), FacesUtil.tratarMensagemErro(e)));
		}catch(Exception e){			
			throw new WebException(e.getMessage());
		}
	}
	
	public void carregarEventosAlunos(){
		
		reset();
		
		carregarAlunosClasses();
		carregarEventosClasses();
		carregarEventosExecutadosAluno();

		
	}
	
	
	public void carregarAlunosClasses(){
		

		if(!DadosUtil.isEmpty(entidade.getClasse()) && !DadosUtil.isEmpty(entidade.getClasse().getIdClasse())){
			
			try {
				
				Classe  classe = servicoGenerico.obterListaLazy(entidade.getClasse(), Classe.class, "alunos");
		
				listaAlunos = classe.getAlunos();
			
			} catch (ServicoException e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("erro_carregar_listas"), FacesUtil.tratarMensagemErro(e)));
			}catch(Exception e){			
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("erro_carregar_listas"), e.getMessage()));
			}
		}
	}
	
	
	public void carregarEventosExecutadosAluno(){
		
		if(!DadosUtil.isEmpty(listaAlunos) && !DadosUtil.isEmpty(entidade.getClasse()) && !DadosUtil.isEmpty(entidade.getDataExecucao())){

			try {	
				
				EventoExecutado filtro = new EventoExecutado();
				
				Classe classeFiltro = new Classe();					
				classeFiltro.setIdClasse(entidade.getClasse().getIdClasse());
				filtro.setClasse(classeFiltro);
				
				Cliente clienteFiltro = new Cliente();
				clienteFiltro.setIdCliente(FacesUtil.recuperarUsuarioSessao().getCliente().getIdCliente());
				filtro.setCliente(clienteFiltro);
				
				for(Aluno aluno:listaAlunos){
												
						filtro.setDataExecucao(entidade.getDataExecucao());
						
						Aluno alunoFiltro = new Aluno();
						alunoFiltro.setIdAluno(aluno.getIdAluno());
						filtro.setAluno(alunoFiltro);

						
						List<EventoExecutado> listaEventosExecutados = (List<EventoExecutado>) eventoExecutadoService.listarEventoExecutados(filtro, null);
						
						aluno.setListaEventoExecutadoManha(new ArrayList<EventoExecutado>());
						aluno.setListaEventoExecutadoTarde(new ArrayList<EventoExecutado>());
						aluno.setListaEventoExecutadoOutros(new ArrayList<EventoExecutado>());

						for(EventoExecutado eventoExecutado:listaEventosExecutados){
							
							switch(eventoExecutado.getTipo()){
								
								case Constantes.TIPO_MANHA:{
									aluno.getListaEventoExecutadoManha().add(eventoExecutado);
									
									break;
								}
								
								case Constantes.TIPO_TARDE:{
									aluno.getListaEventoExecutadoTarde().add(eventoExecutado);
									break;
								}
								
								case Constantes.TIPO_OUTROS:{
									aluno.getListaEventoExecutadoOutros().add(eventoExecutado);
									break;
								}				
										
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
	
	
	public void carregarEventosClasses(){
		

		if(!DadosUtil.isEmpty(entidade.getClasse()) && !DadosUtil.isEmpty(entidade.getClasse().getIdClasse())){
				
			try {
				
				EventoClasse filtro = new EventoClasse();
				
				filtro.setClasse(new Classe());
				filtro.getClasse().setIdClasse(entidade.getClasse().getIdClasse());
				
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
	

	@Override
	public void salvarImpl() throws ServicoException {
		
		EventoExecutado eventoExe = new EventoExecutado();
		
		eventoExe.setUsuario(FacesUtil.recuperarUsuarioSessao());
		eventoExe.setDataExecucao(new Date());
		eventoExe.setCliente(eventoExe.getUsuario().getCliente());
		eventoExe.setAluno(entidade.getAluno());
		eventoExe.setClasse(entidade.getClasse());
		eventoExe.setObservacoes(entidade.getObservacoes());
		eventoExe.setQuantidade(entidade.getQuantidade());
		eventoExe.setEvento(entidade.getEvento());
		eventoExe.setTipo(entidade.getTipo());
		
		eventoExecutadoService.salvarEventoExecutado(eventoExe);
		
		entidade.setQuantidade(null);
		entidade.setObservacoes(null);
		
		carregarEventosExecutadosAluno();
		
	}

	@Override
	public void alterarImpl() throws ServicoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excluirImpl() throws ServicoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pesquisar() {
		// TODO Auto-generated method stub
		
	}


	public List<Classe> getListaClasses() {
		return listaClasses;
	}


	public void setListaClasses(List<Classe> listaClasses) {
		this.listaClasses = listaClasses;
	}




	public List<Aluno> getListaAlunos() {
		return listaAlunos;
	}


	public void setListaAlunos(List<Aluno> listaAlunos) {
		this.listaAlunos = listaAlunos;
	}


	public List<EventoClasse> getEventoClasseManha() {
		return eventoClasseManha;
	}


	public void setEventoClasseManha(List<EventoClasse> eventoClasseManha) {
		this.eventoClasseManha = eventoClasseManha;
	}


	public List<EventoClasse> getEventoClasseTarde() {
		return eventoClasseTarde;
	}


	public void setEventoClasseTarde(List<EventoClasse> eventoClasseTarde) {
		this.eventoClasseTarde = eventoClasseTarde;
	}


	public List<EventoClasse> getEventoClasseOutros() {
		return eventoClasseOutros;
	}


	public void setEventoClasseOutros(List<EventoClasse> eventoClasseOutros) {
		this.eventoClasseOutros = eventoClasseOutros;
	}

	
	
	

}
