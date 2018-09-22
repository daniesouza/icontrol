package br.com.icontrol.business;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import br.com.icontrol.dao.LogDAOLocal;
import br.com.icontrol.entity.Log;
import br.com.icontrol.exceptions.DAOException;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.util.DadosUtil;
import br.com.icontrol.util.Paginacao;

/**
 * @author Icontrol
 * Session Bean implementation class LogServiceImpl
 */
@Stateless
@Local(value=LogServiceLocal.class)
public class LogServiceImpl implements LogServiceLocal {

    /**
     * Default constructor. 
     */
    public LogServiceImpl() {
        // TODO Auto-generated constructor stub
    }
    
	@EJB
	private LogDAOLocal logDAO;
	
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
	public void salvarLog(Log log) throws ServicoException {
		
		try {
			validarLog(log);
					
			logDAO.salvarLog(log);
			
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServicoException(e.getMessage());
		}
			
	}

	@Override
	public void alterarLog(Log log) throws ServicoException {
		

		try {
			validarLog(log);
			
			logDAO.alterarLog(log);
			
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServicoException(e.getMessage());
		}
			
			
	}

	@Override
	public Collection<Log> listarLogs() throws ServicoException {
		
		try {
			
			return logDAO.listarLogs();
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);			
		}
	}

	@Override
	public Collection<Log> listarLogs(Log filtro,Paginacao paginacao) throws ServicoException {

		try {
			
			return logDAO.listarLogs(filtro,paginacao);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);			
		}
	}


	@Override
	public void excluirLog(Log Log) throws ServicoException {
		
		try {
			
			logDAO.excluirLog(Log);
			
		} catch (DAOException e) {
			
			throw new ServicoException(e);		
		} 
		
	}
	
	
	private void validarLog(Log log) throws ServicoException {
		ServicoException se = new ServicoException();


		if (DadosUtil.isEmpty(log.getDescricao())) {
			se.adicionarMensagem("DESCRICAO_NAO_VAZIO");
		}
		
		if (DadosUtil.isEmpty(log.getTipo())) {
			se.adicionarMensagem("TIPO_NAO_VAZIO");
		}
		
		if (DadosUtil.isEmpty(log.getDtCad())) {
			se.adicionarMensagem("DATACAD_NAO_VAZIO");
		}
		
		if (DadosUtil.isEmpty(log.getUsuario()) || DadosUtil.isEmpty(log.getUsuario().getId())) {
			se.adicionarMensagem("USUARIO_NAO_VAZIO");
		}

		if (se.existeErro()) {
			throw se;
		}
	}
	

}
