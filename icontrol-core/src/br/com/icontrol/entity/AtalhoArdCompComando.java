package br.com.icontrol.entity;

import java.io.Serializable;  

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Icontrol
 * 
 */

@Entity
@Table(name="ATALHO_ARD_COMP_COMANDO")
@IdClass(AtalhoArdCompComandoPK.class)
public class AtalhoArdCompComando implements Serializable {

	private static final long serialVersionUID = -7751624435061271565L;

	@Id
	@Column(name="ID_ATALHO")
	private Long idAtalho;
	
	@Id
	@Column(name="ID_ARDUINO")
	private Long idArduino;

	@Id
	@Column(name="ID_COMPONENTE")
	private Long idComponente;
	
	@Id
	@Column(name="ID_COMANDO")
	private Long idComando;	

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ARDUINO", referencedColumnName="ID_ARDUINO", insertable=false, updatable=false)
	private Arduino arduino;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ATALHO", referencedColumnName="ID_ATALHO", insertable=false, updatable=false)	
	private Atalho atalho;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_COMPONENTE", referencedColumnName="ID_COMPONENTE", insertable=false, updatable=false)	
	private Componente componente;	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_COMANDO", referencedColumnName="ID_COMANDO", insertable=false, updatable=false)	
	private Comando comando;

	
	@Override
	public String toString() {
		return "AtalhoArdCompComando [idAtalho=" + idAtalho + ", idArduino="
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


	public Arduino getArduino() {
		return arduino;
	}


	public void setArduino(Arduino arduino) {
		this.arduino = arduino;
	}


	public Atalho getAtalho() {
		return atalho;
	}


	public void setAtalho(Atalho atalho) {
		this.atalho = atalho;
	}


	public Componente getComponente() {
		return componente;
	}


	public void setComponente(Componente componente) {
		this.componente = componente;
	}


	public Comando getComando() {
		return comando;
	}


	public void setComando(Comando comando) {
		this.comando = comando;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arduino == null) ? 0 : arduino.hashCode());
		result = prime * result + ((atalho == null) ? 0 : atalho.hashCode());
		result = prime * result + ((comando == null) ? 0 : comando.hashCode());
		result = prime * result
				+ ((componente == null) ? 0 : componente.hashCode());
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
		AtalhoArdCompComando other = (AtalhoArdCompComando) obj;
		if (arduino == null) {
			if (other.arduino != null)
				return false;
		} else if (!arduino.equals(other.arduino))
			return false;
		if (atalho == null) {
			if (other.atalho != null)
				return false;
		} else if (!atalho.equals(other.atalho))
			return false;
		if (comando == null) {
			if (other.comando != null)
				return false;
		} else if (!comando.equals(other.comando))
			return false;
		if (componente == null) {
			if (other.componente != null)
				return false;
		} else if (!componente.equals(other.componente))
			return false;
		return true;
	}







}
