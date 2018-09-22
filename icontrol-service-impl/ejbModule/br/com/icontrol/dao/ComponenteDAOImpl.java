package br.com.icontrol.dao;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.icontrol.entity.Componente;
import br.com.icontrol.exceptions.DAOException;
import br.com.icontrol.util.DadosUtil;
import br.com.icontrol.util.Paginacao;
import br.com.icontrol.util.QueryUtil;

/**
 *  @author Icontrol
 * Session Bean implementation class ComponenteDAOImpl
 */
@Stateless
@Local(value=ComponenteDAOLocal.class)
public class ComponenteDAOImpl extends GenericDAOImpl<Componente,Long> implements ComponenteDAOLocal {

    /**
     * Default constructor. 
     */
    public ComponenteDAOImpl() {
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
	public void salvarComponente(Componente componente) throws DAOException{
			
		try{
			
			save(componente);
		
		}catch(Exception e){
			throw new DAOException(e);
		}
	
	}
	
	@Override
	public void alterarComponente(Componente componente) throws DAOException {
	
		try{
			
			update(componente);
			
		}catch(Exception e){
			throw new DAOException(e);
		}
		
	}


	
	@Override
	public Collection<Componente> listarComponentes() throws DAOException{
		
		try{			
	        return  getAll("Componente");
	        
		}catch(Exception e){
			throw new DAOException(e);
		}

	}
	
	@Override
	public Collection<Componente> listarComponentes(Componente filtro,Paginacao paginacao) throws DAOException{
		
		try{
			
			QueryUtil queryCount = queryConsultarComponente(filtro, true);
			
			Number totalRegistros;
			
			try {
				totalRegistros = (Number) queryCount.obterQuery(getEntityManager()).getSingleResult();			
			} catch (NoResultException e) {
				totalRegistros = 0;
			}
			paginacao.setTotalRegistros(totalRegistros.intValue());
			
			QueryUtil queryRegistros = queryConsultarComponente(filtro, false);
			
			queryRegistros.setPaginacao(paginacao);
			
			Query query = queryRegistros.obterQuery(getEntityManager());
			
			@SuppressWarnings("unchecked")
			List<Componente> Componentes = query.getResultList();
			
			return Componentes;

		}catch(Exception e){
			throw new DAOException(e);
		}

	}
	
	
	private QueryUtil queryConsultarComponente(Componente filtro, boolean count) {
	
		String sql = "SELECT u FROM Componente u";
		QueryUtil queryUtil = new QueryUtil(sql, false, count);	
		
		
		if(!DadosUtil.isEmpty(filtro)) {
			
			if(!DadosUtil.isEmpty(filtro.getCodigo())) {
				queryUtil.adicionarFiltroAproximado("u.codigo", filtro.getCodigo());
			}
			
			if(!DadosUtil.isEmpty(filtro.getNome())) {
				queryUtil.adicionarFiltroAproximado("u.nome", filtro.getNome());
			}
			
			if(!DadosUtil.isEmpty(filtro.getMarca())) {
				queryUtil.adicionarFiltroAproximado("u.marca", filtro.getMarca());
			}
			
			if(!DadosUtil.isEmpty(filtro.getPortaArduino())) {
				queryUtil.adicionarFiltroExato("u.portaArduino", filtro.getPortaArduino());
			}
			

		}
		return queryUtil;
	}


	@Override
	public void excluirComponente(Componente Componente) throws DAOException {
		try{
			delete(Componente);
		}catch(Exception e){
			throw new DAOException(e);
		}
	}

	private QueryUtil queryConsultarExisteComponente(Componente filtro, boolean count) {
		
		String sql = "SELECT u FROM Componente u";
		QueryUtil queryUtil = new QueryUtil(sql, false, count);	
		
		
		if(!DadosUtil.isEmpty(filtro)) {
			
			if(!DadosUtil.isEmpty(filtro.getCodigo())) {
				queryUtil.adicionarFiltroExato("u.codigo", filtro.getCodigo());
			}
						
		}
		return queryUtil;
	}
	
	@Override
	public Componente selectById(Long id) throws DAOException {
		
		return find(Componente.class, id);
	}
	
	@Override
	public int consultarQtde(Componente filtro) throws DAOException {
		QueryUtil queryCount = queryConsultarExisteComponente(filtro, true);
		Number totalRegistros = (Number) queryCount.obterQuery(getEntityManager()).getSingleResult();
		return totalRegistros.intValue();
	}
    
}
