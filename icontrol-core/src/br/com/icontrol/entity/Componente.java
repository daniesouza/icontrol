package br.com.icontrol.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.icontrol.util.Constantes;

/**
 * @author Icontrol
 * 
 */

@Entity
@Table(name="componente")
public class Componente implements Entidade ,Comparable<Componente>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5078072252212279655L;


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)  
	@Column(name="id_componente")
    private Long idComponente;
	
    
	@Column(name="codigo",nullable=false,unique=true)
	private String codigo;
	
	@Column(name="nome",nullable=false)
	private String nome;
	
	@Column(name="marca")
	private String marca;
	
	@Column(name="porta_arduino",nullable=false)
	private Integer portaArduino;
	
	@Column(name="consumo")
	private Double consumo;
	
	@ManyToMany(mappedBy="componentes")
    private List<Arduino> arduinos;
	
	@Transient
	private transient String statusPorta;

	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="COMPONENTE_COMANDO",
	joinColumns=@JoinColumn(name="id_componente", referencedColumnName="id_componente"),
	inverseJoinColumns=@JoinColumn(name="id_comando", referencedColumnName="id_comando"))
    private List<Comando> comandos;

	
	@Override
	public Long getId() {

		return this.getIdComponente();
	}

	@Override
	public void setId(Long id) {

		this.setIdComponente(id);
	}
	
	@Override
	public int compareTo(Componente o) {
		return this.getNome().compareTo(o.getNome());
	}

	public Long getIdComponente() {
		return idComponente;
	}

	public void setIdComponente(Long idComponente) {
		this.idComponente = idComponente;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result
				+ ((idComponente == null) ? 0 : idComponente.hashCode());
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
		Componente other = (Componente) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (idComponente == null) {
			if (other.idComponente != null)
				return false;
		} else if (!idComponente.equals(other.idComponente))
			return false;
		return true;
	}

	public List<Arduino> getArduinos() {
		return arduinos;
	}

	public void setArduinos(List<Arduino> arduinos) {
		this.arduinos = arduinos;
	}

	public List<Comando> getComandos() {
		return comandos;
	}

	public void setComandos(List<Comando> comandos) {
		this.comandos = comandos;
	}

	public Integer getPortaArduino() {
		return portaArduino;
	}

	public void setPortaArduino(Integer portaArduino) {
		this.portaArduino = portaArduino;
	}
	

	public Double getConsumo() {
		return consumo;
	}

	public void setConsumo(Double consumo) {
		this.consumo = consumo;
	}

	public  int getA0() {
		return Constantes.A0;
	}
	public  int getA1() {
		return Constantes.A1;
	}
	public  int getA2() {
		return Constantes.A2;
	}
	public  int getA3() {
		return Constantes.A3;
	}
	public  int getA4() {
		return Constantes.A4;
	}
	public  int getA5() {
		return Constantes.A5;
	}

	public String getStatusPorta() {
		return statusPorta;
	}

	public void setStatusPorta(String statusPorta) {
		this.statusPorta = statusPorta;
	}

	



	
	
}
