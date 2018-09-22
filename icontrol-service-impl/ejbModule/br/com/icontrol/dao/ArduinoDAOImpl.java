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

import br.com.icontrol.entity.Arduino;
import br.com.icontrol.exceptions.DAOException;
import br.com.icontrol.util.DadosUtil;
import br.com.icontrol.util.Paginacao;
import br.com.icontrol.util.QueryUtil;

/**
 * Session Bean implementation class ArduinoDAOImpl
 *  @author Icontrol
 */


@Stateless
@Local(value = ArduinoDAOLocal.class)
public class ArduinoDAOImpl extends GenericDAOImpl<Arduino, Long> implements ArduinoDAOLocal {

	/**
	 * Default constructor.
	 */
	public ArduinoDAOImpl() {
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	public void carregarInformacoes() {
		System.out.println("CARREGADO OS RECURSOS DO EJB "+ this.getClass().getName());
	}

	@PreDestroy
	public void clear() {
		System.out.println("LIBERANDO OS RECURSOS DO EJB "+ this.getClass().getName());
	}

	@Override
	public void salvarArduino(Arduino arduino) throws DAOException {

		try {

			save(arduino);

		} catch (Exception e) {
			throw new DAOException(e);
		}

	}

	@Override
	public void alterarArduino(Arduino arduino) throws DAOException {

		try {

			update(arduino);

		} catch (Exception e) {
			throw new DAOException(e);
		}

	}

	@Override
	public Collection<Arduino> listarArduinos() throws DAOException {

		try {
			return getAll("Arduino");

		} catch (Exception e) {
			throw new DAOException(e);
		}

	}

	@Override
	public Collection<Arduino> listarArduinosSemCliente() throws DAOException {

		try {
			
			String sql = "SELECT u FROM Arduino u where u.cliente = null";
			Query query = getEntityManager().createQuery(sql);
			
			@SuppressWarnings("unchecked")
			List<Arduino> arduinos = query.getResultList();

			return arduinos;

		} catch (Exception e) {
			throw new DAOException(e);
		}

	}

	@Override
	public Collection<Arduino> listarArduinos(Arduino filtro,Paginacao paginacao) throws DAOException {

		try {

			QueryUtil queryCount = queryConsultarArduino(filtro, true);

			Number totalRegistros;

			try {
				totalRegistros = (Number) queryCount.obterQuery(getEntityManager()).getSingleResult();
			} catch (NoResultException e) {
				totalRegistros = 0;
			}
			paginacao.setTotalRegistros(totalRegistros.intValue());

			QueryUtil queryRegistros = queryConsultarArduino(filtro, false);

			queryRegistros.setPaginacao(paginacao);

			Query query = queryRegistros.obterQuery(getEntityManager());

			@SuppressWarnings("unchecked")
			List<Arduino> arduinos = query.getResultList();

			return arduinos;

		} catch (Exception e) {
			throw new DAOException(e);
		}

	}

	private QueryUtil queryConsultarArduino(Arduino filtro, boolean count) {

		String sql = "SELECT u FROM Arduino u";
		QueryUtil queryUtil = new QueryUtil(sql, false, count);

		if (!DadosUtil.isEmpty(filtro)) {

			if (!DadosUtil.isEmpty(filtro.getCodigo())) {
				queryUtil.adicionarFiltroAproximado("u.codigo",	filtro.getCodigo());
			}

			if (!DadosUtil.isEmpty(filtro.getNome())) {
				queryUtil.adicionarFiltroAproximado("u.nome", filtro.getNome());
			}
			
			if(!DadosUtil.isEmpty(filtro.getCliente())) {
				
				if(!DadosUtil.isEmpty(filtro.getCliente().getIdCliente())) {
					queryUtil.adicionarFiltroExato("u.cliente.idCliente", filtro.getCliente().getIdCliente());
				}
				
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
					queryUtil.adicionarFiltroExato("u.cliente.cep", filtro.getCliente().getCep());
				}
				
				if(!DadosUtil.isEmpty(filtro.getCliente().getBairro())) {
					queryUtil.adicionarFiltroAproximado("u.cliente.bairro", filtro.getCliente().getBairro());
				}
				
				if(!DadosUtil.isEmpty(filtro.getCliente().getEstado())) {
					queryUtil.adicionarFiltroExato("u.cliente.estado", filtro.getCliente().getEstado());
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

	private QueryUtil queryConsultarExisteArduino(Arduino filtro, boolean count) {

		String sql = "SELECT u FROM Arduino u";
		QueryUtil queryUtil = new QueryUtil(sql, false, count);

		if (!DadosUtil.isEmpty(filtro)) {

			if (!DadosUtil.isEmpty(filtro.getCodigo())) {
				queryUtil.adicionarFiltroExato("u.codigo", filtro.getCodigo());
			}

		}
		return queryUtil;
	}

	@Override
	public Arduino selectById(Long id) throws DAOException {
		try {
			return find(Arduino.class, id);
		} catch (Exception e) {
			throw new DAOException(e);
		}

	}

	@Override
	public int consultarQtde(Arduino filtro) throws DAOException {

		try {
			QueryUtil queryCount = queryConsultarExisteArduino(filtro, true);
			Number totalRegistros = (Number) queryCount.obterQuery(getEntityManager()).getSingleResult();
			return totalRegistros.intValue();

		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public void excluirArduino(Arduino Arduino) throws DAOException {
		try {
			delete(Arduino);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	
	

}
