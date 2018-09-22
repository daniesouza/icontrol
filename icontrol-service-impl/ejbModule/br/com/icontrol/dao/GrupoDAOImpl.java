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

import br.com.icontrol.entity.Grupo;
import br.com.icontrol.entity.GrupoArdCompPermissoes;
import br.com.icontrol.exceptions.DAOException;
import br.com.icontrol.util.DadosUtil;
import br.com.icontrol.util.Paginacao;
import br.com.icontrol.util.QueryUtil;

/**
 * @author Icontrol
 * Session Bean implementation class GrupoDAOImpl
 */
@Stateless
@Local(value=GrupoDAOLocal.class)
public class GrupoDAOImpl extends GenericDAOImpl<Grupo,Long> implements GrupoDAOLocal {

    /**
     * Default constructor. 
     */
    public GrupoDAOImpl() {
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
	public void salvarGrupo(Grupo grupo) throws DAOException{
			
		try{
			
			save(grupo);
		
		}catch(Exception e){
			throw new DAOException(e);
		}
	
	}
	
	@Override
	public void alterarGrupo(Grupo grupo) throws DAOException {
	
		try{
			
			update(grupo);
			
		}catch(Exception e){
			throw new DAOException(e);
		}
		
	}


	
	@Override
	public Collection<Grupo> listarGrupos() throws DAOException{
		
		try{			
	        return  getAll("Grupo");
	        
		}catch(Exception e){
			throw new DAOException(e);
		}

	}
	
	@Override
	public Collection<Grupo> listarGrupos(Grupo filtro,Paginacao paginacao) throws DAOException{
		
		try{
			
			QueryUtil queryCount = queryConsultarGrupo(filtro, true);
			
			Number totalRegistros;
			
			try {
				totalRegistros = (Number) queryCount.obterQuery(getEntityManager()).getSingleResult();			
			} catch (NoResultException e) {
				totalRegistros = 0;
			}
			paginacao.setTotalRegistros(totalRegistros.intValue());
			
			QueryUtil queryRegistros = queryConsultarGrupo(filtro, false);
			
			queryRegistros.setPaginacao(paginacao);
			
			Query query = queryRegistros.obterQuery(getEntityManager());
			
			@SuppressWarnings("unchecked")
			List<Grupo> Grupos = query.getResultList();
			
			return Grupos;

		}catch(Exception e){
			throw new DAOException(e);
		}

	}
	
	
	private QueryUtil queryConsultarGrupo(Grupo filtro, boolean count) {
	
		String sql = "SELECT u FROM Grupo u";
		QueryUtil queryUtil = new QueryUtil(sql, false, count);	
		
		
		if(!DadosUtil.isEmpty(filtro)) {
			
			if(!DadosUtil.isEmpty(filtro.getDescricao())) {
				queryUtil.adicionarFiltroAproximado("u.descricao", filtro.getDescricao());
			}
			
			if(!DadosUtil.isEmpty(filtro.getNome())) {
				queryUtil.adicionarFiltroAproximado("u.nome", filtro.getNome());
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

	
	private QueryUtil queryConsultarExisteGrupo(Grupo filtro, boolean count) {
		
		String sql = "SELECT u FROM Grupo u";
		QueryUtil queryUtil = new QueryUtil(sql, false, count);	
		
		
		if(!DadosUtil.isEmpty(filtro)) {
			
			if(!DadosUtil.isEmpty(filtro.getNome())) {
				queryUtil.adicionarFiltroExato("u.nome", filtro.getNome());
			}
						

		}
		return queryUtil;
	}
	
	@Override
	public Grupo selectById(Long id) throws DAOException {
		
		return find(Grupo.class, id);
	}
	
	@Override
	public int consultarQtde(Grupo filtro) throws DAOException {
		QueryUtil queryCount = queryConsultarExisteGrupo(filtro, true);
		Number totalRegistros = (Number) queryCount.obterQuery(getEntityManager()).getSingleResult();
		return totalRegistros.intValue();
	}

	@Override
	public void excluirGrupo(Grupo grupo) throws DAOException {
		try{
			delete(grupo);
		}catch(Exception e){
			throw new DAOException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GrupoArdCompPermissoes> obterGrupoArdCompPermissoes(long idGrupo)	throws DAOException {
		String hql = "SELECT mf FROM GrupoArdCompPermissoes mf join fetch mf.grupo join fetch mf.arduino join fetch mf.comando join fetch mf.componente WHERE mf.grupo.idGrupo = :idGrupo";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("idGrupo", idGrupo);
		List<GrupoArdCompPermissoes> grupoArdCompPermissoes = query.getResultList();
		return grupoArdCompPermissoes;
	}

    
}
