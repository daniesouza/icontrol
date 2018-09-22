package br.com.icontrol.entity;

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

import br.com.icontrol.util.Constantes;

@Entity
@Table(name="log")
public class Log implements Entidade{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6826545646268066321L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)  
	@Column(name="id_log")
    private Long idLog;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_usuario", referencedColumnName="id_usuario",nullable=false)
	private Usuario usuario;
	
	@Column(name="descricao",nullable=false)
	private String descricao;

	@Column(name="dtcad",nullable=false)
	private Date dtCad;
	
	@Column(name="tipo",nullable=false)
	private Integer tipo;
	

	@Override
	public Long getId() {
		return this.getIdLog();
	}

	@Override
	public void setId(Long id) {
		this.setIdLog(id);		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idLog == null) ? 0 : idLog.hashCode());
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
		Log other = (Log) obj;
		if (idLog == null) {
			if (other.idLog != null)
				return false;
		} else if (!idLog.equals(other.idLog))
			return false;
		return true;
	}

	public Long getIdLog() {
		return idLog;
	}

	public void setIdLog(Long idLog) {
		this.idLog = idLog;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDtCad() {
		return dtCad;
	}

	public void setDtCad(Date dtCad) {
		this.dtCad = dtCad;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	
	public int getInclusao(){
		return Constantes.INCLUSAO;
	}
	
	public int getAlteracao(){
		return Constantes.ALTERACAO;
	}
	
	public int getExclusao(){
		return Constantes.EXCLUSAO;
	}
	
	public int getComando(){
		return Constantes.COMANDO;
	}
	
	public int getLogin(){
		return Constantes.LOGIN;
	}

	
}
