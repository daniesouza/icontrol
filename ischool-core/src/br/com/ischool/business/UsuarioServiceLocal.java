package br.com.ischool.business;

import java.util.Collection;

import br.com.ischool.entity.Usuario;
import br.com.ischool.exceptions.ServicoException;
import br.com.ischool.util.Paginacao;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
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
