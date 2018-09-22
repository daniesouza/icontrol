package br.com.ischool.dao;

import java.util.Collection;

import br.com.ischool.entity.Aluno;
import br.com.ischool.exceptions.DAOException;
import br.com.ischool.util.Paginacao;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * 
 */
public interface AlunoDAOLocal {

	public void salvarAluno(Aluno aluno) throws DAOException;
	
	public void alterarAluno(Aluno aluno) throws DAOException;
	
	public Collection<Aluno> listarAlunos() throws DAOException;
	
	public Collection<Aluno> listarAlunos(Aluno filtro,Paginacao paginacao) throws DAOException;
	
	public void excluirAluno(Aluno aluno) throws DAOException;

	int consultarQtde(Aluno filtro) throws DAOException;
	
	public Aluno selectById(Long id) throws DAOException;
}
