package br.com.ischool.dao;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.ischool.entity.EventoExecutado;
import br.com.ischool.exceptions.DAOException;
import br.com.ischool.util.DadosUtil;
import br.com.ischool.util.Paginacao;
import br.com.ischool.util.QueryUtil;

/**
 *  @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * Session Bean implementation class EventoExecutadoDAOImpl
 */
@Stateless
@Local(value=EventoExecutadoDAOLocal.class)
public class EventoExecutadoDAOImpl extends GenericDAOImpl<EventoExecutado,Long> implements EventoExecutadoDAOLocal {

    /**
     * Default constructor. 
     */
    public EventoExecutadoDAOImpl() {
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
	public void salvarEventoExecutado(EventoExecutado eventoExecutado) throws DAOException{
			
		try{
			
			save(eventoExecutado);
		
		}catch(Exception e){
			throw new DAOException(e);
		}
	
	}
	
	@Override
	public void alterarEventoExecutado(EventoExecutado eventoExecutado) throws DAOException {
	
		try{
			
			update(eventoExecutado);
			
		}catch(Exception e){
			throw new DAOException(e);
		}
		
	}


	
	@Override
	public Collection<EventoExecutado> listarEventoExecutados() throws DAOException{
		
		try{			
	        return  getAll("EventoExecutado");
	        
		}catch(Exception e){
			throw new DAOException(e);
		}

	}
	
	@Override
	public Collection<EventoExecutado> listarEventoExecutados(EventoExecutado filtro,Paginacao paginacao) throws DAOException{
		
		try{
			
			if(paginacao.getInicio() >-1){
				
				QueryUtil queryCount = queryConsultarEventoExecutado(filtro, true);
				
				Number totalRegistros;
				
				try {
					totalRegistros = (Number) queryCount.obterQuery(getEntityManager()).getSingleResult();			
				} catch (NoResultException e) {
					totalRegistros = 0;
				}
				paginacao.setTotalRegistros(totalRegistros.intValue());
			}

			QueryUtil queryRegistros = queryConsultarEventoExecutado(filtro, false);
			
			queryRegistros.setPaginacao(paginacao);
			
			Query query = queryRegistros.obterQuery(getEntityManager());
			
			@SuppressWarnings("unchecked")
			List<EventoExecutado> EventoExecutados = query.getResultList();
			
			return EventoExecutados;

		}catch(Exception e){
			throw new DAOException(e);
		}

	}
	
	
	private QueryUtil queryConsultarEventoExecutado(EventoExecutado filtro, boolean count) {
	
		String sql = "SELECT u FROM EventoExecutado u";
		QueryUtil queryUtil = new QueryUtil(sql, false, count);	
		
		
		if(!DadosUtil.isEmpty(filtro)) {
			
			if(!DadosUtil.isEmpty(filtro.getIdEventoExecutado())) {
				queryUtil.adicionarFiltroExato("u.idEventoExecutado", filtro.getIdEventoExecutado());
			}
			
			if(!DadosUtil.isEmpty(filtro.getObservacoes())) {
				queryUtil.adicionarFiltroAproximado("u.observacoes", filtro.getObservacoes());
			}
			
			if(!DadosUtil.isEmpty(filtro.getQuantidade())) {
				queryUtil.adicionarFiltroExato("u.quantidade", filtro.getQuantidade());
			}
			
			if(!DadosUtil.isEmpty(filtro.getTipo())) {
				queryUtil.adicionarFiltroExato("u.tipo", filtro.getTipo());
			}
			
			if(!DadosUtil.isEmpty(filtro.getDataExecucao())) {
				
				Calendar dataFim = Calendar.getInstance();
				dataFim.setTime(filtro.getDataExecucao());
				dataFim.set(Calendar.HOUR_OF_DAY, 23);
				dataFim.set(Calendar.MINUTE, 59);
				dataFim.set(Calendar.SECOND, 59);
				dataFim.set(Calendar.MILLISECOND, 999);
				
				queryUtil.adicionarFiltroEntre("u.dataExecucao", filtro.getDataExecucao(),dataFim.getTime());
			}
			
			if(!DadosUtil.isEmpty(filtro.getCliente())) {
				
				if(!DadosUtil.isEmpty(filtro.getCliente().getIdCliente())) {
					queryUtil.adicionarFiltroExato("u.cliente.id", filtro.getCliente().getIdCliente());
				}
				
				if(!DadosUtil.isEmpty(filtro.getCliente().getRazaoSocial())) {
					queryUtil.adicionarFiltroAproximado("u.cliente.razaoSocial", filtro.getCliente().getRazaoSocial());
				}
				
				if(!DadosUtil.isEmpty(filtro.getCliente().getNome())) {
					queryUtil.adicionarFiltroAproximado("u.cliente.nome", filtro.getCliente().getNome());
				}
				
				if(!DadosUtil.isEmpty(filtro.getCliente().getCnpj())) {
					queryUtil.adicionarFiltroExato("u.cliente.cnpj", filtro.getCliente().getCnpj());
				}
				
				if(!DadosUtil.isEmpty(filtro.getCliente().getEndereco())) {
					queryUtil.adicionarFiltroAproximado("u.cliente.endereco", filtro.getCliente().getEndereco());
				}
				
				
				if(!DadosUtil.isEmpty(filtro.getCliente().getTelefone())) {
					queryUtil.adicionarFiltroAproximado("u.cliente.telefone", filtro.getCliente().getTelefone());
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
			
			if(!DadosUtil.isEmpty(filtro.getAluno())) {
				
				if(!DadosUtil.isEmpty(filtro.getAluno().getIdAluno())) {
					queryUtil.adicionarFiltroExato("u.aluno.id", filtro.getAluno().getIdAluno());
				}

				if(!DadosUtil.isEmpty(filtro.getAluno().getNome())) {
					queryUtil.adicionarFiltroAproximado("u.aluno.nome", filtro.getAluno().getNome());
				}
					
				if(!DadosUtil.isEmpty(filtro.getAluno().getRg())) {
					queryUtil.adicionarFiltroExato("u.aluno.rg", filtro.getAluno().getRg());
				}
				
				if(!DadosUtil.isEmpty(filtro.getAluno().getCodigoAluno())) {
					queryUtil.adicionarFiltroExato("u.aluno.codigoAluno", filtro.getAluno().getCodigoAluno());
				}
				
				if(!DadosUtil.isEmpty(filtro.getAluno().getEndereco())) {
					queryUtil.adicionarFiltroAproximado("u.aluno.endereco", filtro.getAluno().getEndereco());
				}
				
				
				if(!DadosUtil.isEmpty(filtro.getAluno().getTelefone())) {
					queryUtil.adicionarFiltroAproximado("u.aluno.telefone", filtro.getAluno().getTelefone());
				}
				
				if(!DadosUtil.isEmpty(filtro.getAluno().getDataCadastro())) {
					
					Calendar dataFim = Calendar.getInstance();
					dataFim.setTime(filtro.getAluno().getDataCadastro());
					dataFim.set(Calendar.HOUR_OF_DAY, 23);
					dataFim.set(Calendar.MINUTE, 59);
					dataFim.set(Calendar.SECOND, 59);
					dataFim.set(Calendar.MILLISECOND, 999);
					
					queryUtil.adicionarFiltroEntre("u.aluno.dataCadastro", filtro.getAluno().getDataCadastro(),dataFim.getTime());
				}
				
				if(!DadosUtil.isEmpty(filtro.getAluno().getDataNascimento())) {
					
					Calendar dataFim = Calendar.getInstance();
					dataFim.setTime(filtro.getAluno().getDataNascimento());
					dataFim.set(Calendar.HOUR_OF_DAY, 23);
					dataFim.set(Calendar.MINUTE, 59);
					dataFim.set(Calendar.SECOND, 59);
					dataFim.set(Calendar.MILLISECOND, 999);
					
					queryUtil.adicionarFiltroEntre("u.aluno.dataNascimento", filtro.getAluno().getDataNascimento(),dataFim.getTime());
				}			
				
				if(!DadosUtil.isEmpty(filtro.getAluno().getEmail())) {
					queryUtil.adicionarFiltroAproximado("u.aluno.email", filtro.getAluno().getEmail());
				}
			}
			
			if(!DadosUtil.isEmpty(filtro.getUsuario())) {
				
				if(!DadosUtil.isEmpty(filtro.getUsuario().getIdUsuario())) {
					queryUtil.adicionarFiltroExato("u.usuario.id", filtro.getUsuario().getIdUsuario());
				}
				
				if(!DadosUtil.isEmpty(filtro.getUsuario().getAutoridade())) {
					queryUtil.adicionarFiltroExato("u.usuario.autoridade", filtro.getUsuario().getAutoridade());
				}
				
				if(!DadosUtil.isEmpty(filtro.getUsuario().getUsuario())) {
					queryUtil.adicionarFiltroAproximado("u.usuario.usuario", filtro.getUsuario().getUsuario());
				}
				
				if(!DadosUtil.isEmpty(filtro.getUsuario().getNome())) {
					queryUtil.adicionarFiltroAproximado("u.usuario.nome", filtro.getUsuario().getNome());
				}
				
				if(!DadosUtil.isEmpty(filtro.getUsuario().getDtCad())) {
					
					Calendar dataFim = Calendar.getInstance();
					dataFim.setTime(filtro.getUsuario().getDtCad());
					dataFim.set(Calendar.HOUR_OF_DAY, 23);
					dataFim.set(Calendar.MINUTE, 59);
					dataFim.set(Calendar.SECOND, 59);
					dataFim.set(Calendar.MILLISECOND, 999);
					
					queryUtil.adicionarFiltroEntre("u.usuario.dtCad", filtro.getUsuario().getDtCad(),dataFim.getTime());
				}
				
				if(!DadosUtil.isEmpty(filtro.getUsuario().getEndereco())) {
					queryUtil.adicionarFiltroAproximado("u.usuario.endereco", filtro.getUsuario().getEndereco());
				}
				if(!DadosUtil.isEmpty(filtro.getUsuario().getTelefone())) {
					queryUtil.adicionarFiltroAproximado("s.usuario.telefone", filtro.getUsuario().getTelefone());
				}
				
				if(!DadosUtil.isEmpty(filtro.getUsuario().getAtivo())) {
					queryUtil.adicionarFiltroExato("u.usuario.ativo", filtro.getUsuario().getAtivo());
				}
				
				if(!DadosUtil.isEmpty(filtro.getUsuario().getCpf())) {
					queryUtil.adicionarFiltroExato("u.usuario.cpf", filtro.getUsuario().getCpf());
				}
				
				if(!DadosUtil.isEmpty(filtro.getUsuario().getRg())) {
					queryUtil.adicionarFiltroExato("u.usuario.rg", filtro.getUsuario().getRg());
				}
			}
			
			if(!DadosUtil.isEmpty(filtro.getClasse())) {
				
				if(!DadosUtil.isEmpty(filtro.getClasse().getIdClasse())) {
					queryUtil.adicionarFiltroExato("u.classe.id", filtro.getClasse().getIdClasse());
				}
				
				if(!DadosUtil.isEmpty(filtro.getClasse().getNome())) {
					queryUtil.adicionarFiltroAproximado("u.classe.nome", filtro.getClasse().getNome());
				}
				
				if(!DadosUtil.isEmpty(filtro.getClasse().getTurma())) {
					queryUtil.adicionarFiltroAproximado("u.classe.turma", filtro.getClasse().getTurma());
				}
				
				if(!DadosUtil.isEmpty(filtro.getClasse().getAno())) {
					queryUtil.adicionarFiltroExato("u.classe.ano", filtro.getClasse().getAno());
				}	
			}

		}
		return queryUtil;
	}
	
//	private QueryUtil queryConsultarExisteEventoExecutado(EventoExecutado filtro, boolean count) {
//		
//		String sql = "SELECT u FROM EventoExecutado u";
//		QueryUtil queryUtil = new QueryUtil(sql, false, count);	
//		
//		
//		if(!DadosUtil.isEmpty(filtro)) {
//						
//			if(!DadosUtil.isEmpty(filtro.getIdEventoExecutado())) {
//				queryUtil.adicionarFiltroExato("u.idEventoExecutado", filtro.getIdEventoExecutado());
//			}					
//
//		}
//		return queryUtil;
//	}

	@Override
	public int consultarQtde(EventoExecutado filtro) throws DAOException {
		try{
//			QueryUtil queryCount = queryConsultarExisteEventoExecutado(filtro, true);
//			Number totalRegistros = (Number) queryCount.obterQuery(getEntityManager()).getSingleResult();
//			return totalRegistros.intValue();
			
			return 0;
		}catch(Exception e){
			throw new DAOException(e);
		}
	}

	@Override
	public void excluirEventoExecutado(EventoExecutado EventoExecutado) throws DAOException {
		try{
			delete(EventoExecutado);
		}catch(Exception e){
			throw new DAOException(e);
		}
	}

	@Override
	public EventoExecutado selectById(Long id) throws DAOException {
		try{
		return find(EventoExecutado.class, id);
		}catch(Exception e){
			throw new DAOException(e);
		}
	}
    
}
