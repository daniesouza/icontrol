package br.com.ischool.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.ischool.util.Constantes;

@Entity
@Table(name="evento_executado")
public class EventoExecutado implements Entidade{



	/**
	 * 
	 */
	private static final long serialVersionUID = -8184498228538335153L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)  
	@Column(name="id_evento_executado")	
    private Long idEventoExecutado;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_evento", referencedColumnName="id_evento")
	private Evento evento;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_cliente", referencedColumnName="id_cliente")
	private Cliente cliente;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_aluno", referencedColumnName="id_aluno")
	private Aluno aluno;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_usuario", referencedColumnName="id_usuario")
	private Usuario usuario;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_classe", referencedColumnName="id_classe")
	private Classe classe;
	
	@Column(name="observacoes")
	private String observacoes;
	
	@Column(name="quantidade")
	private Long quantidade;
	
	@Column(name="data_execucao")
	private Date dataExecucao;
	
	@Column(name="tipo")
	private Integer tipo;
	

	@Override
	public Long getId() {
		return this.getIdEventoExecutado();
	}
	@Override
	public void setId(Long id) {
		this.setIdEventoExecutado(id);
		
	}
	public Long getIdEventoExecutado() {
		return idEventoExecutado;
	}
	public void setIdEventoExecutado(Long idEventoExecutado) {
		this.idEventoExecutado = idEventoExecutado;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Evento getEvento() {
		return evento;
	}
	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Aluno getAluno() {
		return aluno;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Classe getClasse() {
		return classe;
	}
	public void setClasse(Classe classe) {
		this.classe = classe;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	public Long getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}
	public Date getDataExecucao() {
		return dataExecucao;
	}
	public void setDataExecucao(Date dataExecucao) {
		this.dataExecucao = dataExecucao;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((idEventoExecutado == null) ? 0 : idEventoExecutado
						.hashCode());
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
		EventoExecutado other = (EventoExecutado) obj;
		if (idEventoExecutado == null) {
			if (other.idEventoExecutado != null)
				return false;
		} else if (!idEventoExecutado.equals(other.idEventoExecutado))
			return false;
		return true;
	}
	
	public  int getTipoManha() {
		return Constantes.TIPO_MANHA;
	}

	public  int getTipoTarde() {
		return Constantes.TIPO_TARDE;
	}
	public  int getTipoOutros() {
		return Constantes.TIPO_OUTROS;
	}
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	
}
