package br.com.icontrol.dao;

import java.util.Collection;

import br.com.icontrol.entity.Arduino;
import br.com.icontrol.exceptions.DAOException;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
 * 
 */
public interface ArduinoDAOLocal {

	public void salvarArduino(Arduino arduino) throws DAOException;
	
	public void alterarArduino(Arduino arduino) throws DAOException;
	
	public Collection<Arduino> listarArduinos() throws DAOException;
	
	public Collection<Arduino> listarArduinosSemCliente() throws DAOException;
	
	public Collection<Arduino> listarArduinos(Arduino filtro,Paginacao paginacao) throws DAOException;
	
	public int consultarQtde(Arduino filtro) throws DAOException;
	
	public void excluirArduino(Arduino arduino) throws DAOException;

	public Arduino selectById(Long id) throws DAOException;

}
