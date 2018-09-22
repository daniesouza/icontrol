package br.com.ischool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * 
 */

@Entity
@Table(name="camera")
public class Camera implements Entidade {



	/**
	 * 
	 */
	private static final long serialVersionUID = 3806069521559032985L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_CAMERA")
	private Long idCamera;
	
	@Column(name="CODIGO_CAMERA", nullable=false,unique=true)
	private String codigo;
	
	@Column(name="NOME_CAMERA", nullable=false)
	private String nome;
	
	@Column(name="ip", nullable=false)
	private String ip;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_classe", referencedColumnName="id_classe")
	private Classe classe;

	@Override
	public Long getId() {
		
		return this.getIdCamera();
	}

	@Override
	public void setId(Long id) {
		
		this.setIdCamera(id);
		
	}

	public Long getIdCamera() {
		return idCamera;
	}

	public void setIdCamera(Long idCamera) {
		this.idCamera = idCamera;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	
	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result
				+ ((idCamera == null) ? 0 : idCamera.hashCode());
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
		Camera other = (Camera) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (idCamera == null) {
			if (other.idCamera != null)
				return false;
		} else if (!idCamera.equals(other.idCamera))
			return false;
		return true;
	}
	
	

}
