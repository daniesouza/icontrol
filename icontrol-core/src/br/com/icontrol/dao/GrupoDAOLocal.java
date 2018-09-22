package br.com.icontrol.dao;

import java.util.Collection;
import java.util.List;

import br.com.icontrol.entity.Grupo;
import br.com.icontrol.entity.GrupoArdCompPermissoes;
import br.com.icontrol.exceptions.DAOException;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
 * 
 */
public interface GrupoDAOLocal {

	public void salvarGrupo(Grupo grupo) throws DAOException;
	
	public void alterarGrupo(Grupo grupo) throws DAOException;
	
	public Collection<Grupo> listarGrupos() throws DAOException;
	
	public Collection<Grupo> listarGrupos(Grupo filtro,Paginacao paginacao) throws DAOException;
		
	public void excluirGrupo(Grupo grupo) throws DAOException;

	public Grupo selectById(Long id) throws DAOException;
	
	public int consultarQtde(Grupo grupo) throws DAOException;

	public List<GrupoArdCompPermissoes> obterGrupoArdCompPermissoes(long idGrupo) throws DAOException;
}
