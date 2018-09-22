package br.com.icontrol.entity;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Icontrol
 * 
 */

@Entity
@Table(name="grupo")
public class Grupo implements Entidade {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -339641975590067006L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_GRUPO")	
	private Long idGrupo;
	
	@Column(name="NOME", nullable=false, unique=true)
	private String nome;
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="ATIVO", nullable=false)
	private boolean ativo;

	@Column(name="ADMINISTRADOR", nullable=false)
	private boolean administrador;
	
	@ManyToMany
	@JoinTable(name="GRUPO_USUARIO",
	joinColumns=@JoinColumn(name="ID_GRUPO", referencedColumnName="ID_GRUPO"),
	inverseJoinColumns=@JoinColumn(name="id_usuario", referencedColumnName="id_usuario"))
	private List<Usuario> usuarios = new ArrayList<Usuario>();
	
	@ManyToMany
	@JoinTable(name="GRUPO_ARDUINO",
	joinColumns=@JoinColumn(name="ID_GRUPO", referencedColumnName="ID_GRUPO"),
	inverseJoinColumns=@JoinColumn(name="ID_ARDUINO", referencedColumnName="ID_ARDUINO"))
	private List<Arduino> arduinos = new ArrayList<Arduino>();
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="grupo",fetch=FetchType.LAZY)
	private List<GrupoArdCompPermissoes> gruposArdCompPermissoes = new ArrayList<GrupoArdCompPermissoes>();
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_cliente", referencedColumnName="id_cliente")
	private Cliente cliente;
	

	public Long getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Grupo other = (Grupo) obj;
		if (idGrupo == null) {
			if (other.idGrupo != null)
				return false;
		} else if (!idGrupo.equals(other.idGrupo))
			return false;
		return true;
	}

	
	@Override
	public void setId(Long id) {
		this.idGrupo = id;
	}

	@Override
	public Long getId() {
		return idGrupo;
	}



	public void addAllArduinos(List<Arduino> arduinos) {
		for (Arduino arduino : arduinos) {
			addArduino(arduino);
		}
	}
	
	public void addArduino(Arduino arduino) {
		if(arduino == null) {
			return;
		}
		if(!this.arduinos.contains(arduino)) {
			this.arduinos.add(arduino);			
		}
		if (arduino.getGrupos() == null) {
			arduino.setGrupos(new ArrayList<Grupo>());
		}	
		if (!arduino.getGrupos().contains(this)) {
			arduino.getGrupos().add(this);			
		}
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Arduino> getArduinos() {
		return arduinos;
	}

	public void setArduinos(List<Arduino> arduinos) {
		this.arduinos = arduinos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public boolean isAdministrador() {
		return administrador;
	}

	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}

	public List<GrupoArdCompPermissoes> getGruposArdCompPermissoes() {
		return gruposArdCompPermissoes;
	}

	public void setGruposArdCompPermissoes(
			List<GrupoArdCompPermissoes> gruposArdCompPermissoes) {
		this.gruposArdCompPermissoes = gruposArdCompPermissoes;
	}

	
	public boolean isAtivo() {
		return ativo;
	}


	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}


	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	
}
