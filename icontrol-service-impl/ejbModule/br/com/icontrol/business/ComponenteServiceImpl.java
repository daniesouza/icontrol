package br.com.icontrol.business;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import br.com.icontrol.dao.ComponenteDAOLocal;
import br.com.icontrol.entity.Componente;
import br.com.icontrol.exceptions.DAOException;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.DadosUtil;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
 * Session Bean implementation class ComponenteServiceImpl
 */
@Stateless
@Local(value=ComponenteServiceLocal.class)
public class ComponenteServiceImpl implements ComponenteServiceLocal {

    /**
     * Default constructor. 
     */
    public ComponenteServiceImpl() {
        // TODO Auto-generated constructor stub
    }
    
	@EJB
	private ComponenteDAOLocal componenteDAO;
	
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
	public void salvarComponente(Componente componente) throws ServicoException {
		
		try {
			validarComponente(componente);
			
			boolean existeComponente = existeComponente(componente);
			if (existeComponente) {
				throw new ServicoException("COMPONENTE_EXISTENTE");
			}
			
			componenteDAO.salvarComponente(componente);
			
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServicoException(e.getMessage());
		}
			
	}

	@Override
	public void alterarComponente(Componente componente) throws ServicoException {
		
		try {
			validarComponente(componente);
			
			boolean existeComponente = existeComponente(componente);
			if (existeComponente) {
				throw new ServicoException("COMPONENTE_EXISTENTE");
			}
			
			componenteDAO.alterarComponente(componente);
			
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServicoException(e.getMessage());
		}
			
	}

	@Override
	public Collection<Componente> listarComponentes() throws ServicoException {
		
		try {
			
			return componenteDAO.listarComponentes();
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}
	}

	@Override
	public Collection<Componente> listarComponentes(Componente filtro,Paginacao paginacao) throws ServicoException {

		try {
			
			return componenteDAO.listarComponentes(filtro,paginacao);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);			
		} 
	}

	@Override
	public void excluirComponente(Componente componente) throws ServicoException {
		
		try {
			
			componenteDAO.excluirComponente(componente);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		} 
		
	}
	
	private void validarComponente(Componente componente) throws ServicoException {
		ServicoException se = new ServicoException();

		if (DadosUtil.isEmpty(componente.getCodigo())) {
			se.adicionarMensagem("CODIGO_NAO_VAZIO");
		}
		if (DadosUtil.isEmpty(componente.getNome())) {
			se.adicionarMensagem("NOME_NAO_VAZIO");
		}
		
		if (DadosUtil.isEmpty(componente.getPortaArduino())) {
			se.adicionarMensagem("PORTA_NAO_VAZIO");
		}

		if (se.existeErro()) {
			throw se;
		}
	}
	
	private boolean existeComponente(Componente componente) throws ServicoException {
		try {
			if (!DadosUtil.isEmpty(componente.getIdComponente())) {
				Componente componenteAux = componenteDAO.selectById(componente.getIdComponente());
				if (componenteAux.getCodigo().equalsIgnoreCase(componente.getCodigo())) {
					return false;
				}
			}

			Componente filtro = new Componente();
			filtro.setCodigo(componente.getCodigo());

			int qtdeClientes = componenteDAO.consultarQtde(filtro);
			return qtdeClientes > 0;

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServicoException(e.getMessage());
		}
	}

}
