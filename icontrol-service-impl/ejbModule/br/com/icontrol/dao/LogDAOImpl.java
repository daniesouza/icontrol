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

import br.com.icontrol.entity.Log;
import br.com.icontrol.exceptions.DAOException;
import br.com.icontrol.util.DadosUtil;
import br.com.icontrol.util.Paginacao;
import br.com.icontrol.util.QueryUtil;

/**
 *  @author Icontrol
 * Session Bean implementation class LogDAOImpl
 */
@Stateless
@Local(value=LogDAOLocal.class)
public class LogDAOImpl extends GenericDAOImpl<Log,Long> implements LogDAOLocal {

    /**
     * Default constructor. 
     */
    public LogDAOImpl() {
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
	public void salvarLog(Log log) throws DAOException{
			
		try{
			
			save(log);
		
		}catch(Exception e){
			throw new DAOException(e);
		}
	
	}
	
	@Override
	public void alterarLog(Log log) throws DAOException {
	
		try{
			
			update(log);
			
		}catch(Exception e){
			throw new DAOException(e);
		}
		
	}


	
	@Override
	public Collection<Log> listarLogs() throws DAOException{
		
		try{			
	        return  getAll("Log");
	        
		}catch(Exception e){
			throw new DAOException(e);
		}

	}
	
	@Override
	public Collection<Log> listarLogs(Log filtro,Paginacao paginacao) throws DAOException{
		
		try{
			
			QueryUtil queryCount = queryConsultarLog(filtro, true);
			
			Number totalRegistros;
			
			try {
				totalRegistros = (Number) queryCount.obterQuery(getEntityManager()).getSingleResult();			
			} catch (NoResultException e) {
				totalRegistros = 0;
			}
			paginacao.setTotalRegistros(totalRegistros.intValue());
			
			QueryUtil queryRegistros = queryConsultarLog(filtro, false);
			
			queryRegistros.setPaginacao(paginacao);
			
			Query query = queryRegistros.obterQuery(getEntityManager());
			
			@SuppressWarnings("unchecked")
			List<Log> logs = query.getResultList();
			
			return logs;

		}catch(Exception e){
			throw new DAOException(e);
		}

	}
	
	
	private QueryUtil queryConsultarLog(Log filtro, boolean count) {
	
		String sql = "SELECT u FROM Log u";
		QueryUtil queryUtil = new QueryUtil(sql, false, count);	
		
		
		if(!DadosUtil.isEmpty(filtro)) {
			
			if(!DadosUtil.isEmpty(filtro.getDescricao())) {
				queryUtil.adicionarFiltroAproximado("u.descricao", filtro.getDescricao());
			}
			
			if(!DadosUtil.isEmpty(filtro.getTipo())) {
				queryUtil.adicionarFiltroExato("u.tipo", filtro.getTipo());
			}
			
			if(!DadosUtil.isEmpty(filtro.getDtCad())) {
				
				Calendar dataFim = Calendar.getInstance();
				dataFim.setTime(filtro.getDtCad());
				dataFim.set(Calendar.HOUR_OF_DAY, 23);
				dataFim.set(Calendar.MINUTE, 59);
				dataFim.set(Calendar.SECOND, 59);
				dataFim.set(Calendar.MILLISECOND, 999);
				
				queryUtil.adicionarFiltroEntre("u.dtCad", filtro.getDtCad(),dataFim.getTime());
			}
			
			if(!DadosUtil.isEmpty(filtro.getUsuario())) {
				
				if(!DadosUtil.isEmpty(filtro.getUsuario().getUsuario())) {
					queryUtil.adicionarFiltroAproximado("u.usuario.usuario", filtro.getUsuario().getUsuario());
				}
				
				if(!DadosUtil.isEmpty(filtro.getUsuario().getNome())) {
					queryUtil.adicionarFiltroAproximado("u.usuario.nome", filtro.getUsuario().getNome());
				}
				
				if(!DadosUtil.isEmpty(filtro.getUsuario().getCliente())) {

					if(!DadosUtil.isEmpty(filtro.getUsuario().getCliente().getId())) {
						queryUtil.adicionarFiltroExato("u.usuario.cliente.idCliente", filtro.getUsuario().getCliente().getId());
					}
					
					if(!DadosUtil.isEmpty(filtro.getUsuario().getCliente().getNome())) {
						queryUtil.adicionarFiltroAproximado("u.usuario.cliente.nome", filtro.getUsuario().getCliente().getNome());
					}
					
					if(!DadosUtil.isEmpty(filtro.getUsuario().getCliente().getCpfCnpj())) {
						queryUtil.adicionarFiltroExato("u.usuario.cliente.cpfCnpj", filtro.getUsuario().getCliente().getCpfCnpj());
					}					
				}
			}

			

		}
		return queryUtil;
	}

	@Override
	public void excluirLog(Log log) throws DAOException {
		try{
			delete(log);
		}catch(Exception e){
			throw new DAOException(e);
		}
	}

	
	@Override
	public Log selectById(Long id) throws DAOException {
		
		return find(Log.class, id);
	}


    
}
