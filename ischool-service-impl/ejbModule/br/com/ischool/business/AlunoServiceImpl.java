package br.com.ischool.business;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import br.com.ischool.dao.AlunoDAOLocal;
import br.com.ischool.entity.Aluno;
import br.com.ischool.entity.Classe;
import br.com.ischool.entity.Usuario;
import br.com.ischool.exceptions.DAOException;
import br.com.ischool.exceptions.ServicoException;
import br.com.ischool.util.DadosUtil;
import br.com.ischool.util.Paginacao;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * Session Bean implementation class AlunoServiceImpl
 */
@Stateless
@Local(value=AlunoServiceLocal.class)
public class AlunoServiceImpl implements AlunoServiceLocal {

    /**
     * Default constructor. 
     */
    public AlunoServiceImpl() {
        // TODO Auto-generated constructor stub
    }
    
	@EJB
	private AlunoDAOLocal alunoDAO;
	
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
	public void salvarAluno(Aluno aluno) throws ServicoException {
		
		try {
			validarAluno(aluno);

			boolean existeAluno = existeAluno(aluno);
			if (existeAluno) {
				throw new ServicoException("ALUNO_EXISTENTE");
			}

			aluno.setDataCadastro(new Date());
			
			generico.salvar(aluno);
					
			
			for(Usuario usuario:aluno.getUsuarios()){
				usuario = generico.obterEntidade(usuario.getId(), Usuario.class);	
				usuario.getAlunos().add(aluno);			
				generico.atualizar(usuario);
			}
			
	
			for(Classe classe:aluno.getClasses()){
				classe = generico.obterEntidade(classe.getId(), Classe.class);	
				classe.getAlunos().add(aluno);			
				generico.atualizar(classe);
			}
					
			
		} catch (ServicoException e) {
			throw e;
		}
					
	}

	
	private void validarAluno(Aluno aluno) throws ServicoException {
		ServicoException se = new ServicoException();

		if (DadosUtil.isEmpty(aluno.getNome())) {
			se.adicionarMensagem("NOME_NAO_VAZIO");
		}
		
		if (DadosUtil.isEmpty(aluno.getCodigoAluno())) {
			se.adicionarMensagem("CODIGO_NAO_VAZIO");
		}

		if (se.existeErro()) {
			throw se;
		}
	}
	
	private boolean existeAluno(Aluno aluno) throws ServicoException {
		try {
			if (!DadosUtil.isEmpty(aluno.getIdAluno())) {
				Aluno alunoAux = alunoDAO.selectById(aluno.getIdAluno());
				if (alunoAux.getCodigoAluno().equals(aluno.getCodigoAluno())) {
					return false;
				}
			}

			Aluno filtro = new Aluno();
			filtro.setCodigoAluno(aluno.getCodigoAluno());

			int qtdeAlunos = alunoDAO.consultarQtde(filtro);
			return qtdeAlunos > 0;

		} catch (DAOException e) {
			throw new ServicoException(e.getMessage());
		}
	}

	@Override
	public void alterarAluno(Aluno aluno,List<Usuario> listaRemoverUsuarios,List<Classe> listaRemoverClasses) throws ServicoException {
		
		try {
			validarAluno(aluno);

			boolean existeAluno = existeAluno(aluno);
			if (existeAluno) {
				throw new ServicoException("ALUNO_EXISTENTE");
			}

			
			generico.atualizar(aluno);
			

			for(Usuario usuario:aluno.getUsuarios()){
				usuario = generico.obterEntidade(usuario.getId(), Usuario.class);	
				if(!usuario.getAlunos().contains(aluno)){
					usuario.getAlunos().add(aluno);
					generico.atualizar(usuario);
				}			
			}
			

			for(Usuario usuario:listaRemoverUsuarios){
				usuario = generico.obterEntidade(usuario.getId(), Usuario.class);	
				
				if(usuario.getAlunos().contains(aluno)){
					usuario.getAlunos().remove(aluno);
					generico.atualizar(usuario);
				}
			}
			
			for(Classe classe:aluno.getClasses()){
				classe = generico.obterEntidade(classe.getId(), Classe.class);	
				
				if(!classe.getAlunos().contains(aluno)){
					classe.getAlunos().add(aluno);
					generico.atualizar(classe);
				}
			}

			for(Classe classe:listaRemoverClasses){
				classe = generico.obterEntidade(classe.getId(), Classe.class);	
				if(classe.getAlunos().contains(aluno)){
					classe.getAlunos().remove(aluno);
					generico.atualizar(classe);
				}
			}		
			
		} catch (ServicoException e) {
			throw e;
		}
					
	}

	@Override
	public Collection<Aluno> listarAlunos() throws ServicoException {
		
		try {
			
			return alunoDAO.listarAlunos();
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}
	}

	@Override
	public Collection<Aluno> listarAlunos(Aluno filtro,Paginacao paginacao) throws ServicoException {

		try {
			
			if(DadosUtil.isEmpty(paginacao)){
				paginacao = new Paginacao(-1);
			}
			
			return alunoDAO.listarAlunos(filtro,paginacao);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}
	}


	@Override
	public void excluirAluno(Aluno Aluno) throws ServicoException {
		
		try {
			
			alunoDAO.excluirAluno(Aluno);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}
		
	}

	@Override
	public Aluno selectById(Long id) throws ServicoException {
		
		try {
			
			return alunoDAO.selectById(id);

			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}
	}


}
