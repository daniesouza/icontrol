package br.com.icontrol.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import br.com.icontrol.dao.GenericDAOLocal;
import br.com.icontrol.dao.GrupoDAOLocal;
import br.com.icontrol.entity.Arduino;
import br.com.icontrol.entity.Grupo;
import br.com.icontrol.entity.GrupoArdCompPermissoes;
import br.com.icontrol.entity.Usuario;
import br.com.icontrol.exceptions.DAOException;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.DadosUtil;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
 * Session Bean implementation class GrupoServiceImpl
 */
@Stateless
@Local(value=GrupoServiceLocal.class)
public class GrupoServiceImpl implements GrupoServiceLocal {

    /**
     * Default constructor. 
     */
    public GrupoServiceImpl() {
        // TODO Auto-generated constructor stub
    }
    
	@EJB
	private GrupoDAOLocal grupoDAO;
	
	@EJB
	private GenericDAOLocal<GrupoArdCompPermissoes,Integer> grupoArdCompPermissoesDAO;
	
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
	public void salvarGrupo(Grupo grupo,
			List<GrupoArdCompPermissoes> grupoArdCompPermissoes,
			List<Arduino> arduinos, List<Usuario> usuarios) throws ServicoException {
		
		try {
			validarGrupo(grupo);

			boolean existeGrupo = existeGrupo(grupo);
			if (existeGrupo) {
				throw new ServicoException("GRUPO_EXISTENTE");
			}

			grupo.setArduinos(arduinos);
			grupo.setGruposArdCompPermissoes(null);
			grupo.setUsuarios(usuarios);
			grupoDAO.salvarGrupo(grupo);
			montarGrupoArdCompPermissoes(grupo, grupoArdCompPermissoes, arduinos);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServicoException(e.getMessage());
		}
					
	}
	
	@Override
	public List<GrupoArdCompPermissoes> obterGrupoArdCompPermissoes(long idGrupo) throws ServicoException {
		try {
			List<GrupoArdCompPermissoes> grupoArdCompPermissoes = grupoDAO.obterGrupoArdCompPermissoes(idGrupo);
			return grupoArdCompPermissoes;
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServicoException(e.getMessage());
		}
	}
	
	private void montarGrupoArdCompPermissoes(Grupo grupo,List<GrupoArdCompPermissoes> grupoArdCompPermissoes,List<Arduino> arduinos) throws DAOException {
		List<GrupoArdCompPermissoes> grupoArdCompPermissoesValidos = new ArrayList<GrupoArdCompPermissoes>();
		for (GrupoArdCompPermissoes grupoArdCompPermisao : grupoArdCompPermissoes) {
			boolean possuiArduino = false;
			for (Arduino arduino : arduinos) {
				if (grupoArdCompPermisao.getArduino().equals(arduino)) {
					possuiArduino = true;
					break;
				}
			}
			if (possuiArduino) {
				grupoArdCompPermisao.setIdComando(grupoArdCompPermisao.getComando().getId());
				grupoArdCompPermisao.setIdComponente(grupoArdCompPermisao.getComponente().getId());
				grupoArdCompPermisao.setIdArduino(grupoArdCompPermisao.getArduino().getId());
				grupoArdCompPermisao.setGrupo(grupo);
				grupoArdCompPermisao.setIdGrupo(grupo.getId());
				grupoArdCompPermissoesDAO.save(grupoArdCompPermisao);
				grupoArdCompPermissoesValidos.add(grupoArdCompPermisao);
			}
		}
		grupo.setGruposArdCompPermissoes(grupoArdCompPermissoesValidos);
	}
	
	private void validarGrupo(Grupo grupo) throws ServicoException {
		ServicoException se = new ServicoException();

		if (DadosUtil.isEmpty(grupo.getDescricao())) {
			se.adicionarMensagem("DESCRICAO_NAO_VAZIA");
		}
		if (DadosUtil.isEmpty(grupo.getNome())) {
			se.adicionarMensagem("NOME_NAO_VAZIO");
		}

		if (se.existeErro()) {
			throw se;
		}
	}
	
	private boolean existeGrupo(Grupo grupo) throws ServicoException {
		try {
			if (!DadosUtil.isEmpty(grupo.getIdGrupo())) {
				Grupo grupoAux = grupoDAO.selectById(grupo.getIdGrupo());
				if (grupoAux.getNome().equalsIgnoreCase(grupo.getNome())) {
					return false;
				}
			}

			Grupo filtro = new Grupo();
			filtro.setNome(grupo.getNome());

			int qtdeClientes = grupoDAO.consultarQtde(filtro);
			return qtdeClientes > 0;

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServicoException(e.getMessage());
		}
	}

	@Override
	public void alterarGrupo(Grupo grupo,List<GrupoArdCompPermissoes> grupoArdCompPermissoes,List<Arduino> arduinos, List<Usuario> usuarios) throws ServicoException {
		
		try {
			validarGrupo(grupo);

			boolean existeGrupo = existeGrupo(grupo);
			if (existeGrupo) {
				throw new ServicoException("GRUPO_EXISTENTE");
			}

			List<GrupoArdCompPermissoes> mfRemover = obterGrupoArdCompPermissoes(grupo.getId());
			for (GrupoArdCompPermissoes mf : mfRemover) {
				grupoArdCompPermissoesDAO.delete(mf);
			}

			grupo.setArduinos(arduinos);
			grupo.setUsuarios(usuarios);
			montarGrupoArdCompPermissoes(grupo, grupoArdCompPermissoes, arduinos);
			grupoDAO.alterarGrupo(grupo);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServicoException(e.getMessage());
		}
					
	}

	@Override
	public Collection<Grupo> listarGrupos() throws ServicoException {
		
		try {
			
			return grupoDAO.listarGrupos();
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		} catch (Exception e) {
			
			throw new ServicoException(e);
		}
	}

	@Override
	public Collection<Grupo> listarGrupos(Grupo filtro,Paginacao paginacao) throws ServicoException {

		try {
			
			return grupoDAO.listarGrupos(filtro,paginacao);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		} catch (Exception e) {
			
			throw new ServicoException(e);
		}
	}


	@Override
	public void excluirGrupo(Grupo grupo) throws ServicoException {
		
		try {
			
			grupoDAO.excluirGrupo(grupo);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		} catch (Exception e) {
			
			throw new ServicoException(e);
		}
		
	}

}
