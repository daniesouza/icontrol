package br.com.ischool.business;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import br.com.ischool.dao.ClasseDAOLocal;
import br.com.ischool.entity.Aluno;
import br.com.ischool.entity.Classe;
import br.com.ischool.entity.Usuario;
import br.com.ischool.exceptions.DAOException;
import br.com.ischool.exceptions.ServicoException;
import br.com.ischool.security.MD5CheckSum;
import br.com.ischool.util.DadosUtil;
import br.com.ischool.util.Paginacao;

/**
 * @author Daniel Souza de lima e-mail:daniesouza@gmail.com
 * Session Bean implementation class ClasseServiceImpl
 */
@Stateless
@Local(value=ClasseServiceLocal.class)
public class ClasseServiceImpl implements ClasseServiceLocal {

    /**
     * Default constructor. 
     */
    public ClasseServiceImpl() {
        // TODO Auto-generated constructor stub
    }
    
	@EJB
	private ClasseDAOLocal classeDAO;
	
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
	public void salvarClasse(Classe classe) throws ServicoException {
		
		try {
			validarClasse(classe);
			
			classe.setDataCadastro(new Date());
			classe.setAno(Calendar.getInstance().get(Calendar.YEAR));
			
			String codigoHash;
					
			codigoHash = classe.getNome()+classe.getTurma()+Calendar.getInstance().get(Calendar.YEAR)+classe.getCliente().getIdCliente();
			
			classe.setCodigoClasse(MD5CheckSum.gerarCodigoHash(codigoHash));

			boolean existeClasse = existeClasse(classe);
			if (existeClasse) {
				throw new ServicoException("CLASSE_EXISTENTE");
			}


			
			generico.salvar(classe);
		
			for(Usuario usuario:classe.getUsuarios()){
				usuario = generico.obterEntidade(usuario.getId(), Usuario.class);	
				usuario.getClasses().add(classe);			
				generico.atualizar(usuario);
			}
						
//			for(Usuario usuario:listaRemoverUsuarios){
//				usuario = generico.obterEntidade(usuario.getId(), Usuario.class);	
//				usuario.getClasses().remove(classe);		
//				generico.atualizar(usuario);
//			}
			
			
			for(Aluno aluno:classe.getAlunos()){
				aluno = generico.obterEntidade(aluno.getId(), Aluno.class);	
				aluno.getClasses().add(classe);			
				generico.atualizar(aluno);
			}
			
			
		} catch (ServicoException e) {
			throw e;
		} catch (Exception e) {			
			throw new ServicoException(e);
		}
					
	}

	
	private void validarClasse(Classe classe) throws ServicoException {
		ServicoException se = new ServicoException();

		if (DadosUtil.isEmpty(classe.getNome())) {
			se.adicionarMensagem("NOME_NAO_VAZIO");
		}
		if (DadosUtil.isEmpty(classe.getTurma())) {
			se.adicionarMensagem("TURMA_NAO_VAZIO");
		}
		
		if(DadosUtil.isEmpty(classe.getCliente()) || DadosUtil.isEmpty(classe.getCliente().getIdCliente())){
			se.adicionarMensagem("CLIENTE_NAO_VAZIO");
		}

		if (se.existeErro()) {
			throw se;
		}
	}
	
	private boolean existeClasse(Classe classe) throws ServicoException {
		try {
			if (!DadosUtil.isEmpty(classe.getIdClasse())) {
				Classe classeAux = classeDAO.selectById(classe.getIdClasse());
				if (classeAux.getCodigoClasse().equals(classe.getCodigoClasse())) {
					return false;
				}
			}

			Classe filtro = new Classe();
			filtro.setCodigoClasse((classe.getCodigoClasse()));

			int qtdeClasses = classeDAO.consultarQtde(filtro);
			return qtdeClasses > 0;

		} catch (DAOException e) {
			throw new ServicoException(e.getMessage());
		}
	}

	@Override
	public void alterarClasse(Classe classe,List<Usuario> listaRemoverUsuarios,List<Aluno> listaRemoverAlunos) throws ServicoException {
		
		try {
			validarClasse(classe);

			String codigoHash;
			
			codigoHash = classe.getNome()+classe.getTurma()+Calendar.getInstance().get(Calendar.YEAR)+classe.getCliente().getIdCliente();
			
			classe.setCodigoClasse(MD5CheckSum.gerarCodigoHash(codigoHash));
			
			boolean existeClasse = existeClasse(classe);
			if (existeClasse) {
				throw new ServicoException("CLASSE_EXISTENTE");
			}

			
			generico.atualizar(classe);
			
		
			for(Usuario usuario:classe.getUsuarios()){
				usuario = generico.obterEntidade(usuario.getId(), Usuario.class);	
				if(!usuario.getClasses().contains(classe)){
					usuario.getClasses().add(classe);
					generico.atualizar(usuario);
				}			
			}
			

			for(Usuario usuario:listaRemoverUsuarios){
				usuario = generico.obterEntidade(usuario.getId(), Usuario.class);	
				
				if(usuario.getClasses().contains(classe)){
					usuario.getClasses().remove(classe);
					generico.atualizar(usuario);
				}
			}
			
			for(Aluno aluno:classe.getAlunos()){
				aluno = generico.obterEntidade(aluno.getId(), Aluno.class);	
				if(!aluno.getClasses().contains(classe)){
					aluno.getClasses().add(classe);
					generico.atualizar(aluno);
				}			
			}
			

			for(Aluno aluno:listaRemoverAlunos){
				aluno = generico.obterEntidade(aluno.getId(), Aluno.class);	
				
				if(aluno.getClasses().contains(classe)){
					aluno.getClasses().remove(classe);
					generico.atualizar(aluno);
				}
			}			
			

		} catch (ServicoException e) {
			throw e;
		} catch (Exception e) {			
			throw new ServicoException(e);
		}
					
	}

	@Override
	public Collection<Classe> listarClasses() throws ServicoException {
		
		try {
			
			return classeDAO.listarClasses();
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}
	}

	@Override
	public Collection<Classe> listarClasses(Classe filtro,Paginacao paginacao) throws ServicoException {

		try {
			
			if(DadosUtil.isEmpty(paginacao)){
				paginacao = new Paginacao(-1);
			}
			
			return classeDAO.listarClasses(filtro,paginacao);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}
	}


	@Override
	public void excluirClasse(Classe Classe) throws ServicoException {
		
		try {
			
			classeDAO.excluirClasse(Classe);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}
		
	}

	@Override
	public Classe selectById(Long id) throws ServicoException {
		
		try {
			
			return classeDAO.selectById(id);

			
		} catch (DAOException e) {
			
			throw new ServicoException(e);
			
		}
	}
	

}
