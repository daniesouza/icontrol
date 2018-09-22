package br.com.icontrol.business;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import br.com.icontrol.dao.ComandoDAOLocal;
import br.com.icontrol.entity.Comando;
import br.com.icontrol.exceptions.DAOException;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.DadosUtil;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
 * Session Bean implementation class ComandoServiceImpl
 */
@Stateless
@Local(value=ComandoServiceLocal.class)
public class ComandoServiceImpl implements ComandoServiceLocal {

    /**
     * Default constructor. 
     */
    public ComandoServiceImpl() {
        // TODO Auto-generated constructor stub
    }
    
	@EJB
	private ComandoDAOLocal comandoDAO;
	
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
	public void salvarComando(Comando comando) throws ServicoException {
		
		try {
			validarComando(comando);
			
			boolean existeComando = existeComando(comando);
			if (existeComando) {
				throw new ServicoException("COMANDO_EXISTENTE");
			}
			
			comandoDAO.salvarComando(comando);
			
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServicoException(e.getMessage());
		}
			
	}

	@Override
	public void alterarComando(Comando comando) throws ServicoException {
		

		try {
			validarComando(comando);
			
			boolean existeGrupo = existeComando(comando);
			if (existeGrupo) {
				throw new ServicoException("COMANDO_EXISTENTE");
			}
			
			comandoDAO.alterarComando(comando);
			
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServicoException(e.getMessage());
		}
			
			
	}

	@Override
	public Collection<Comando> listarComandos() throws ServicoException {
		
		try {
			
			return comandoDAO.listarComandos();
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);			
		}
	}

	@Override
	public Collection<Comando> listarComandos(Comando filtro,Paginacao paginacao) throws ServicoException {

		try {
			
			return comandoDAO.listarComandos(filtro,paginacao);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);			
		}
	}


	@Override
	public void excluirComando(Comando comando) throws ServicoException {
		
		try {
			
			comandoDAO.excluirComando(comando);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);		
		} 
		
	}
	
	
	private void validarComando(Comando comando) throws ServicoException {
		ServicoException se = new ServicoException();

		if (DadosUtil.isEmpty(comando.getCodigo())) {
			se.adicionarMensagem("CODIGO_NAO_VAZIO");
		}
		if (DadosUtil.isEmpty(comando.getNome())) {
			se.adicionarMensagem("NOME_NAO_VAZIO");
		}
		
		if (DadosUtil.isEmpty(comando.getStatus())) {
			se.adicionarMensagem("STATUS_NAO_VAZIO");
		}
		
		if (DadosUtil.isEmpty(comando.getIcone())) {
			se.adicionarMensagem("ICONE_NAO_VAZIO");
		}
		
		if (DadosUtil.isEmpty(comando.getTipo())) {
			se.adicionarMensagem("TIPO_COMANDO_NAO_VAZIO");
		}else{
			
			if(comando.getTipo() == comando.getTipoInfraVermelho()){
				try{
					comando.setCodigo(Long.decode(comando.getCodigo()).toString());
				}catch (NumberFormatException e) {
					se.adicionarMensagem("CODIGO_COMANDO_INVALIDO");
				}catch (Exception e) {
					se.adicionarMensagem("CODIGO_COMANDO_INVALIDO");
				}
			}
		}
		
		if (DadosUtil.isEmpty(comando.getCodificacao())) {
			se.adicionarMensagem("CODIGICACAO_NAO_VAZIO");
		}

		if (se.existeErro()) {
			throw se;
		}
	}
	
	private boolean existeComando(Comando comando) throws ServicoException {
		try {
			if (!DadosUtil.isEmpty(comando.getIdComando())) {
				Comando ComandoAux = comandoDAO.selectById(comando.getIdComando());
				if (ComandoAux.getCodigo().equalsIgnoreCase(comando.getCodigo())) {
					return false;
				}
			}

			Comando filtro = new Comando();
			filtro.setCodigo(comando.getCodigo());

			int qtdeClientes = comandoDAO.consultarQtde(filtro);
			return qtdeClientes > 0;

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServicoException(e.getMessage());
		}
	}

}
