package br.com.icontrol.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import br.com.icontrol.dao.AtalhoDAOLocal;
import br.com.icontrol.dao.GenericDAOLocal;
import br.com.icontrol.entity.Atalho;
import br.com.icontrol.entity.AtalhoArdCompComando;
import br.com.icontrol.exceptions.DAOException;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.DadosUtil;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
 * Session Bean implementation class AtalhoServiceImpl
 */
@Stateless
@Local(value=AtalhoServiceLocal.class)
public class AtalhoServiceImpl implements AtalhoServiceLocal {

    /**
     * Default constructor. 
     */
    public AtalhoServiceImpl() {
        // TODO Auto-generated constructor stub
    }
    
	@EJB
	private AtalhoDAOLocal atalhoDAO;
	
	@EJB
	private GenericDAOLocal<AtalhoArdCompComando,Integer> atalhoArdCompPermissoesDAO;
	
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
	public void salvarAtalho(Atalho atalho,	List<AtalhoArdCompComando> atalhoArdCompComandos) throws ServicoException {
		
		try {
			validarAtalho(atalho);

			boolean existeAtalho = existeAtalho(atalho);
			if (existeAtalho) {
				throw new ServicoException("ATALHO_EXISTENTE");
			}

			atalho.setAtalhosArdCompComando(null);
			atalhoDAO.salvarAtalho(atalho);
			montarAtalhoArdCompPermissoes(atalho, atalhoArdCompComandos);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServicoException(e.getMessage());
		}
					
	}
	
	@Override
	public List<AtalhoArdCompComando> obterAtalhoArdCompComando(long idAtalho) throws ServicoException {
		try {
			List<AtalhoArdCompComando> atalhoArdCompComandos = atalhoDAO.obterAtalhoArdCompComando(idAtalho);
			return atalhoArdCompComandos;
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServicoException(e.getMessage());
		}
	}
	
	private void montarAtalhoArdCompPermissoes(Atalho atalho,List<AtalhoArdCompComando> atalhoArdCompComandos) throws DAOException {
		List<AtalhoArdCompComando> atalhoArdCompPermissoesValidos = new ArrayList<AtalhoArdCompComando>();
		
		for (AtalhoArdCompComando atalhoArdCompPermisao : atalhoArdCompComandos) {
			
				atalhoArdCompPermisao.setIdComando(atalhoArdCompPermisao.getComando().getId());
				atalhoArdCompPermisao.setIdComponente(atalhoArdCompPermisao.getComponente().getId());
				atalhoArdCompPermisao.setIdArduino(atalhoArdCompPermisao.getArduino().getId());
				atalhoArdCompPermisao.setAtalho(atalho);
				atalhoArdCompPermisao.setIdAtalho(atalho.getId());
				atalhoArdCompPermissoesDAO.save(atalhoArdCompPermisao);
				atalhoArdCompPermissoesValidos.add(atalhoArdCompPermisao);
	}
}
	
	private void validarAtalho(Atalho atalho) throws ServicoException {
		ServicoException se = new ServicoException();

		if (DadosUtil.isEmpty(atalho.getDescricao())) {
			se.adicionarMensagem("DESCRICAO_NAO_VAZIA");
		}
		if (DadosUtil.isEmpty(atalho.getNome())) {
			se.adicionarMensagem("NOME_NAO_VAZIO");
		}
		if (DadosUtil.isEmpty(atalho.getIcone())) {
			se.adicionarMensagem("ICONE_NAO_VAZIO");
		}

		if (se.existeErro()) {
			throw se;
		}
	}
	
	private boolean existeAtalho(Atalho atalho) throws ServicoException {
		try {
			if (!DadosUtil.isEmpty(atalho.getIdAtalho())) {
				Atalho AtalhoAux = atalhoDAO.selectById(atalho.getIdAtalho());
				if (AtalhoAux.getNome().equalsIgnoreCase(atalho.getNome())) {
					return false;
				}
			}

			Atalho filtro = new Atalho();
			filtro.setNome(atalho.getNome());

			int qtdeClientes = atalhoDAO.consultarQtde(filtro);
			return qtdeClientes > 0;

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServicoException(e.getMessage());
		}
	}

	@Override
	public void alterarAtalho(Atalho atalho,List<AtalhoArdCompComando> atalhoArdCompComandos) throws ServicoException {
		
		try {
			validarAtalho(atalho);

			boolean existeAtalho = existeAtalho(atalho);
			if (existeAtalho) {
				throw new ServicoException("ATALHO_EXISTENTE");
			}

			List<AtalhoArdCompComando> mfRemover = obterAtalhoArdCompComando(atalho.getId());
			for (AtalhoArdCompComando mf : mfRemover) {
				atalhoArdCompPermissoesDAO.delete(mf);
			}

			montarAtalhoArdCompPermissoes(atalho, atalhoArdCompComandos);
			atalhoDAO.alterarAtalho(atalho);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServicoException(e.getMessage());
		}
					
	}

	@Override
	public Collection<Atalho> listarAtalhos() throws ServicoException {
		
		try {
			
			return atalhoDAO.listarAtalhos();
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		} catch (Exception e) {
			
			throw new ServicoException(e);
		}
	}

	@Override
	public Collection<Atalho> listarAtalhos(Atalho filtro,Paginacao paginacao) throws ServicoException {

		try {
			
			return atalhoDAO.listarAtalhos(filtro,paginacao);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		} catch (Exception e) {
			
			throw new ServicoException(e);
		}
	}


	@Override
	public void excluirAtalho(Atalho atalho) throws ServicoException {
		
		try {
			
			atalhoDAO.excluirAtalho(atalho);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		} catch (Exception e) {
			
			throw new ServicoException(e);
		}
		
	}

}
