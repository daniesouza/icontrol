package br.com.icontrol.dao;

import java.util.Collection;

import br.com.icontrol.entity.Usuario;
import br.com.icontrol.exceptions.DAOException;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
 * 
 */
public interface UsuarioDAOLocal {

	public void salvarUsuario(Usuario usuario) throws DAOException;
	
	public void alterarUsuario(Usuario usuario) throws DAOException;
	
	public Collection<Usuario> listarUsuarios() throws DAOException;
	
	public Collection<Usuario> listarUsuarios(Usuario filtro,Paginacao paginacao) throws DAOException;
	
	public void excluirUsuario(Usuario usuario) throws DAOException;

	public Collection<Usuario> listarUsuariosSemClienteSemAdmin() throws DAOException;
	
	public Usuario selectById(Long id) throws DAOException;
	
	public int consultarQtde(Usuario filtro) throws DAOException;

	public Usuario pesquisarUsuarioNomeUsuario(String nomeUsuario) throws DAOException;
}
