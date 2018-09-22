package br.com.icontrol.entity;

import java.util.ArrayList;
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
import javax.persistence.Transient;

import br.com.icontrol.util.ClienteSocket;

/**
 * @author Icontrol
 * 
 */

@Entity
@Table(name="arduino")
public class Arduino implements Entidade {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4947454660843477347L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_ARDUINO")
	private Long idArduino;
	
	@Column(name="CODIGO_ARDUINO", nullable=false,unique=true)
	private String codigo;
	
	@Column(name="nome", nullable=false)
	private String nome;
	
	@Column(name="ip", nullable=false)
	private String ip;
	
	@Column(name="porta", nullable=false)
	private Integer porta;
	
	@ManyToMany(mappedBy="arduinos")
	private List<Grupo> grupos = new ArrayList<Grupo>();
	
	@ManyToMany
	@JoinTable(name="COMPONENTE_ARDUINO",
	joinColumns=@JoinColumn(name="ID_ARDUINO", referencedColumnName="ID_ARDUINO"),
	inverseJoinColumns=@JoinColumn(name="id_componente", referencedColumnName="id_componente"))
	private List<Componente> componentes;
		
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_cliente", referencedColumnName="id_cliente")
	private Cliente cliente;
	
	@OneToMany(mappedBy="arduino")
	private List<Camera> cameras;
	
	@Column(name="andar", nullable=false)
	private Integer andar;
	
	@Transient
	private transient boolean hide;
	
	
	@Transient
	private transient ClienteSocket clienteSocket;
		
	public List<Camera> getCameras() {
		return cameras;
	}

	public void setCameras(List<Camera> cameras) {
		this.cameras = cameras;
	}

	@Override
	public Long getId() {
		
		return this.getIdArduino();
	}

	@Override
	public void setId(Long id) {
		this.setIdArduino(id);
		
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

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Componente> getComponentes() {
		return componentes;
	}

	public void setComponentes(List<Componente> componentes) {
		this.componentes = componentes;
	}


	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Long getIdArduino() {
		return idArduino;
	}

	public void setIdArduino(Long idArduino) {
		this.idArduino = idArduino;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
		
	public Integer getPorta() {
		return porta;
	}

	public void setPorta(Integer porta) {
		this.porta = porta;
	}

	public ClienteSocket getClienteSocket() {
		return clienteSocket;
	}

	public void setClienteSocket(ClienteSocket clienteSocket) {
		this.clienteSocket = clienteSocket;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result
				+ ((idArduino == null) ? 0 : idArduino.hashCode());
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
		Arduino other = (Arduino) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (idArduino == null) {
			if (other.idArduino != null)
				return false;
		} else if (!idArduino.equals(other.idArduino))
			return false;
		return true;
	}

	public boolean isHide() {
		return hide;
	}

	public void setHide(boolean hide) {
		this.hide = hide;
	}

	public Integer getAndar() {
		return andar;
	}

	public void setAndar(Integer andar) {
		this.andar = andar;
	}

	@Override
	public String toString() {
		return "Arduino [idArduino=" + idArduino + ", codigo=" + codigo
				+ ", nome=" + nome + ", ip=" + ip + ", porta=" + porta
				+ ", grupos=" + grupos + ", componentes=" + componentes
				+ ", cliente=" + cliente + ", cameras=" + cameras + ", andar="
				+ andar + "]";
	}



	
	
}
