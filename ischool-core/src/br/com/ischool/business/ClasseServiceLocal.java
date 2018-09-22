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
public interface ClasseServiceLocal {

	public void salvarClasse(Classe classe) throws ServicoException;
	
	public void alterarClasse(Classe classe,List<Usuario> listaRemoverUsuarios,List<Aluno> listaRemoverAlunos) throws ServicoException;
	
	public Collection<Classe> listarClasses() throws ServicoException;
	
	public Collection<Classe> listarClasses(Classe filtro,Paginacao paginacao) throws ServicoException;
	
	public void excluirClasse(Classe classe) throws ServicoException;

	public Classe selectById(Long id) throws ServicoException;

}
