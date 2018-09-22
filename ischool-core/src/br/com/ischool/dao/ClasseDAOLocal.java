package br.com.ischool.dao;

import java.util.Collection;

import br.com.ischool.entity.Classe;
import br.com.ischool.exceptions.DAOException;
import br.com.ischool.util.Paginacao;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * 
 */
public interface ClasseDAOLocal {

	public void salvarClasse(Classe classe) throws DAOException;
	
	public void alterarClasse(Classe classe) throws DAOException;
	
	public Collection<Classe> listarClasses() throws DAOException;
	
	public Collection<Classe> listarClasses(Classe filtro,Paginacao paginacao) throws DAOException;
	
	public void excluirClasse(Classe classe) throws DAOException;

	int consultarQtde(Classe filtro) throws DAOException;
	
	public Classe selectById(Long id) throws DAOException;

}
