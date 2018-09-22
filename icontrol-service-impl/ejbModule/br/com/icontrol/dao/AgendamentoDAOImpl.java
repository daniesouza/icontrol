package br.com.icontrol.dao;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.icontrol.entity.Agendamento;
import br.com.icontrol.exceptions.DAOException;
import br.com.icontrol.util.DadosUtil;
import br.com.icontrol.util.Paginacao;
import br.com.icontrol.util.QueryUtil;

/**
 *  @author Icontrol
 * Session Bean implementation class AgendamentoDAOImpl
 */
@Stateless
@Local(value=AgendamentoDAOLocal.class)
public class AgendamentoDAOImpl extends GenericDAOImpl<Agendamento,Long> implements AgendamentoDAOLocal {

    /**
     * Default constructor. 
     */
    public AgendamentoDAOImpl() {
        // TODO Auto-generated constructor stub
    }

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
	public void salvarAgendamento(Agendamento agendamento) throws DAOException{
			
		try{
			
			save(agendamento);
		
		}catch(Exception e){
			throw new DAOException(e);
		}
	
	}
	
	@Override
	public void alterarAgendamento(Agendamento agendamento) throws DAOException {
	
		try{
			
			update(agendamento);
			
		}catch(Exception e){
			throw new DAOException(e);
		}
		
	}


	
	@Override
	public Collection<Agendamento> listarAgendamentos() throws DAOException{
		
		try{			
	        return  getAll("Agendamento");
	        
		}catch(Exception e){
			throw new DAOException(e);
		}

	}
	
