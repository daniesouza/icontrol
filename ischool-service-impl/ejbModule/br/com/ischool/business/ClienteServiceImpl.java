package br.com.ischool.business;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import br.com.ischool.dao.ClienteDAOLocal;
import br.com.ischool.entity.Cliente;
import br.com.ischool.entity.Usuario;
import br.com.ischool.exceptions.DAOException;
import br.com.ischool.exceptions.ServicoException;
import br.com.ischool.util.DadosUtil;
import br.com.ischool.util.Paginacao;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * Session Bean implementation class ClienteServiceImpl
 */
@Stateless
@Local(value=ClienteServiceLocal.class)
public class ClienteServiceImpl implements ClienteServiceLocal {

    /**
     * Default constructor. 
     */
    public ClienteServiceImpl() {
        // TODO Auto-generated constructor stub
    }
    
	@EJB
	private ClienteDAOLocal clienteDAO;
	
	@EJB
	private GenericServiceLocal generico;
	
    @PostConstruct
    public void carregarInformacoes()
    {
    	System.out.println("CARREGADO OS RECURSOS DO EJB "+this.getClass().getName());
    }
    
    @PreDestroy
    public void clear()
    {
    	System.out.println("LIBERANDO OS RECURSOS DO EJB "+this.getClass().getName());
    }
	
	@Override
	public void salvarCliente(Cliente cliente,List<Usuario> listaRemoverUsuarios) throws ServicoException {
		
		try {
			validarCliente(cliente);

			boolean existeCliente = existeCliente(cliente);
			if (existeCliente) {
				throw new ServicoException("CLIENTE_EXISTENTE");
			}

			cliente.setDtCad(new Date());
			
			generico.salvar(cliente);
			
			

			
			for(Usuario usuario:cliente.getUsuarios()){
				usuario = generico.obterEntidade(usuario.getId(), Usuario.class);	
				usuario.setCliente(cliente);			
				generico.atualizar(usuario);
			}
			

			
			for(Usuario usuario:listaRemoverUsuarios){
				usuario = generico.obterEntidade(usuario.getId(), Usuario.class);	
				usuario.setCliente(null);			
				generico.atualizar(usuario);
			}
			
			
		} catch (ServicoException e) {
			throw e;
		}
					
	}

	
	private void validarCliente(Cliente cliente) throws ServicoException {
		ServicoException se = new ServicoException();

		if (DadosUtil.isEmpty(cliente.getRazaoSocial())) {
			se.adicionarMensagem("RAZAO_SOCIAL_NAO_VAZIA");
		}
		if (DadosUtil.isEmpty(cliente.getNome())) {
			se.adicionarMensagem("NOME_NAO_VAZIO");
		}

		if (se.existeErro()) {
			throw se;
		}
	}
	
	private boolean existeCliente(Cliente cliente) throws ServicoException {
		try {
			if (!DadosUtil.isEmpty(cliente.getIdCliente())) {
				Cliente clienteAux = clienteDAO.selectById(cliente.getIdCliente());
				if (clienteAux.getCnpj().equals(cliente.getCnpj())) {
					return false;
				}
			}

			Cliente filtro = new Cliente();
			filtro.setCnpj(cliente.getCnpj());

			int qtdeClientes = clienteDAO.consultarQtde(filtro);
			return qtdeClientes > 0;

		} catch (DAOException e) {
			throw new ServicoException(e.getMessage());
		}
	}

	@Override
	public void alterarCliente(Cliente cliente,List<Usuario> listaRemoverUsuarios) throws ServicoException {
		
		try {
			validarCliente(cliente);

			boolean existeCliente = existeCliente(cliente);
			if (existeCliente) {
				throw new ServicoException("CLIENTE_EXISTENTE");
			}

			
			generico.atualizar(cliente);
			
			

			
			for(Usuario usuario:cliente.getUsuarios()){
				usuario.setCliente(cliente);			
				generico.atualizar(usuario);
			}
			
			
				
			for(Usuario usuario:listaRemoverUsuarios){
				usuario.setCliente(null);			
				generico.atualizar(usuario);
			}
		} catch (ServicoException e) {
			throw e;
		}
					
	}

	@Override
	public Collection<Cliente> listarClientes() throws ServicoException {
		
		try {
			
			return clienteDAO.listarClientes();
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}
	}

	@Override
	public Collection<Cliente> listarClientes(Cliente filtro,Paginacao paginacao) throws ServicoException {

		try {
			
			return clienteDAO.listarClientes(filtro,paginacao);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}
	}


	@Override
	public void excluirCliente(Cliente cliente) throws ServicoException {
		
		try {
			
			clienteDAO.excluirCliente(cliente);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}
		
	}

}
