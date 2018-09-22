package br.com.icontrol.business;

import java.util.Collection;

import br.com.icontrol.entity.Usuario;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
 * 
 */
public interface UsuarioServiceLocal {

	public void salvarUsuario(Usuario usuario) throws ServicoException;
	
	public void alterarUsuario(Usuario usuario) throws ServicoException;
	
	public Collection<Usuario> listarUsuarios() throws ServicoException;
	
	public Collection<Usuario> listarUsuarios(Usuario filtro,Paginacao paginacao) throws ServicoException;
	
	public Usuario pesquisarUsuarioNomeUsuario(String nome) throws ServicoException;
	
	public void excluirUsuario(Usuario usuario) throws ServicoException;

	public Collection<Usuario> listarUsuariosSemClienteSemAdmin() throws ServicoException;
	
	public Usuario selectById(Long id) throws ServicoException;
}