	@Override
	public Collection<Agendamento> listarAgendamentos(Agendamento filtro,Paginacao paginacao) throws DAOException{
		
		try{
			
			QueryUtil queryCount = queryConsultarAgendamento(filtro, true);
			
			Number totalRegistros;
			
			try {
				totalRegistros = (Number) queryCount.obterQuery(getEntityManager()).getSingleResult();			
			} catch (NoResultException e) {
				totalRegistros = 0;
			}
			paginacao.setTotalRegistros(totalRegistros.intValue());
			
			QueryUtil queryRegistros = queryConsultarAgendamento(filtro, false);
			
			queryRegistros.setPaginacao(paginacao);
			
			Query query = queryRegistros.obterQuery(getEntityManager());
			
			@SuppressWarnings("unchecked")
			List<Agendamento> Agendamentos = query.getResultList();
			
			return Agendamentos;

		}catch(Exception e){
			throw new DAOException(e);
		}

	}
	
	
	private QueryUtil queryConsultarAgendamento(Agendamento filtro, boolean count) {
	
		String sql = "SELECT u FROM Agendamento u";
		QueryUtil queryUtil = new QueryUtil(sql, false, count);	
		
		
		if(!DadosUtil.isEmpty(filtro)) {
						
			if(!DadosUtil.isEmpty(filtro.getNome())) {
				queryUtil.adicionarFiltroAproximado("u.nome", filtro.getNome());
			}
			
			if(!DadosUtil.isEmpty(filtro.getHorario())) {
				queryUtil.adicionarFiltroExato("u.horario", filtro.getHorario());
			}

			if(filtro.isSegunda()) {
				queryUtil.adicionarFiltroExato("u.segunda", filtro.isSegunda());
			}
			
			if(filtro.isTerca()) {
				queryUtil.adicionarFiltroExato("u.terca", filtro.isTerca());
			}
			
			if(filtro.isQuarta()) {
				queryUtil.adicionarFiltroExato("u.quarta", filtro.isQuarta());
			}
			
			if(filtro.isQuinta()) {
				queryUtil.adicionarFiltroExato("u.quinta", filtro.isQuinta());
			}
			
			if(filtro.isSexta()) {
				queryUtil.adicionarFiltroExato("u.sexta", filtro.isSexta());
			}
			
			if(filtro.isSabado()) {
				queryUtil.adicionarFiltroExato("u.sabado", filtro.isSabado());
			}
			
			if(filtro.isDomingo()) {
				queryUtil.adicionarFiltroExato("u.domingo", filtro.isDomingo());
			}
			
			if(!DadosUtil.isEmpty(filtro.getUsuario())) {
				
				if(!DadosUtil.isEmpty(filtro.getUsuario().getId())) {
					queryUtil.adicionarFiltroExato("u.usuario.id", filtro.getUsuario().getId());
				}				
			}
			
			if(!DadosUtil.isEmpty(filtro.getCliente())) {
				
				if(!DadosUtil.isEmpty(filtro.getCliente().getRazaoSocial())) {
					queryUtil.adicionarFiltroAproximado("u.cliente.razaoSocial", filtro.getCliente().getRazaoSocial());
				}
				
				if(!DadosUtil.isEmpty(filtro.getCliente().getNome())) {
					queryUtil.adicionarFiltroAproximado("u.cliente.nome", filtro.getCliente().getNome());
				}
				
				if(!DadosUtil.isEmpty(filtro.getCliente().getCpfCnpj())) {
					queryUtil.adicionarFiltroExato("u.cliente.cpfCnpj", filtro.getCliente().getCpfCnpj());
				}
				
				if(!DadosUtil.isEmpty(filtro.getCliente().getEndereco())) {
					queryUtil.adicionarFiltroAproximado("u.cliente.endereco", filtro.getCliente().getEndereco());
				}
				
				if(!DadosUtil.isEmpty(filtro.getCliente().getTipo())) {
					queryUtil.adicionarFiltroExato("u.cliente.tipo", filtro.getCliente().getTipo());
				}
				
				if(!DadosUtil.isEmpty(filtro.getCliente().getTelefone())) {
					queryUtil.adicionarFiltroAproximado("u.cliente.telefone", filtro.getCliente().getTelefone());
				}
				
				
				if(!DadosUtil.isEmpty(filtro.getCliente().getCep())) {
					queryUtil.adicionarFiltroAproximado("u.cliente.cep", filtro.getCliente().getCep());
				}
				
				if(!DadosUtil.isEmpty(filtro.getCliente().getBairro())) {
					queryUtil.adicionarFiltroAproximado("u.cliente.bairro", filtro.getCliente().getBairro());
				}
				
				if(!DadosUtil.isEmpty(filtro.getCliente().getEstado())) {
					queryUtil.adicionarFiltroAproximado("u.cliente.estado", filtro.getCliente().getEstado());
				}
				
				if(!DadosUtil.isEmpty(filtro.getCliente().getCidade())) {
					queryUtil.adicionarFiltroAproximado("u.cliente.cidade", filtro.getCliente().getCidade());
				}
				
				if(!DadosUtil.isEmpty(filtro.getCliente().getDtCad())) {
					
					Calendar dataFim = Calendar.getInstance();
					dataFim.setTime(filtro.getCliente().getDtCad());
					dataFim.set(Calendar.HOUR_OF_DAY, 23);
					dataFim.set(Calendar.MINUTE, 59);
					dataFim.set(Calendar.SECOND, 59);
					dataFim.set(Calendar.MILLISECOND, 999);
					
					queryUtil.adicionarFiltroEntre("u.cliente.dtCad", filtro.getCliente().getDtCad(),dataFim.getTime());
				}
				
				if(!DadosUtil.isEmpty(filtro.getCliente().getEmail())) {
					queryUtil.adicionarFiltroAproximado("u.cliente.email", filtro.getCliente().getEmail());
				}
				
				if(!DadosUtil.isEmpty(filtro.getCliente().getAtivo())) {
					queryUtil.adicionarFiltroExato("u.cliente.ativo", filtro.getCliente().getAtivo());
				}
			}
			

		}
		return queryUtil;
	}

	@Override
	public void excluirAgendamento(Agendamento agendamento) throws DAOException {
		try{
			delete(agendamento);
		}catch(Exception e){
			throw new DAOException(e);
		}
	}

	private QueryUtil queryConsultarExisteAgendamento(Agendamento filtro, boolean count) {
		
		String sql = "SELECT u FROM Agendamento u";
		QueryUtil queryUtil = new QueryUtil(sql, false, count);	
		
		
		if(!DadosUtil.isEmpty(filtro)) {
			
			if(!DadosUtil.isEmpty(filtro.getNome())) {
				queryUtil.adicionarFiltroExato("u.nome", filtro.getNome());
			}
		}
		return queryUtil;
	}
	
	@Override
	public Agendamento selectById(Long id) throws DAOException {
		
		return find(Agendamento.class, id);
	}
	
	@Override
	public int consultarQtde(Agendamento filtro) throws DAOException {
		QueryUtil queryCount = queryConsultarExisteAgendamento(filtro, true);
		Number totalRegistros = (Number) queryCount.obterQuery(getEntityManager()).getSingleResult();
		return totalRegistros.intValue();
	}

    
}
