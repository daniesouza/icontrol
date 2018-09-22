package br.com.icontrol.business;

import java.util.Collection;
import java.util.List;

import br.com.icontrol.entity.Arduino;
import br.com.icontrol.entity.Camera;
import br.com.icontrol.entity.Cliente;
import br.com.icontrol.entity.Usuario;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
 * 
 */
public interface ArduinoServiceLocal {

	public void salvarArduino(Arduino arduino,List<Camera> listaRemoverCameras) throws ServicoException;
	
	public void alterarArduino(Arduino arduino,List<Camera> listaRemoverCameras) throws ServicoException;
	
	public Collection<Arduino> listarArduinos() throws ServicoException;
	
	public Collection<Arduino> listarArduinosSemCliente() throws ServicoException;
	
	public Collection<Arduino> listarArduinos(Arduino filtro,Paginacao paginacao) throws ServicoException;
		
	public void excluirArduino(Arduino arduino) throws ServicoException;
	
	public Arduino selectById(Long id) throws ServicoException;
	
	public Collection<Arduino> obterArduinosPermissoesPorUsuario(Usuario usuario) throws ServicoException;
	
	public Collection<Arduino> obterArduinosPermissaoAdmin(Cliente cliente) throws ServicoException;

}
