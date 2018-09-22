package br.com.ischool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="evento_classe")
public class EventoClasse implements Entidade{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 563187035193866662L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)  
	@Column(name="id_evento_classe")
    private Long idEventoClasse;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_evento", referencedColumnName="id_evento")
	private Evento evento;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_classe", referencedColumnName="id_classe")	
	private Classe classe;
	
	@Column(name="tipo")
	private Integer tipo;

	
	public Long getIdEventoClasse() {
		return idEventoClasse;
	}

	public void setIdEventoClasse(Long idEventoClasse) {
		this.idEventoClasse = idEventoClasse;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}


	@Override
	public Long getId() {
		
		return this.getIdEventoClasse();
	}

	@Override
	public void setId(Long id) {
		this.setIdEventoClasse(id);		
	}

	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classe == null) ? 0 : classe.hashCode());
		result = prime * result + ((evento == null) ? 0 : evento.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		EventoClasse other = (EventoClasse) obj;
		if (classe == null) {
			if (other.classe != null)
				return false;
		} else if (!classe.equals(other.classe))
			return false;
		if (evento == null) {
			if (other.evento != null)
				return false;
		} else if (!evento.equals(other.evento))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}


	

}
