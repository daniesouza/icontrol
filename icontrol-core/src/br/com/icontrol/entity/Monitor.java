package br.com.icontrol.entity;

/**
 * @author Icontrol
 * 
 */

public class Monitor implements Entidade{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7605633364340348978L;
	
	private Usuario usuario;
	private Long 	idMonitoramento;
	private Cliente cliente;
	
	@Override
	public Long getId() {
		
		return this.getIdMonitoramento();
	}
	@Override
	public void setId(Long id) {
		this.setIdMonitoramento(id);
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Long getIdMonitoramento() {
		return idMonitoramento;
	}
	public void setIdMonitoramento(Long idMonitoramento) {
		this.idMonitoramento = idMonitoramento;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
}
