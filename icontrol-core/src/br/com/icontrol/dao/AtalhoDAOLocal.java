package br.com.icontrol.dao;

import java.util.Collection;
import java.util.List;
import br.com.icontrol.entity.Atalho;
import br.com.icontrol.entity.AtalhoArdCompComando;
import br.com.icontrol.exceptions.DAOException;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
 * 
 */
public interface AtalhoDAOLocal {

	public void salvarAtalho(Atalho atalho) throws DAOException;
	
	public void alterarAtalho(Atalho atalho) throws DAOException;
	
	public Collection<Atalho> listarAtalhos() throws DAOException;
	
	public Collection<Atalho> listarAtalhos(Atalho filtro,Paginacao paginacao) throws DAOException;
		
	public void excluirAtalho(Atalho atalho) throws DAOException;

	public Atalho selectById(Long id) throws DAOException;
	
	public int consultarQtde(Atalho atalho) throws DAOException;

	public List<AtalhoArdCompComando> obterAtalhoArdCompComando(long idAtalho) throws DAOException;
}
