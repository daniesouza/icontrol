package br.com.icontrol.business;

import java.util.Collection;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import br.com.icontrol.dao.UsuarioDAOLocal;
import br.com.icontrol.entity.Usuario;
import br.com.icontrol.exceptions.DAOException;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.DadosUtil;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
 * Session Bean implementation class UsuarioServiceImpl
 */
@Stateless
@Local(value=UsuarioServiceLocal.class)
public class UsuarioServiceImpl implements UsuarioServiceLocal {

	@EJB
	private UsuarioDAOLocal usuarioDAO;
	
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
	
    
	private void validarUsuario(Usuario usuario) throws ServicoException {
		ServicoException se = new ServicoException();

		if (DadosUtil.isEmpty(usuario.getUsuario())) {
			se.adicionarMensagem("USUARIO_NAO_VAZIO");
		}
		if (DadosUtil.isEmpty(usuario.getNome())) {
			se.adicionarMensagem("NOME_NAO_VAZIO");
		}
		if (DadosUtil.isEmpty(usuario.getSenha())) {
			se.adicionarMensagem("SENHA_NAO_VAZIO");
		}
		
		if (DadosUtil.isEmpty(usuario.getAutoridade())) {
			se.adicionarMensagem("AUTORIDADE_NAO_VAZIO");
		}

		if (se.existeErro()) {
			throw se;
		}
	}
	
	private boolean existeUsuario(Usuario usuario) throws ServicoException {
		try {
			if (!DadosUtil.isEmpty(usuario.getIdUsuario())) {
				Usuario usuarioAux = usuarioDAO.selectById(usuario.getIdUsuario());
				if (usuarioAux.getUsuario().equalsIgnoreCase(usuario.getUsuario())) {
					return false;
				}
			}

			Usuario filtro = new Usuario();
			filtro.setUsuario(usuario.getUsuario());
			int qtdeArduinos = usuarioDAO.consultarQtde(filtro);
			return qtdeArduinos > 0;

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServicoException(e.getMessage());
		}
	}
    
    
	@Override
	public void salvarUsuario(Usuario usuario) throws ServicoException {
		
		try {
				validarUsuario(usuario);
				
				boolean existeUsuario = existeUsuario(usuario);
				if (existeUsuario) {
					throw new ServicoException("USUARIO_EXISTENTE");
				}
				
				
				usuario.setDtCad(new Date());
				usuario.setAtivo(true);
				usuario.setDataSenha(new Date());
				usuario.setTrocarSenha(false);
				usuario.setTentativasSenhaIncorreta((byte)0);
							
				usuarioDAO.salvarUsuario(usuario);
				
			} catch (DAOException e) {
				
				throw new ServicoException(e);
				
			} catch (ServicoException e) {
				throw e;
			} catch (Exception e) {
				
				throw new ServicoException(e);
			}
		
			
	}

	@Override
	public void alterarUsuario(Usuario usuario) throws ServicoException {
		

		try {
			
			validarUsuario(usuario);
			
			boolean existeUsuario = existeUsuario(usuario);
			if (existeUsuario) {
				throw new ServicoException("USUARIO_EXISTENTE");
			}
			
			usuarioDAO.alterarUsuario(usuario);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);		
		} catch (ServicoException e) {
			throw e;
		} catch (Exception e) {		
			throw new ServicoException(e);
		}
	}

	@Override
	public Collection<Usuario> listarUsuarios() throws ServicoException {
		
		try {
			
			return usuarioDAO.listarUsuarios();
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}
	}
	
	@Override
	public Collection<Usuario> listarUsuariosSemClienteSemAdmin() throws ServicoException {
		
		try {
			
			return usuarioDAO.listarUsuariosSemClienteSemAdmin();
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}
	}

	@Override
	public Collection<Usuario> listarUsuarios(Usuario filtro,Paginacao paginacao) throws ServicoException {

		try {
			
			return usuarioDAO.listarUsuarios(filtro,paginacao);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}
	}

	@Override
	public Usuario pesquisarUsuarioNomeUsuario(String nome) throws ServicoException {

		try {
			
			return usuarioDAO.pesquisarUsuarioNomeUsuario(nome);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		} catch (Exception e) {
			
			throw new ServicoException(e);
		}
	}

	@Override
	public void excluirUsuario(Usuario usuario) throws ServicoException {
		
		try {
			
			usuarioDAO.excluirUsuario(usuario);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}
		
	}

	@Override
	public Usuario selectById(Long id) throws ServicoException {
		
		try {
			
			return usuarioDAO.selectById(id);

			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}
	}


}
