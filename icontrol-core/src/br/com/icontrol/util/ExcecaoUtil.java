package br.com.icontrol.util;

import java.util.ArrayList;
import java.util.List;

import br.com.icontrol.exceptions.ServicoException;

/**
 * @author Icontrol
 * 
 */

public class ExcecaoUtil {

	public static List<Mensagem> tratarMensagemErro(ServicoException se) {
		List<Mensagem> mensagens = new ArrayList<Mensagem>();
		List<String> mensagensEx = se.getMensagens();
		if (DadosUtil.isEmpty(mensagensEx)) {
			mensagens.add(new Mensagem(se.getMessage(), TipoMensagem.ERRO));
		} else {
			for (String mensagem : mensagensEx) {
				String valor = Propriedades.obterValor(mensagem);
				mensagens.add(new Mensagem(valor, TipoMensagem.ERRO));
			}
		}
		return mensagens;
	}
	
	public static String tratarMensagemErro(List<Mensagem> mensagens) {
		StringBuilder retorno = new StringBuilder();
		if (!DadosUtil.isEmpty(mensagens)) {
			for (Mensagem mensagem : mensagens) {
				String valor = Propriedades.obterValor(mensagem.getMensagem());
				retorno.append(valor);
				retorno.append("<br>");
			}
		}
		return retorno.toString();
	}	
	
}
