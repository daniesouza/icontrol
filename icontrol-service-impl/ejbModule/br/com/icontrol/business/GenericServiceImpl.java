package br.com.icontrol.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import br.com.icontrol.dao.GenericDAOLocal;
import br.com.icontrol.entity.Entidade;
import br.com.icontrol.exceptions.DAOException;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
 * Session Bean implementation class GenericServiceImpl
 */
@Stateless
@Local(value=GenericServiceLocal.class)
public class GenericServiceImpl implements GenericServiceLocal {

	@SuppressWarnings("rawtypes")
	@EJB
	private GenericDAOLocal dao;	
	


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <T> T obterEntidade(Long id, Class clazz) throws ServicoException {
		try {
			T objeto = (T) dao.find(clazz,id);
			return objeto;
		} catch (DAOException e) {
			throw new ServicoException(e.getMessage());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <T> List<T> obterEntidades(Class clazz) throws ServicoException {
		try {
			List<T> objetos = dao.getAll(clazz.getName());
			return objetos;
		} catch (DAOException e) {
			throw new ServicoException(e.getMessage());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <T> T obterListaLazy(Object objeto, Class clazz, String relacionamento) throws ServicoException {
		try {
			T objetoRetorno = (T) dao.loadLazyRelationship(objeto, clazz.getName(), relacionamento);
			return objetoRetorno;
		} catch (DAOException e) {
			throw new ServicoException(e.getMessage());
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int count(Object objeto, Class clazz, String relacionamento) throws ServicoException {
		try {
			int count = dao.count(objeto, clazz.getName(), relacionamento);
			return count;
		} catch (DAOException e) {
			throw new ServicoException(e.getMessage());
		}
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <T> List<T> obterEntidades(Class clazz, Paginacao paginacao, String ordenacao) throws ServicoException {
		try {
			List<T> objetos = dao.getAll(clazz.getName(), paginacao, ordenacao);
			return objetos;
		} catch (DAOException e) {
			throw new ServicoException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void remover(List<T> objetos) throws ServicoException {
		try {
			for(T obj : objetos) {
				dao.delete(obj);				
			}
		} catch (DAOException e) {
			throw new ServicoException(e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> void remover(T objeto) throws ServicoException {
		try {
			dao.delete(objeto);
		} catch (DAOException e) {
			throw new ServicoException(e.getMessage());
		}
	}	

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entidade> void salvar(T objeto) throws ServicoException {
		try {
			dao.save(objeto);
		} catch (DAOException e) {
			throw new ServicoException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void atualizar(T objeto) throws ServicoException {
		try {
			dao.update(objeto);
		} catch (DAOException e) {
			throw new ServicoException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void detach(T objeto) throws ServicoException {
		
		try {
			dao.detach(objeto);
		} catch (DAOException e) {
			throw new ServicoException(e.getMessage());
		}
	}
}
