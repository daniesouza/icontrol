package br.com.icontrol.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Icontrol
 * 
 */

@Entity
@Table(name="cliente")
public class Cliente implements Entidade{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1654440848232809511L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)  
	@Column(name="id_cliente")
    private Long idCliente;
	
	@Column(name="cpf_cnpj",nullable=false,unique=true,length=14)
	private Long cpfCnpj;
    
	@Column(name="razao_social",nullable=false)
	private String razaoSocial;
	
	@Column(name="nome",nullable=false)
	private String nome;
	
	@Column(name="endereco",nullable=false)
	private String endereco;
	
	@Column(name="cep",nullable=false)
	private Long cep;
	
	@Column(name="bairro",nullable=false)
	private String bairro;
	
	@Column(name="cidade",nullable=false)
	private String cidade;
	
	@Column(name="estado",nullable=false)
	private Integer estado;
	
	@Column(name="tipo",nullable=false,length=1)
	private Integer tipo;
	
	@Column(name="telefone")
	private String telefone;

	@Column(name="dtcad")
	private Date dtCad;
	
	@Column(name="email")
	private String email;
	
	@Column(name="ativo", nullable=false)
	private Boolean ativo;
	
	@OneToMany(mappedBy="cliente")
	private List<Arduino> arduinos;
	
	@OneToMany(mappedBy="cliente")
	private List<Usuario> usuarios;
	
	@OneToMany(mappedBy="cliente")
	private List<Grupo> grupos;
	
	@OneToMany(mappedBy="cliente")
	private List<Atalho> atalhos;
	
	@OneToMany(mappedBy="cliente")
	private List<Agendamento> agendamentos;

	@Override
	public Long getId() {
		
		return this.getIdCliente();
	}

	@Override
	public void setId(Long id) {
		
		this.setIdCliente(id);	
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpfCnpj == null) ? 0 : cpfCnpj.hashCode());
		result = prime * result
				+ ((idCliente == null) ? 0 : idCliente.hashCode());
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
		Cliente other = (Cliente) obj;
		if (cpfCnpj == null) {
			if (other.cpfCnpj != null)
				return false;
		} else if (!cpfCnpj.equals(other.cpfCnpj))
			return false;
		if (idCliente == null) {
			if (other.idCliente != null)
				return false;
		} else if (!idCliente.equals(other.idCliente))
			return false;
		return true;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(Long cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Date getDtCad() {
		return dtCad;
	}

	public void setDtCad(Date dtCad) {
		this.dtCad = dtCad;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Arduino> getArduinos() {
		return arduinos;
	}

	public void setArduinos(List<Arduino> arduinos) {
		this.arduinos = arduinos;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	public Long getCep() {
		return cep;
	}

	public void setCep(Long cep) {
		this.cep = cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public List<Atalho> getAtalhos() {
		return atalhos;
	}

	public void setAtalhos(List<Atalho> atalhos) {
		this.atalhos = atalhos;
	}

	public List<Agendamento> getAgendamentos() {
		return agendamentos;
	}

	public void setAgendamentos(List<Agendamento> agendamentos) {
		this.agendamentos = agendamentos;
	}
	
	

}
