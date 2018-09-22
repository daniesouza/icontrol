package br.com.ischool.business;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import br.com.ischool.dao.EventoClasseDAOLocal;
import br.com.ischool.entity.EventoClasse;
import br.com.ischool.exceptions.DAOException;
import br.com.ischool.exceptions.ServicoException;
import br.com.ischool.util.DadosUtil;
import br.com.ischool.util.Paginacao;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * Session Bean implementation class EventoClasseServiceImpl
 */
@Stateless
@Local(value=EventoClasseServiceLocal.class)
public class EventoClasseServiceImpl implements EventoClasseServiceLocal {

    /**
     * Default constructor. 
     */
    public EventoClasseServiceImpl() {
        // TODO Auto-generated constructor stub
    }
    
	@EJB
	private EventoClasseDAOLocal eventoClasseDAO;
	
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
	public void salvarEventoClasse(EventoClasse eventoClasse) throws ServicoException {
		
		try {
			validarEventoClasse(eventoClasse);
			
			generico.salvar(eventoClasse);

		} catch (ServicoException e) {
			throw e;
		}					
	}
	

	@Override
	public void atualizarEventoClasse(Collection<EventoClasse> eventosClasse,Long idClasse)	throws ServicoException {
		try {
			
			for(EventoClasse evento:eventosClasse){
				validarEventoClasse(evento);				
			}
			
			if(!DadosUtil.isEmpty(idClasse)){
				limparEventosClasse(idClasse);
			}
			
			generico.atualizar(eventosClasse);
						
		
		} catch (ServicoException e) {
			throw e;
		} catch (Exception e) {
			throw new ServicoException(e.getMessage());
		}
		
	}	
	
	
	public void limparEventosClasse(Long idClasse) throws ServicoException {
		try{
			
			eventoClasseDAO.limparEventosClasse(idClasse);
		}catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}

	}

	
	private void validarEventoClasse(EventoClasse eventoClasse) throws ServicoException {
		ServicoException se = new ServicoException();

		if (DadosUtil.isEmpty(eventoClasse.getClasse())) {
			se.adicionarMensagem("CLASSE_NAO_VAZIO");
		}
		
		if (DadosUtil.isEmpty(eventoClasse.getEvento())) {
			se.adicionarMensagem("EVENTO_NAO_VAZIO");
		}

		if (se.existeErro()) {
			throw se;
		}
	}
	
//	private boolean existeEventoClasse(EventoClasse EventoClasse) throws ServicoException {
//		try {
//			if (!DadosUtil.isEmpty(EventoClasse.getIdEventoClasse())) {
//				EventoClasse EventoClasseAux = EventoClasseDAO.selectById(EventoClasse.getIdEventoClasse());
//				if (EventoClasseAux.getIdEventoClasse().equals(EventoClasse.getIdEventoClasse())) {
//					return false;
//				}
//			}
//
//			EventoClasse filtro = new EventoClasse();
//			filtro.setIdEventoClasse(EventoClasse.getIdEventoClasse());
//
//			int qtdeEventoClasses = EventoClasseDAO.consultarQtde(filtro);
//			return qtdeEventoClasses > 0;
//
//		} catch (DAOException e) {
//			throw new ServicoException(e.getMessage());
//		}
//	}

	@Override
	public void alterarEventoClasse(EventoClasse eventoClasse) throws ServicoException {
		
		try {
			validarEventoClasse(eventoClasse);

//			boolean existeEventoClasse = existeEventoClasse(EventoClasse);
//			if (existeEventoClasse) {
//				throw new ServicoException("EVENTOCLASSE_EXISTENTE");
//			}

			
			generico.atualizar(eventoClasse);
			
			
		} catch (ServicoException e) {
			throw e;
		}
					
	}

	@Override
	public Collection<EventoClasse> listarEventoClasses() throws ServicoException {
		
		try {
			
			return eventoClasseDAO.listarEventoClasses();
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}
	}

	@Override
	public Collection<EventoClasse> listarEventoClasses(EventoClasse filtro,Paginacao paginacao) throws ServicoException {

		try {
			
			if(DadosUtil.isEmpty(paginacao)){
				paginacao = new Paginacao(-1);
			}
			
			return eventoClasseDAO.listarEventoClasses(filtro,paginacao);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}
	}


	@Override
	public void excluirEventoClasse(EventoClasse eventoClasse) throws ServicoException {
		
		try {
			
			eventoClasseDAO.excluirEventoClasse(eventoClasse);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);		
		}
		
	}

	@Override
	public EventoClasse selectById(Long id) throws ServicoException {
		
		try {
			
			return eventoClasseDAO.selectById(id);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}
	}





}
