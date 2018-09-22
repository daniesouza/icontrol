package br.com.ischool.business;

import java.util.Collection;

import br.com.ischool.entity.Camera;
import br.com.ischool.exceptions.ServicoException;
import br.com.ischool.util.Paginacao;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * 
 */
public interface CameraServiceLocal {

	public void salvarCamera(Camera camera) throws ServicoException;
	
	public void alterarCamera(Camera camera) throws ServicoException;
	
	public Collection<Camera> listarCameras() throws ServicoException;
	
	public Collection<Camera> listarCameras(Camera filtro,Paginacao paginacao) throws ServicoException;
	
	public Collection<Camera> listarCamerasSemArduino() throws ServicoException;
	
	public void excluirCamera(Camera camera) throws ServicoException;
}
