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

import br.com.ischool.util.Constantes;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * 
 */
 
@Entity
@Table(name="usuario")
public class Usuario implements Entidade{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1611809499291124578L;


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)  
	@Column(name="id_usuario")
    private Long idUsuario;
	
    
	@Column(name="usuario",nullable=false,unique=true)
	private String usuario;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="senha",nullable=false)
	private String senha;
	
	@Column(name="endereco")
	private String endereco;
	
	@Column(name="autoridade",nullable=false)
	private String autoridade;
	
	@Column(name="telefone")
	private String telefone;

	@Column(name="dtcad")
	private Date dtCad;
	
	@Column(name="rg",unique=true,length=14)
	private String rg;
	
	@Column(name="cpf",unique=true,length=14)
	private Long cpf;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="ATIVO", nullable=false)
	private Boolean ativo;
	
	@Column(name="DATA_ULTIMO_ACESSO")
	private Date dataUltimoAcesso;
	
	@Column(name="DATA_ACESSO_ATUAL")
	private Date dataAcessoAtual;	
		
	@Column(name="DATA_SENHA", nullable=false)
	private Date dataSenha;
	
	@Column(name="TROCAR_SENHA", nullable=false)
	private Boolean trocarSenha;
	
	@Column(name="TENTATIVAS", nullable=false)
	private Byte tentativasSenhaIncorreta;
		
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_cliente", referencedColumnName="id_cliente")
	private Cliente cliente;
	
	@ManyToMany(mappedBy="usuarios")
    private List<Classe> classes;
	
	@OneToMany(mappedBy="usuario")
	private List<EventoExecutado> eventosExecutados;
	
	@ManyToMany
	@JoinTable(name="RESPONSAVEL_ALUNO",
	joinColumns=@JoinColumn(name="ID_USUARIO", referencedColumnName="ID_USUARIO"),
	inverseJoinColumns=@JoinColumn(name="id_aluno", referencedColumnName="id_aluno"))
	private List<Aluno> alunos;
	
	@Override
	public Long getId() {
		return this.getIdUsuario();
	}

	@Override
	public void setId(Long id) {
		this.setIdUsuario(id);
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}


	public String getEndereco() {
		return endereco;
	}


	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}


	public String getAutoridade() {
		return autoridade;
	}


	public void setAutoridade(String autoridade) {
		this.autoridade = autoridade;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
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
		result = prime * result
				+ ((idUsuario == null) ? 0 : idUsuario.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		Usuario other = (Usuario) obj;
		if (idUsuario == null) {
			if (other.idUsuario != null)
				return false;
		} else if (!idUsuario.equals(other.idUsuario))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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

	public Date getDataUltimoAcesso() {
		return dataUltimoAcesso;
	}

	public void setDataUltimoAcesso(Date dataUltimoAcesso) {
		this.dataUltimoAcesso = dataUltimoAcesso;
	}

	public Date getDataAcessoAtual() {
		return dataAcessoAtual;
	}

	public void setDataAcessoAtual(Date dataAcessoAtual) {
		this.dataAcessoAtual = dataAcessoAtual;
	}

	public Date getDataSenha() {
		return dataSenha;
	}

	public void setDataSenha(Date dataSenha) {
		this.dataSenha = dataSenha;
	}

	public Boolean getTrocarSenha() {
		return trocarSenha;
	}

	public void setTrocarSenha(Boolean trocarSenha) {
		this.trocarSenha = trocarSenha;
	}

	public Byte getTentativasSenhaIncorreta() {
		return tentativasSenhaIncorreta;
	}

	public void setTentativasSenhaIncorreta(Byte tentativasSenhaIncorreta) {
		this.tentativasSenhaIncorreta = tentativasSenhaIncorreta;
	}

	public  String getAdministrador() {
		return Constantes.ADMINISTRADOR;
	}

	public  String getAdminCliente() {
		return Constantes.ADMIN_CLIENTE;
	}

	public  String getProfessor() {
		return Constantes.PROFESSOR;
	}
	
	public  String getPai() {
		return Constantes.PAI;
	}

	public List<Classe> getClasses() {
		return classes;
	}

	public void setClasses(List<Classe> classes) {
		this.classes = classes;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public List<EventoExecutado> getEventosExecutados() {
		return eventosExecutados;
	}

	public void setEventosExecutados(List<EventoExecutado> eventosExecutados) {
		this.eventosExecutados = eventosExecutados;
	}


}
