package br.com.icontrol.business;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import br.com.icontrol.dao.AgendamentoDAOLocal;
import br.com.icontrol.entity.Agendamento;
import br.com.icontrol.exceptions.DAOException;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.DadosUtil;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
 * Session Bean implementation class AgendamentoServiceImpl
 */
@Stateless
@Local(value=AgendamentoServiceLocal.class)
public class AgendamentoServiceImpl implements AgendamentoServiceLocal {

    /**
     * Default constructor. 
     */
    public AgendamentoServiceImpl() {
        // TODO Auto-generated constructor stub
    }
    
	@EJB
	private AgendamentoDAOLocal agendamentoDAO;
	
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
	public void salvarAgendamento(Agendamento agendamento) throws ServicoException {
		
		try {
			validarAgendamento(agendamento);
			
			boolean existeAgendamento = existeAgendamento(agendamento);
			if (existeAgendamento) {
				throw new ServicoException("AGENDAMENTO_EXISTENTE");
			}
			
			agendamentoDAO.salvarAgendamento(agendamento);
			
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServicoException(e.getMessage());
		}
			
	}

	@Override
	public void alterarAgendamento(Agendamento agendamento) throws ServicoException {
		

		try {
			validarAgendamento(agendamento);
			
			boolean existeGrupo = existeAgendamento(agendamento);
			if (existeGrupo) {
				throw new ServicoException("AGENDAMENTO_EXISTENTE");
			}
			
			agendamentoDAO.alterarAgendamento(agendamento);
			
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServicoException(e.getMessage());
		}
			
			
	}

	@Override
	public Collection<Agendamento> listarAgendamentos() throws ServicoException {
		
		try {
			
			return agendamentoDAO.listarAgendamentos();
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);			
		}
	}

	@Override
	public Collection<Agendamento> listarAgendamentos(Agendamento filtro,Paginacao paginacao) throws ServicoException {

		try {
			
			return agendamentoDAO.listarAgendamentos(filtro,paginacao);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);			
		}
	}


	@Override
	public void excluirAgendamento(Agendamento Agendamento) throws ServicoException {
		
		try {
			
			agendamentoDAO.excluirAgendamento(Agendamento);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);		
		} 
		
	}
	
	
	private void validarAgendamento(Agendamento agendamento) throws ServicoException {
		ServicoException se = new ServicoException();

		if (DadosUtil.isEmpty(agendamento.getNome())) {
			se.adicionarMensagem("NOME_NAO_VAZIO");
		}
				
		if (se.existeErro()) {
			throw se;
		}
	}
	
	private boolean existeAgendamento(Agendamento agendamento) throws ServicoException {
		try {
			if (!DadosUtil.isEmpty(agendamento.getIdAgendamento())) {
				Agendamento AgendamentoAux = agendamentoDAO.selectById(agendamento.getIdAgendamento());
				if (AgendamentoAux.getNome().equalsIgnoreCase(agendamento.getNome())) {
					return false;
				}
			}

			Agendamento filtro = new Agendamento();
			filtro.setNome(agendamento.getNome());

			int qtdeClientes = agendamentoDAO.consultarQtde(filtro);
			return qtdeClientes > 0;

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServicoException(e.getMessage());
		}
	}

}
