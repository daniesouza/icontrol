package br.com.ischool.dao;

import java.util.Collection;

import br.com.ischool.entity.Camera;
import br.com.ischool.exceptions.DAOException;
import br.com.ischool.util.Paginacao;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
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