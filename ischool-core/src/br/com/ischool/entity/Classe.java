package br.com.ischool.entity;

import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * 
 */

@Entity
@Table(name="classe")
public class Classe implements Entidade{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6544992143608965070L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)  
	@Column(name="id_classe")
    private Long idClasse;
	
	@Column(name="codigo_classe",nullable=false,unique=true)
	private String codigoClasse;	

	@Column(name="nome_classe",nullable=false)
	private String nome;
	
	@Column(name="turma",nullable=false)
	private String turma;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_cliente", referencedColumnName="id_cliente")
	private Cliente cliente;
	
	@OneToMany(mappedBy="classe")
	private List<Camera> cameras;
	
	@OneToMany(mappedBy="classe")
	private List<EventoExecutado> eventosExecutados;
	
	@Column(name="data_cadastro")
	private Date dataCadastro;
	
	@Column(name="ano")
	private Integer ano;

	@ManyToMany
	@JoinTable(name="CLASSE_ALUNO",joinColumns=@JoinColumn(name="ID_CLASSE", referencedColumnName="ID_CLASSE"),
	inverseJoinColumns=@JoinColumn(name="id_aluno", referencedColumnName="id_aluno"))
	private List<Aluno> alunos;
	
	@ManyToMany
	@JoinTable(name="CLASSE_USUARIO",
	joinColumns=@JoinColumn(name="ID_CLASSE", referencedColumnName="ID_CLASSE"),
	inverseJoinColumns=@JoinColumn(name="id_usuario", referencedColumnName="id_usuario"))
	private List<Usuario> usuarios;	
	

	@Override
	public Long getId() {
		return getIdClasse();
	}

	@Override
	public void setId(Long id) {
		this.setIdClasse(id);		
	}
	
	public Long getIdClasse() {
		return idClasse;
	}

	public void setIdClasse(Long idClasse) {
		this.idClasse = idClasse;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public List<Camera> getCameras() {
		return cameras;
	}

	public void setCameras(List<Camera> cameras) {
		this.cameras = cameras;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTurma() {
		return turma;
	}

	public void setTurma(String turma) {
		this.turma = turma;
	}
	public String getCodigoClasse() {
		return codigoClasse;
	}

	public void setCodigoClasse(String codigoClasse) {
		this.codigoClasse = codigoClasse;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codigoClasse == null) ? 0 : codigoClasse.hashCode());
		result = prime * result
				+ ((idClasse == null) ? 0 : idClasse.hashCode());
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
		Classe other = (Classe) obj;
		if (codigoClasse == null) {
			if (other.codigoClasse != null)
				return false;
		} else if (!codigoClasse.equals(other.codigoClasse))
			return false;
		if (idClasse == null) {
			if (other.idClasse != null)
				return false;
		} else if (!idClasse.equals(other.idClasse))
			return false;
		return true;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public List<EventoExecutado> getEventosExecutados() {
		return eventosExecutados;
	}

	public void setEventosExecutados(List<EventoExecutado> eventosExecutados) {
		this.eventosExecutados = eventosExecutados;
	}


	
	
}
