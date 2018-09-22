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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Icontrol
 * 
 */

@Entity
@Table(name="agendamento")
public class Agendamento implements Entidade {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3454573589224353418L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_AGENDAMENTO")	
	private Long idAgendamento;
	
	@Column(name="NOME", nullable=false, unique=true)
	private String nome;
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="horario")
	private Date horario;
	
	@Column(name="ATIVO", nullable=false)
	private boolean ativo;
	
	@Column(name="segunda", nullable=false)
	private boolean segunda;
	
	@Column(name="terca", nullable=false)
	private boolean terca;
	
	@Column(name="quarta", nullable=false)
	private boolean quarta;
	
	@Column(name="quinta", nullable=false)
	private boolean quinta;
	
	@Column(name="sexta", nullable=false)
	private boolean sexta;
	
	@Column(name="sabado", nullable=false)
	private boolean sabado;
	
	@Column(name="domingo", nullable=false)
	private boolean domingo;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_usuario", referencedColumnName="id_usuario")
	private Usuario usuario;
		
	@ManyToMany//(cascade=CascadeType.ALL, mappedBy="atalho",fetch=FetchType.LAZY)
	@JoinTable(name="ATALHO_AGENDAMENTO",
	joinColumns=@JoinColumn(name="ID_AGENDAMENTO", referencedColumnName="ID_AGENDAMENTO"),
	inverseJoinColumns=@JoinColumn(name="ID_ATALHO", referencedColumnName="ID_ATALHO"))
	private List<Atalho> atalhos = new ArrayList<Atalho>();
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_cliente", referencedColumnName="id_cliente")
	private Cliente cliente;
	

	@Override
	public Long getId() {
		return this.getIdAgendamento();
	}

	@Override
	public void setId(Long id) {
		this.setIdAgendamento(id);		
	}

	public Long getIdAgendamento() {
		return idAgendamento;
	}

	public void setIdAgendamento(Long idAgendamento) {
		this.idAgendamento = idAgendamento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Atalho> getAtalhos() {
		return atalhos;
	}

	public void setAtalhos(List<Atalho> atalhos) {
		this.atalhos = atalhos;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public boolean isSegunda() {
		return segunda;
	}

	public void setSegunda(boolean segunda) {
		this.segunda = segunda;
	}

	public boolean isTerca() {
		return terca;
	}

	public void setTerca(boolean terca) {
		this.terca = terca;
	}

	public boolean isQuarta() {
		return quarta;
	}

	public void setQuarta(boolean quarta) {
		this.quarta = quarta;
	}

	public boolean isQuinta() {
		return quinta;
	}

	public void setQuinta(boolean quinta) {
		this.quinta = quinta;
	}

	public boolean isSexta() {
		return sexta;
	}

	public void setSexta(boolean sexta) {
		this.sexta = sexta;
	}

	public boolean isSabado() {
		return sabado;
	}

	public void setSabado(boolean sabado) {
		this.sabado = sabado;
	}

	public boolean isDomingo() {
		return domingo;
	}

	public void setDomingo(boolean domingo) {
		this.domingo = domingo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getHorario() {
		return horario;
	}

	public void setHorario(Date horario) {
		this.horario = horario;
	}

	

	
}
