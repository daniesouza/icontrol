package br.com.icontrol.entity;

import java.util.ArrayList;
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

import br.com.icontrol.util.Constantes;

/**
 * @author Icontrol
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
	
	@Column(name="nome",nullable=false)
	private String nome;
	
	@Column(name="senha",nullable=false)
	private String senha;
	
	@Column(name="endereco")
	private String endereco;
	
	@Column(name="cep")
	private Long cep;
	
	@Column(name="bairro")
	private String bairro;
	
	@Column(name="cidade")
	private String cidade;
	
	@Column(name="estado")
	private Integer estado;
	
	@Column(name="autoridade",nullable=false)
	private String autoridade;
	
	@Column(name="telefone")
	private String telefone;

	@Column(name="dtcad")
	private Date dtCad;
	
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
	
	@Column(name="TEMA")
	private String tema;
	
	
	@ManyToMany(mappedBy="usuarios")
	private List<Grupo> grupos = new ArrayList<Grupo>();
	
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_cliente", referencedColumnName="id_cliente")
	private Cliente cliente;
	
	@OneToMany(mappedBy="usuario")
	private List<Usuario> usuarios;
	
	@OneToMany(mappedBy="usuario")
	private List<Atalho> atalhos;

	@OneToMany(mappedBy="usuario")
	private List<Agendamento> agendamentos;
	

	public void removeAllGrupos(List<Grupo> grupos) {
		getGrupos().removeAll(grupos);
	}
	
	public void addAllGrupos(List<Grupo> grupos) {
		List<Grupo> gruposUsuario = getGrupos();
		for (Grupo grupo : grupos) {
			if (!gruposUsuario.contains(grupo)) {
				gruposUsuario.add(grupo);
			}
		}
	}	


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

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	public  String getAdministrador() {
		return Constantes.ADMINISTRADOR;
	}

	public  String getAdminCliente() {
		return Constantes.ADMIN_CLIENTE;
	}

	public  String getClient() {
		return Constantes.CLIENT;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
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

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
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
