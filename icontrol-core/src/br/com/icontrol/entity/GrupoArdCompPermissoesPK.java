package br.com.icontrol.entity;

import java.io.Serializable;

/**
 * @author Icontrol
 * 
 */

public class GrupoArdCompPermissoesPK implements Serializable {

	private static final long serialVersionUID = -8665498239810290285L;


	private Long idGrupo;
	
	private Long idArduino;

	private Long idComponente;
	
	private Long idComando;
	

	@Override
	public String toString() {
		return "GrupoArdCompPermissoesPK [idGrupo=" + idGrupo + ", idArduino="
				+ idArduino + ", idComponente=" + idComponente + ", idComando="
				+ idComando + "]";
	}

	public Long getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
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
		result = prime * result + ((idGrupo == null) ? 0 : idGrupo.hashCode());
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
		GrupoArdCompPermissoesPK other = (GrupoArdCompPermissoesPK) obj;
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
		if (idGrupo == null) {
			if (other.idGrupo != null)
				return false;
		} else if (!idGrupo.equals(other.idGrupo))
			return false;
		return true;
	}	




}
