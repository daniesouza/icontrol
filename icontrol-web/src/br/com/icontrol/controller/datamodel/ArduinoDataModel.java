package br.com.icontrol.controller.datamodel;

import java.util.List;

import br.com.icontrol.business.ArduinoServiceLocal;
import br.com.icontrol.entity.Arduino;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.Paginacao;

public class ArduinoDataModel extends GenericDataModel<Arduino> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3629996708909210706L;
	
	private ArduinoServiceLocal arduinoLocal;
	
	@Override
	public List<Arduino> obterResultados(Arduino filtro, Paginacao paginacao) throws ServicoException {

		List<Arduino> listaArduino = (List<Arduino>) arduinoLocal.listarArduinos(filtro, paginacao);
			
		return listaArduino;
	}

	public ArduinoServiceLocal getArduinoLocal() {
		return arduinoLocal;
	}

	public void setArduinoLocal(ArduinoServiceLocal arduinoLocal) {
		this.arduinoLocal = arduinoLocal;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
