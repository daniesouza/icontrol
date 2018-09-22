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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Icontrol
 * 
 */

@Entity
@Table(name="atalho")
public class Atalho implements Entidade {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5460653330521680584L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_ATALHO")	
	private Long idAtalho;
	
	@Column(name="NOME", nullable=false, unique=true)
	private String nome;
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="ATIVO", nullable=false)
	private boolean ativo;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_usuario", referencedColumnName="id_usuario")
	private Usuario usuario;
		
	@OneToMany(cascade=CascadeType.ALL, mappedBy="atalho",fetch=FetchType.LAZY)
	private List<AtalhoArdCompComando> atalhosArdCompComando = new ArrayList<AtalhoArdCompComando>();
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_cliente", referencedColumnName="id_cliente")
	private Cliente cliente;
	
	@Column(name="icone",nullable=false)
	private String icone;
	
	@ManyToMany(cascade=CascadeType.ALL, mappedBy="atalhos",fetch=FetchType.LAZY)
	private List<Agendamento> agendamentos = new ArrayList<Agendamento>();
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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

	@Override
	public Long getId() {
		return this.getIdAtalho();
	}

	@Override
	public void setId(Long id) {
		this.setIdAtalho(id);
	}

	public Long getIdAtalho() {
		return idAtalho;
	}

	public void setIdAtalho(Long idAtalho) {
		this.idAtalho = idAtalho;
	}

	public List<AtalhoArdCompComando> getAtalhosArdCompComando() {
		return atalhosArdCompComando;
	}

	public void setAtalhosArdCompComando(
			List<AtalhoArdCompComando> atalhosArdCompComando) {
		this.atalhosArdCompComando = atalhosArdCompComando;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idAtalho == null) ? 0 : idAtalho.hashCode());
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
		Atalho other = (Atalho) obj;
		if (idAtalho == null) {
			if (other.idAtalho != null)
				return false;
		} else if (!idAtalho.equals(other.idAtalho))
			return false;
		return true;
	}

	public String getIcone() {
		return icone;
	}

	public void setIcone(String icone) {
		this.icone = icone;
	}

	public List<Agendamento> getAgendamentos() {
		return agendamentos;
	}

	public void setAgendamentos(List<Agendamento> agendamentos) {
		this.agendamentos = agendamentos;
	}


	
	
	
	
}
