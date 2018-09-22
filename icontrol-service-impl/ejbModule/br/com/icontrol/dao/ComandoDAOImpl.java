package br.com.icontrol.dao;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.icontrol.entity.Comando;
import br.com.icontrol.exceptions.DAOException;
import br.com.icontrol.util.DadosUtil;
import br.com.icontrol.util.Paginacao;
import br.com.icontrol.util.QueryUtil;

/**
 *  @author Icontrol
 * Session Bean implementation class ComandoDAOImpl
 */
@Stateless
@Local(value=ComandoDAOLocal.class)
public class ComandoDAOImpl extends GenericDAOImpl<Comando,Long> implements ComandoDAOLocal {

    /**
     * Default constructor. 
     */
    public ComandoDAOImpl() {
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
	public void salvarComando(Comando comando) throws DAOException{
			
		try{
			
			save(comando);
		
		}catch(Exception e){
			throw new DAOException(e);
		}
	
	}
	
	@Override
	public void alterarComando(Comando comando) throws DAOException {
	
		try{
			
			update(comando);
			
		}catch(Exception e){
			throw new DAOException(e);
		}
		
	}


	
	@Override
	public Collection<Comando> listarComandos() throws DAOException{
		
		try{			
	        return  getAll("Comando");
	        
		}catch(Exception e){
			throw new DAOException(e);
		}

	}
	
	@Override
	public Collection<Comando> listarComandos(Comando filtro,Paginacao paginacao) throws DAOException{
		
		try{
			
			QueryUtil queryCount = queryConsultarComando(filtro, true);
			
			Number totalRegistros;
			
			try {
				totalRegistros = (Number) queryCount.obterQuery(getEntityManager()).getSingleResult();			
			} catch (NoResultException e) {
				totalRegistros = 0;
			}
			paginacao.setTotalRegistros(totalRegistros.intValue());
			
			QueryUtil queryRegistros = queryConsultarComando(filtro, false);
			
			queryRegistros.setPaginacao(paginacao);
			
			Query query = queryRegistros.obterQuery(getEntityManager());
			
			@SuppressWarnings("unchecked")
			List<Comando> comandos = query.getResultList();
			
			return comandos;

		}catch(Exception e){
			throw new DAOException(e);
		}

	}
	
	
	private QueryUtil queryConsultarComando(Comando filtro, boolean count) {
	
		String sql = "SELECT u FROM Comando u";
		QueryUtil queryUtil = new QueryUtil(sql, false, count);	
		
		
		if(!DadosUtil.isEmpty(filtro)) {
			
			if(!DadosUtil.isEmpty(filtro.getCodigo())) {
				queryUtil.adicionarFiltroAproximado("u.codigo", filtro.getCodigo());
			}
			
			if(!DadosUtil.isEmpty(filtro.getNome())) {
				queryUtil.adicionarFiltroAproximado("u.nome", filtro.getNome());
			}
			
			if(!DadosUtil.isEmpty(filtro.getTipo())) {
				queryUtil.adicionarFiltroExato("u.tipo", filtro.getTipo());
			}

			

		}
		return queryUtil;
	}

	@Override
	public void excluirComando(Comando comando) throws DAOException {
		try{
			delete(comando);
		}catch(Exception e){
			throw new DAOException(e);
		}
	}

	private QueryUtil queryConsultarExisteComando(Comando filtro, boolean count) {
		
		String sql = "SELECT u FROM Comando u";
		QueryUtil queryUtil = new QueryUtil(sql, false, count);	
		
		
		if(!DadosUtil.isEmpty(filtro)) {
			
			if(!DadosUtil.isEmpty(filtro.getCodigo())) {
				queryUtil.adicionarFiltroExato("u.codigo", filtro.getCodigo());
			}
		}
		return queryUtil;
	}
	
	@Override
	public Comando selectById(Long id) throws DAOException {
		
		return find(Comando.class, id);
	}
	
	@Override
	public int consultarQtde(Comando filtro) throws DAOException {
		QueryUtil queryCount = queryConsultarExisteComando(filtro, true);
		Number totalRegistros = (Number) queryCount.obterQuery(getEntityManager()).getSingleResult();
		return totalRegistros.intValue();
	}

    
}
