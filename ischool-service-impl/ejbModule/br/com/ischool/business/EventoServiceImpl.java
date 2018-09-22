package br.com.ischool.business;

import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import br.com.ischool.dao.EventoDAOLocal;
import br.com.ischool.entity.Evento;
import br.com.ischool.exceptions.DAOException;
import br.com.ischool.exceptions.ServicoException;
import br.com.ischool.util.DadosUtil;
import br.com.ischool.util.Paginacao;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * Session Bean implementation class EventoServiceImpl
 */
@Stateless
@Local(value=EventoServiceLocal.class)
public class EventoServiceImpl implements EventoServiceLocal {

    /**
     * Default constructor. 
     */
    public EventoServiceImpl() {
        // TODO Auto-generated constructor stub
    }
    
	@EJB
	private EventoDAOLocal eventoDAO;
	
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
	public void salvarEvento(Evento evento) throws ServicoException {
		
		try {
			validarEvento(evento);

			boolean existeEvento = existeEvento(evento);
			if (existeEvento) {
				throw new ServicoException("EVENTO_EXISTENTE");
			}

			evento.setDataCadastro(new Date());
			
			generico.salvar(evento);
					
				
			
		} catch (ServicoException e) {
			throw e;
		}
					
	}

	
	private void validarEvento(Evento evento) throws ServicoException {
		ServicoException se = new ServicoException();

		if (DadosUtil.isEmpty(evento.getNome())) {
			se.adicionarMensagem("NOME_NAO_VAZIO");
		}
		
		if (DadosUtil.isEmpty(evento.getCodigoEvento())) {
			se.adicionarMensagem("CODIGO_NAO_VAZIO");
		}

		if (se.existeErro()) {
			throw se;
		}
	}
	
	private boolean existeEvento(Evento evento) throws ServicoException {
		try {
			if (!DadosUtil.isEmpty(evento.getIdEvento())) {
				Evento EventoAux = eventoDAO.selectById(evento.getIdEvento());
				if (EventoAux.getCodigoEvento().equals(evento.getCodigoEvento())) {
					return false;
				}
			}

			Evento filtro = new Evento();
			filtro.setCodigoEvento(evento.getCodigoEvento());

			int qtdeEventos = eventoDAO.consultarQtde(filtro);
			return qtdeEventos > 0;

		} catch (DAOException e) {
			throw new ServicoException(e.getMessage());
		}
	}

	@Override
	public void alterarEvento(Evento evento) throws ServicoException {
		
		try {
			validarEvento(evento);

			boolean existeEvento = existeEvento(evento);
			if (existeEvento) {
				throw new ServicoException("EVENTO_EXISTENTE");
			}

			
			generico.atualizar(evento);
			
			
		} catch (ServicoException e) {
			throw e;
		}
					
	}

	@Override
	public Collection<Evento> listarEventos() throws ServicoException {
		
		try {
			
			return eventoDAO.listarEventos();
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}
	}

	@Override
	public Collection<Evento> listarEventos(Evento filtro,Paginacao paginacao) throws ServicoException {

		try {
			
			if(DadosUtil.isEmpty(paginacao)){
				paginacao = new Paginacao(-1);
			}
			
			return eventoDAO.listarEventos(filtro,paginacao);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}
	}


	@Override
	public void excluirEvento(Evento Evento) throws ServicoException {
		
		try {
			
			eventoDAO.excluirEvento(Evento);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);		
		}
		
	}

	@Override
	public Evento selectById(Long id) throws ServicoException {
		
		try {
			
			return eventoDAO.selectById(id);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}
	}


}
