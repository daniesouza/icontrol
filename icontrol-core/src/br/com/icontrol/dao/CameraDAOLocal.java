package br.com.icontrol.dao;

import java.util.Collection;

import br.com.icontrol.entity.Camera;
import br.com.icontrol.exceptions.DAOException;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
 * 
 */
public interface CameraDAOLocal {

	public void salvarCamera(Camera camera) throws DAOException;
	
	public void alterarCamera(Camera camera) throws DAOException;
	
	public Collection<Camera> listarCameras() throws DAOException;
	
	public Collection<Camera> listarCamerasSemArduino() throws DAOException;
	
	public Collection<Camera> listarCameras(Camera filtro,Paginacao paginacao) throws DAOException;
	
	public void excluirCamera(Camera camera) throws DAOException;
	
	public Camera selectById(Long id) throws DAOException;
	
	public int consultarQtde(Camera camera) throws DAOException;
}