package br.com.ischool.business;

import java.util.Collection;
import java.util.List;

import br.com.ischool.entity.Aluno;
import br.com.ischool.entity.Classe;
import br.com.ischool.entity.Usuario;
import br.com.ischool.exceptions.ServicoException;
import br.com.ischool.util.Paginacao;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * 
 */
public interface AlunoServiceLocal {

	public void salvarAluno(Aluno aluno) throws ServicoException;
	
	public void alterarAluno(Aluno aluno,List<Usuario> listaRemoverUsuarios,List<Classe> listaRemoverClasses) throws ServicoException;
	
	public Collection<Aluno> listarAlunos() throws ServicoException;
	
	public Collection<Aluno> listarAlunos(Aluno filtro,Paginacao paginacao) throws ServicoException;
	
	public void excluirAluno(Aluno aluno) throws ServicoException;

	public Aluno selectById(Long id) throws ServicoException;

}
