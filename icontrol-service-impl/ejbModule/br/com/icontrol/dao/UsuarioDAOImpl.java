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

import br.com.icontrol.entity.Usuario;
import br.com.icontrol.exceptions.DAOException;
import br.com.icontrol.util.DadosUtil;
import br.com.icontrol.util.Paginacao;
import br.com.icontrol.util.QueryUtil;

/**
 * @author Icontrol
 * Session Bean implementation class UsuarioService
 */
@Stateless
@Local(UsuarioDAOLocal.class)
public class UsuarioDAOImpl extends GenericDAOImpl<Usuario,Long> implements UsuarioDAOLocal {


    /**
     * Default constructor. 
     */
    public UsuarioDAOImpl() {
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
	public void salvarUsuario(Usuario usuario) throws DAOException{
			
		try{
			
			save(usuario);
		
		}catch(Exception e){
			throw new DAOException(e);
		}
	
	}
	
	@Override
	public void alterarUsuario(Usuario usuario) throws DAOException {
	
		try{
			
			update(usuario);
			
		}catch(Exception e){
			throw new DAOException(e);
		}
		
	}


	
	@Override
	public Collection<Usuario> listarUsuarios() throws DAOException{
		
		try{			
	        return  getAll("Usuario");
	        
		}catch(Exception e){
			throw new DAOException(e);
		}

	}
	
	@Override
	public Collection<Usuario> listarUsuarios(Usuario filtro,Paginacao paginacao) throws DAOException{
		
		try{
			
			QueryUtil queryCount = queryConsultarUsuario(filtro, true);
			
			Number totalRegistros;
			
			try {
				totalRegistros = (Number) queryCount.obterQuery(getEntityManager()).getSingleResult();			
			} catch (NoResultException e) {
				totalRegistros = 0;
			}
			paginacao.setTotalRegistros(totalRegistros.intValue());
			
			QueryUtil queryRegistros = queryConsultarUsuario(filtro, false);
			
			queryRegistros.setPaginacao(paginacao);
			
			Query query = queryRegistros.obterQuery(getEntityManager());
			
			@SuppressWarnings("unchecked")
			List<Usuario> usuarios = query.getResultList();
			
			return usuarios;

		}catch(Exception e){
			throw new DAOException(e);
		}

	}
	
	
	private QueryUtil queryConsultarUsuario(Usuario filtro, boolean count) {
	
		String sql = "SELECT u FROM Usuario u";
		QueryUtil queryUtil = new QueryUtil(sql, false, count);	
		
		
		if(!DadosUtil.isEmpty(filtro)) {
			
			if(!DadosUtil.isEmpty(filtro.getAutoridade())) {
				queryUtil.adicionarFiltroExato("u.autoridade", filtro.getAutoridade());
			}
			
			if(!DadosUtil.isEmpty(filtro.getUsuario())) {
				queryUtil.adicionarFiltroAproximado("u.usuario", filtro.getUsuario());
			}
			
			if(!DadosUtil.isEmpty(filtro.getNome())) {
				queryUtil.adicionarFiltroAproximado("u.nome", filtro.getNome());
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
			
			if(!DadosUtil.isEmpty(filtro.getEndereco())) {
				queryUtil.adicionarFiltroAproximado("u.endereco", filtro.getEndereco());
			}
			if(!DadosUtil.isEmpty(filtro.getTelefone())) {
				queryUtil.adicionarFiltroAproximado("s.telefone", filtro.getTelefone());
			}
			
			if(!DadosUtil.isEmpty(filtro.getAtivo())) {
				queryUtil.adicionarFiltroExato("u.ativo", filtro.getAtivo());
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
	public Usuario selectById(Long id) throws DAOException {
		try {
			return find(Usuario.class, id);
		} catch (Exception e) {
			throw new DAOException(e);
		}

	}
	
	private QueryUtil queryConsultarExisteUsuario(Usuario filtro, boolean count) {

		String sql = "SELECT u FROM Usuario u";
		QueryUtil queryUtil = new QueryUtil(sql, false, count);

		if (!DadosUtil.isEmpty(filtro)) {

			if (!DadosUtil.isEmpty(filtro.getUsuario())) {
				queryUtil.adicionarFiltroExato("u.usuario", filtro.getUsuario());
			}

		}
		return queryUtil;
	}

	@Override
	public int consultarQtde(Usuario filtro) throws DAOException {

		try {
			QueryUtil queryCount = queryConsultarExisteUsuario(filtro, true);
			Number totalRegistros = (Number) queryCount.obterQuery(getEntityManager()).getSingleResult();
			return totalRegistros.intValue();

		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public void excluirUsuario(Usuario usuario) throws DAOException {
		try{
			delete(usuario);
		}catch(Exception e){
			throw new DAOException(e);
		}
	}

	@Override
	public Collection<Usuario> listarUsuariosSemClienteSemAdmin() throws DAOException {
		try {
			
			String sql = "SELECT u FROM Usuario u where u.cliente = null and u.autoridade != 'ROLE_ADMIN'";
			Query query = getEntityManager().createQuery(sql);
			
			@SuppressWarnings("unchecked")
			List<Usuario> usuarios = query.getResultList();

			return usuarios;

		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	

	@Override
	public Usuario pesquisarUsuarioNomeUsuario(String nomeUsuario) throws DAOException {
		        		
		try{
			
			return (Usuario) getEntityManager().createQuery("Select u from Usuario u where u.usuario = :nome").setParameter("nome", nomeUsuario).getSingleResult();

		}catch(Exception e){			
			throw new DAOException(e);
		}
	
	}



}
