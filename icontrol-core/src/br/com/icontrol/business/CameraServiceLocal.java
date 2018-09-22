package br.com.icontrol.business;

import java.util.Collection;

import br.com.icontrol.entity.Camera;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
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
