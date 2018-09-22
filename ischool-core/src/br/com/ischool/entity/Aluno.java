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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * 
 */

@Entity
@Table(name="aluno")
public class Aluno implements Entidade{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4177648884923803942L;

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)  
	@Column(name="id_aluno")
    private Long idAluno;
	
	@Column(name="cod_aluno",nullable=false,unique=true)
	private String codigoAluno;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_cliente", referencedColumnName="id_cliente")
	private Cliente cliente;
	
	@ManyToMany(mappedBy="alunos")
    private List<Classe> classes;
	
	@ManyToMany(mappedBy="alunos")
    private List<Usuario> usuarios;
	
	@OneToMany(mappedBy="aluno")
	private List<EventoExecutado> eventosExecutados;
	
	@Column(name="nome",nullable=false)
	private String nome;
	
	@Column(name="rg",length=14)
	private String rg;
		
	@Column(name="endereco")
	private String endereco;
	
	@Column(name="telefone")
	private String telefone;
	
	@Column(name="observacoes")
	private String observacoes;
	
	@Column(name="nota")
	private Double nota;
	
	@Column(name="faltas")
	private Integer faltas;
	
	@Column(name="data_cadastro")
	private Date dataCadastro;
	
	@Column(name="data_nascimento")
	private Date dataNascimento;
	
	@Column(name="EMAIL")
	private String email;
	
	@Transient
	private transient List<EventoExecutado> listaEventoExecutadoManha;
	@Transient
	private transient List<EventoExecutado> listaEventoExecutadoTarde;
	@Transient
	private transient List<EventoExecutado> listaEventoExecutadoOutros;
	
	@Override
	public Long getId() {
		return this.getIdAluno();
	}

	@Override
	public void setId(Long id) {
		this.setIdAluno(id);		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codigoAluno == null) ? 0 : codigoAluno.hashCode());
		result = prime * result + ((idAluno == null) ? 0 : idAluno.hashCode());
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
		Aluno other = (Aluno) obj;
		if (codigoAluno == null) {
			if (other.codigoAluno != null)
				return false;
		} else if (!codigoAluno.equals(other.codigoAluno))
			return false;
		if (idAluno == null) {
			if (other.idAluno != null)
				return false;
		} else if (!idAluno.equals(other.idAluno))
			return false;
		return true;
	}

	public Long getIdAluno() {
		return idAluno;
	}
	public void setIdAluno(Long idAluno) {
		this.idAluno = idAluno;
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

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCodigoAluno() {
		return codigoAluno;
	}

	public void setCodigoAluno(String codigoAluno) {
		this.codigoAluno = codigoAluno;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Double getNota() {
		return nota;
	}

	public void setNota(Double nota) {
		this.nota = nota;
	}

	public Integer getFaltas() {
		return faltas;
	}

	public void setFaltas(Integer faltas) {
		this.faltas = faltas;
	}

	public List<Classe> getClasses() {
		return classes;
	}

	public void setClasses(List<Classe> classes) {
		this.classes = classes;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<EventoExecutado> getEventosExecutados() {
		return eventosExecutados;
	}

	public void setEventosExecutados(List<EventoExecutado> eventosExecutados) {
		this.eventosExecutados = eventosExecutados;
	}

	public List<EventoExecutado> getListaEventoExecutadoManha() {
		return listaEventoExecutadoManha;
	}

	public void setListaEventoExecutadoManha(
			List<EventoExecutado> listaEventoExecutadoManha) {
		this.listaEventoExecutadoManha = listaEventoExecutadoManha;
	}

	public List<EventoExecutado> getListaEventoExecutadoTarde() {
		return listaEventoExecutadoTarde;
	}

	public void setListaEventoExecutadoTarde(
			List<EventoExecutado> listaEventoExecutadoTarde) {
		this.listaEventoExecutadoTarde = listaEventoExecutadoTarde;
	}

	public List<EventoExecutado> getListaEventoExecutadoOutros() {
		return listaEventoExecutadoOutros;
	}

	public void setListaEventoExecutadoOutros(
			List<EventoExecutado> listaEventoExecutadoOutros) {
		this.listaEventoExecutadoOutros = listaEventoExecutadoOutros;
	}

	
}
