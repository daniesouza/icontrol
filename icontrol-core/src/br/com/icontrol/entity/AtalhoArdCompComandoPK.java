package br.com.icontrol.entity;

import java.io.Serializable;

/**
 * @author Icontrol
 * 
 */

public class AtalhoArdCompComandoPK implements Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = 179660932390754491L;

	private Long idAtalho;
	
	private Long idArduino;

	private Long idComponente;
	
	private Long idComando;
	

	@Override
	public String toString() {
		return "AtalhoArdCompComandoPK [idAtalho=" + idAtalho + ", idArduino="
				+ idArduino + ", idComponente=" + idComponente + ", idComando="
				+ idComando + "]";
	}


	public Long getIdAtalho() {
		return idAtalho;
	}
	public void setIdAtalho(Long idAtalho) {
		this.idAtalho = idAtalho;
	}

	public Long getIdArduino() {
		return idArduino;
	}

	public void setIdArduino(Long idArduino) {
		this.idArduino = idArduino;
	}

	public Long getIdComponente() {
		return idComponente;
	}

	public void setIdComponente(Long idComponente) {
		this.idComponente = idComponente;
	}

	public Long getIdComando() {
		return idComando;
	}

	public void setIdComando(Long idComando) {
		this.idComando = idComando;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idArduino == null) ? 0 : idArduino.hashCode());
		result = prime * result
				+ ((idComando == null) ? 0 : idComando.hashCode());
		result = prime * result
				+ ((idComponente == null) ? 0 : idComponente.hashCode());
		result = prime * result + ((idAtalho == null) ? 0 : idAtalho.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AtalhoArdCompComandoPK other = (AtalhoArdCompComandoPK) obj;
		if (idArduino == null) {
			if (other.idArduino != null)
				return false;
		} else if (!idArduino.equals(other.idArduino))
			return false;
		if (idComando == null) {
			if (other.idComando != null)
				return false;
		} else if (!idComando.equals(other.idComando))
			return false;
		if (idComponente == null) {
			if (other.idComponente != null)
				return false;
		} else if (!idComponente.equals(other.idComponente))
			return false;
		if (idAtalho == null) {
			if (other.idAtalho != null)
				return false;
		} else if (!idAtalho.equals(other.idAtalho))
			return false;
		return true;
	}	




}
