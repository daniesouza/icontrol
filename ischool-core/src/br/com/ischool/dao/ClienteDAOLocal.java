package br.com.ischool.dao;

import java.util.Collection;

import br.com.ischool.entity.Cliente;
import br.com.ischool.exceptions.DAOException;
import br.com.ischool.util.Paginacao;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * 
 */
public interface ClienteDAOLocal {

	public void salvarCliente(Cliente cliente) throws DAOException;
	
	public void alterarCliente(Cliente cliente) throws DAOException;
	
	public Collection<Cliente> listarClientes() throws DAOException;
	
	public Collection<Cliente> listarClientes(Cliente filtro,Paginacao paginacao) throws DAOException;
	
	public void excluirCliente(Cliente cliente) throws DAOException;

	int consultarQtde(Cliente filtro) throws DAOException;
	
	public Cliente selectById(Long id) throws DAOException;
}
