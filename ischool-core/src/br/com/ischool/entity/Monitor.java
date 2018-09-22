package br.com.ischool.entity;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * 
 */

public class Monitor implements Entidade{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7605633364340348978L;
	
	private Usuario usuario;
	private Long idMonitoramento;
	
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
	
	
}
