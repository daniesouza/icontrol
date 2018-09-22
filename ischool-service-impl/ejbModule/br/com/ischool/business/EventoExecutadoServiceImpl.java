package br.com.ischool.business;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import br.com.ischool.dao.EventoExecutadoDAOLocal;
import br.com.ischool.entity.EventoExecutado;
import br.com.ischool.exceptions.DAOException;
import br.com.ischool.exceptions.ServicoException;
import br.com.ischool.util.DadosUtil;
import br.com.ischool.util.Paginacao;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * Session Bean implementation class EventoExecutadoServiceImpl
 */
@Stateless
@Local(value=EventoExecutadoServiceLocal.class)
public class EventoExecutadoServiceImpl implements EventoExecutadoServiceLocal {

    /**
     * Default constructor. 
     */
    public EventoExecutadoServiceImpl() {
        // TODO Auto-generated constructor stub
    }
    
	@EJB
	private EventoExecutadoDAOLocal eventoExecutadoDAO;
	
	@EJB
	private GenericServiceLocal generico;
	
    @PostConstruct
    public void carregarInformacoes()
    {
    	System.out.println("CARREGADO OS RECURSOS DO EJB "+this.getClass().getName());
    }
    
    @PreDestroy
    public void clear()
    {
    	System.out.println("LIBERANDO OS RECURSOS DO EJB "+this.getClass().getName());
    }
	
	@Override
	public void salvarEventoExecutado(EventoExecutado eventoExecutado) throws ServicoException {
		
		try {
			validarEventoExecutado(eventoExecutado);
			
			generico.salvar(eventoExecutado);

		} catch (ServicoException e) {
			throw e;
		}					
	}

	
	private void validarEventoExecutado(EventoExecutado EventoExecutado) throws ServicoException {
		ServicoException se = new ServicoException();

		if (DadosUtil.isEmpty(EventoExecutado.getQuantidade())) {
			se.adicionarMensagem("QUANTIDADE_NAO_VAZIO");
		}
		
		if (DadosUtil.isEmpty(EventoExecutado.getDataExecucao())) {
			se.adicionarMensagem("DATA_EXECUCAO_NAO_VAZIO");
		}
		
		if (DadosUtil.isEmpty(EventoExecutado.getClasse())) {
			se.adicionarMensagem("CLASSE_NAO_VAZIO");
		}
		
		if (DadosUtil.isEmpty(EventoExecutado.getCliente())) {
			se.adicionarMensagem("CLIENTE_NAO_VAZIO");
		}
		
		if (DadosUtil.isEmpty(EventoExecutado.getEvento())) {
			se.adicionarMensagem("EVENTO_NAO_VAZIO");
		}
		
		if (DadosUtil.isEmpty(EventoExecutado.getAluno())) {
			se.adicionarMensagem("ALUNO_NAO_VAZIO");
		}
		
		if (DadosUtil.isEmpty(EventoExecutado.getUsuario())) {
			se.adicionarMensagem("USUARIO_NAO_VAZIO");
		}		

		if (se.existeErro()) {
			throw se;
		}
	}
	
//	private boolean existeEventoExecutado(EventoExecutado EventoExecutado) throws ServicoException {
//		try {
//			if (!DadosUtil.isEmpty(EventoExecutado.getIdEventoExecutado())) {
//				EventoExecutado EventoExecutadoAux = EventoExecutadoDAO.selectById(EventoExecutado.getIdEventoExecutado());
//				if (EventoExecutadoAux.getIdEventoExecutado().equals(EventoExecutado.getIdEventoExecutado())) {
//					return false;
//				}
//			}
//
//			EventoExecutado filtro = new EventoExecutado();
//			filtro.setIdEventoExecutado(EventoExecutado.getIdEventoExecutado());
//
//			int qtdeEventoExecutados = EventoExecutadoDAO.consultarQtde(filtro);
//			return qtdeEventoExecutados > 0;
//
//		} catch (DAOException e) {
//			throw new ServicoException(e.getMessage());
//		}
//	}

	@Override
	public void alterarEventoExecutado(EventoExecutado eventoExecutado) throws ServicoException {
		
		try {
			validarEventoExecutado(eventoExecutado);

//			boolean existeEventoExecutado = existeEventoExecutado(EventoExecutado);
//			if (existeEventoExecutado) {
//				throw new ServicoException("EventoExecutado_EXISTENTE");
//			}

			
			generico.atualizar(eventoExecutado);
			
			
		} catch (ServicoException e) {
			throw e;
		}
					
	}

	@Override
	public Collection<EventoExecutado> listarEventoExecutados() throws ServicoException {
		
		try {
			
			return eventoExecutadoDAO.listarEventoExecutados();
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}
	}

	@Override
	public Collection<EventoExecutado> listarEventoExecutados(EventoExecutado filtro,Paginacao paginacao) throws ServicoException {

		try {
			
			if(DadosUtil.isEmpty(paginacao)){
				paginacao = new Paginacao(-1);
			}
			
			return eventoExecutadoDAO.listarEventoExecutados(filtro,paginacao);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}
	}


	@Override
	public void excluirEventoExecutado(EventoExecutado eventoExecutado) throws ServicoException {
		
		try {
			
			eventoExecutadoDAO.excluirEventoExecutado(eventoExecutado);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);		
		}
		
	}

	@Override
	public EventoExecutado selectById(Long id) throws ServicoException {
		
		try {
			
			return eventoExecutadoDAO.selectById(id);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}
	}



}
