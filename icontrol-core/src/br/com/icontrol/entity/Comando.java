package br.com.icontrol.entity;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.icontrol.util.Constantes;

/**
 * @author Icontrol
 * 
 */

@Entity
@Table(name="comando")
public class Comando implements Entidade{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5078072252212279655L;


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)  
	@Column(name="id_comando")
    private Long idComando;
	
    
	@Column(name="codigo",nullable=false,unique=true)
	private String codigo;
	
	@Column(name="nome",nullable=false)
	private String nome;

	@Column(name="icone",nullable=false)
	private String icone;
	
	@Column(name="status",nullable=false)
	private String status;
	
	@Column(name="tipo",nullable=false)
	private Integer tipo;
	
	@Column(name="codificacao",nullable=false)
	private Integer codificacao;
	
	@ManyToMany(mappedBy="comandos",fetch = FetchType.LAZY)
	private Collection<Componente> componentes;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="comando")	
	private List<GrupoArdCompPermissoes> grupoArdCompPermissoes;	

	@Override
	public Long getId() {
		
		return this.getIdComando();
	}

	@Override
	public void setId(Long id) {
		
		this.setIdComando(id);
		
	}
	
	public Long getIdComando() {
		return idComando;
	}


	public void setIdComando(Long idComando) {
		this.idComando = idComando;
	}


	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}

	public Collection<Componente> getComponentes() {
		return componentes;
	}

	public void setComponentes(Collection<Componente> componentes) {
		this.componentes = componentes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Integer getCodificacao() {
		return codificacao;
	}

	public void setCodificacao(Integer codificacao) {
		this.codificacao = codificacao;
	}

	@Override
	public String toString() {
		return "Comando [idComando=" + idComando + ", codigo=" + codigo
				+ ", nome=" + nome + ", icone=" + icone + ", status=" + status
				+ ", componentes=" + componentes + ", grupoArdCompPermissoes="
				+ grupoArdCompPermissoes + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result
				+ ((idComando == null) ? 0 : idComando.hashCode());
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
		Comando other = (Comando) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (idComando == null) {
			if (other.idComando != null)
				return false;
		} else if (!idComando.equals(other.idComando))
			return false;
		return true;
	}

	public List<GrupoArdCompPermissoes> getGrupoArdCompPermissoes() {
		return grupoArdCompPermissoes;
	}

	public void setGrupoArdCompPermissoes(
			List<GrupoArdCompPermissoes> grupoArdCompPermissoes) {
		this.grupoArdCompPermissoes = grupoArdCompPermissoes;
	}

	public String getIcone() {
		return icone;
	}

	public void setIcone(String icone) {
		this.icone = icone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public  int getTipoInfraVermelho() {
		return Constantes.TIPO_COMANDO_INFRA_VERMELHO;
	}

	public  int getTipoAnalogico() {
		return Constantes.TIPO_COMANDO_ANALOGICO;
	}
	
}
