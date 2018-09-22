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

import br.com.ischool.entity.EventoClasse;
import br.com.ischool.exceptions.DAOException;
import br.com.ischool.util.DadosUtil;
import br.com.ischool.util.Paginacao;
import br.com.ischool.util.QueryUtil;

/**
 *  @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * Session Bean implementation class EventoClasseDAOImpl
 */
@Stateless
@Local(value=EventoClasseDAOLocal.class)
public class EventoClasseDAOImpl extends GenericDAOImpl<EventoClasse,Long> implements EventoClasseDAOLocal {

    /**
     * Default constructor. 
     */
    public EventoClasseDAOImpl() {
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
	public void salvarEventoClasse(EventoClasse EventoClasse) throws DAOException{
			
		try{
			
			save(EventoClasse);
		
		}catch(Exception e){
			throw new DAOException(e);
		}
	
	}
	
	@Override
	public void alterarEventoClasse(EventoClasse EventoClasse) throws DAOException {
	
		try{
			
			update(EventoClasse);
			
		}catch(Exception e){
			throw new DAOException(e);
		}
		
	}


	
	@Override
	public Collection<EventoClasse> listarEventoClasses() throws DAOException{
		
		try{			
	        return  getAll("EventoClasse");
	        
		}catch(Exception e){
			throw new DAOException(e);
		}

	}
	
	@Override
	public Collection<EventoClasse> listarEventoClasses(EventoClasse filtro,Paginacao paginacao) throws DAOException{
		
		try{
			
			QueryUtil queryCount = queryConsultarEventoClasse(filtro, true);
			
			Number totalRegistros;
			
			try {
				totalRegistros = (Number) queryCount.obterQuery(getEntityManager()).getSingleResult();			
			} catch (NoResultException e) {
				totalRegistros = 0;
			}
			paginacao.setTotalRegistros(totalRegistros.intValue());
			
			QueryUtil queryRegistros = queryConsultarEventoClasse(filtro, false);
			
			queryRegistros.setPaginacao(paginacao);
			
			Query query = queryRegistros.obterQuery(getEntityManager());
			
			@SuppressWarnings("unchecked")
			List<EventoClasse> EventoClasses = query.getResultList();
			
			return EventoClasses;

		}catch(Exception e){
			throw new DAOException(e);
		}

	}
	
	
	private QueryUtil queryConsultarEventoClasse(EventoClasse filtro, boolean count) {
	
		String sql = "SELECT u FROM EventoClasse u";
		QueryUtil queryUtil = new QueryUtil(sql, false, count);	
		
		
		if(!DadosUtil.isEmpty(filtro)) {
			
			if(!DadosUtil.isEmpty(filtro.getIdEventoClasse())) {
				queryUtil.adicionarFiltroExato("u.idEventoClasse", filtro.getIdEventoClasse());
			}	
							
			if(!DadosUtil.isEmpty(filtro.getTipo())) {
				queryUtil.adicionarFiltroExato("u.tipo", filtro.getTipo());
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
			
			if(!DadosUtil.isEmpty(filtro.getEvento())) {

				if(!DadosUtil.isEmpty(filtro.getEvento().getIdEvento())) {
					queryUtil.adicionarFiltroExato("u.evento.id", filtro.getEvento().getIdEvento());
				}
				
				if(!DadosUtil.isEmpty(filtro.getEvento().getCodigoEvento())) {
					queryUtil.adicionarFiltroAproximado("u.evento.codigoEvento", filtro.getEvento().getCodigoEvento());
				}
				
				if(!DadosUtil.isEmpty(filtro.getEvento().getNome())) {
					queryUtil.adicionarFiltroAproximado("u.evento.nome", filtro.getEvento().getNome());
				}
				
				if(!DadosUtil.isEmpty(filtro.getEvento().getDataCadastro())) {
					
					Calendar dataFim = Calendar.getInstance();
					dataFim.setTime(filtro.getEvento().getDataCadastro());
					dataFim.set(Calendar.HOUR_OF_DAY, 23);
					dataFim.set(Calendar.MINUTE, 59);
					dataFim.set(Calendar.SECOND, 59);
					dataFim.set(Calendar.MILLISECOND, 999);
					
					queryUtil.adicionarFiltroEntre("u.evento.dataCadastro", filtro.getEvento().getDataCadastro(),dataFim.getTime());
				}		
				
			}													

		}
		return queryUtil;
	}
	
//	private QueryUtil queryConsultarExisteEventoClasse(EventoClasse filtro, boolean count) {
//		
//		String sql = "SELECT u FROM EventoClasse u";
//		QueryUtil queryUtil = new QueryUtil(sql, false, count);	
//		
//		
//		if(!DadosUtil.isEmpty(filtro)) {
//						
//			if(!DadosUtil.isEmpty(filtro.getClasse().getIdClasse())) {
//				queryUtil.adicionarFiltroExato("u.classe.idClasse", filtro.getClasse().getIdClasse());
//			}					
//
//		}
//		return queryUtil;
//	}

	@Override
	public int consultarQtde(EventoClasse filtro) throws DAOException {
		try{
//			QueryUtil queryCount = queryConsultarExisteEventoClasse(filtro, true);
//			Number totalRegistros = (Number) queryCount.obterQuery(getEntityManager()).getSingleResult();
//			return totalRegistros.intValue();
			
			return 0;
		}catch(Exception e){
			throw new DAOException(e);
		}
	}

	@Override
	public void excluirEventoClasse(EventoClasse EventoClasse) throws DAOException {
		try{
			delete(EventoClasse);
		}catch(Exception e){
			throw new DAOException(e);
		}
	}

	@Override
	public EventoClasse selectById(Long id) throws DAOException {
		try{
		return find(EventoClasse.class, id);
		}catch(Exception e){
			throw new DAOException(e);
		}
	}

	@Override
	public void limparEventosClasse(Long idClasse) throws DAOException{
		
		getEntityManager().createQuery("DELETE from EventoClasse u where u.classe.idClasse = :idClasse").setParameter("idClasse", idClasse).executeUpdate();
		
	}
    
}
